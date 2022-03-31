package com.citi.trade.stock;

import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.citi.trade.dao.StockDAO;
import com.citi.trade.stock.quote.Quote;
import com.citi.trade.stock.quote.TradeHistoricalData;
import com.citi.trade.util.StockUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockRecommendation {

	private static final Logger LOGGER = Logger.getLogger(StockRecommendation.class.getName());

	private static final String HEX_VALUE_FOR_DOUBLE_QUOTE = "%22";
	private static final String YAHOO_FINANCE_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20({0})%20and%20startDate%20=%20%22{1}%22%20and%20endDate%20=%20%22{2}%22&format=json&env=store://datatables.org/alltableswithkeys";
	private static final int NUMBER_OF_DAYS_DIFFERENCE = -14;
	private static final int NUMBER_OF_TOP_STOCKS = 5;

	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	public JSONObject getStockRecommendations(String marketCap) throws JSONException, IOException {
		Calendar cal = Calendar.getInstance();
		String todaysDate = dateFormatter.format(cal.getTime());
		LOGGER.info("Todays Date:" + todaysDate);

		cal.add(Calendar.DATE, NUMBER_OF_DAYS_DIFFERENCE);
		String historicalDate = dateFormatter.format(cal.getTime());
		LOGGER.info("Start Date:" + historicalDate);

		StockDAO stockDAO = new StockDAO();
		List stocksForSelectedCAP = stockDAO.getStocksForMarketCap(marketCap);

		/*List stocksForSelectedCAP = new ArrayList<>();
		stocksForSelectedCAP.add("HSBC");
		stocksForSelectedCAP.add("HCA");
		stocksForSelectedCAP.add("MSFT");
		stocksForSelectedCAP.add("AAPL");
		stocksForSelectedCAP.add("GOOGL");*/

		String stockQueryStr = "";
		for (int i = 0; i < stocksForSelectedCAP.size(); i++) {
			String stockSymbol = (String) stocksForSelectedCAP.get(i);
			stockQueryStr = stockQueryStr + HEX_VALUE_FOR_DOUBLE_QUOTE + stockSymbol + HEX_VALUE_FOR_DOUBLE_QUOTE + ",";
		}
		stockQueryStr = stockQueryStr.substring(0, stockQueryStr.length() - 1);
		LOGGER.info("stockQueryStr :" + stockQueryStr);

		JSONObject jsonResponse = StockUtils
				.readJsonFromUrl(MessageFormat.format(YAHOO_FINANCE_URL, stockQueryStr, historicalDate, todaysDate));
		LOGGER.info("json Response for all stocks in cap :" + jsonResponse);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = jsonResponse.toString();
		TradeHistoricalData tradeData = mapper.readValue(jsonInString, TradeHistoricalData.class);

		List<Quote> quoteList = tradeData.getQuery().getResults().getQuote();
		HashMap<String, List> tickerQuoteListMap = new HashMap<>();

		for (Quote quoteObj : quoteList) {
			LOGGER.info("quoteObj.getSymbol():" + quoteObj.getSymbol());
			List quoteListForTicker = tickerQuoteListMap.get(quoteObj.getSymbol());
			if (null == quoteListForTicker) {
				quoteListForTicker = new ArrayList<>();
			}
			quoteListForTicker.add(quoteObj);
			tickerQuoteListMap.put(quoteObj.getSymbol(), quoteListForTicker);
		}

		Comparator desendingSlopeComp = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Double d1 = (Double) o1;
				Double d2 = (Double) o2;
				return d2.compareTo(d1);
			}
		};
		TreeMap quoteTickersMap = new TreeMap(desendingSlopeComp);
		for (Map.Entry<String, List> entry : tickerQuoteListMap.entrySet()) {
			String ticker = entry.getKey();
			LOGGER.info("ticker:" + ticker);
			List quotes = entry.getValue();
			Collections.reverse(quotes);
			double arrayForSlope[][] = new double[quotes.size()][2];
			int xAxisIndex = 0;

			for (Iterator iterator = quotes.iterator(); iterator.hasNext();) {

				Quote quoteObj = (Quote) iterator.next();
				double openValue = Double.parseDouble(quoteObj.getOpen());

				Date quotedDate = null;
				try {
					quotedDate = (Date) dateFormatter.parse(quoteObj.getDate());
				} catch (ParseException e) { // TODO Auto-generated catch block
					e.printStackTrace();
					LOGGER.info("Exception occurred while parsing date : " + e.getMessage());
				}
				LOGGER.info("quotedDate.getTime() : " + quotedDate.getTime());
				arrayForSlope[xAxisIndex][0] = Double.parseDouble(quotedDate.getTime() + "");
				arrayForSlope[xAxisIndex][1] = openValue;
				xAxisIndex++;

			}
			LOGGER.info("arrayForSlope::");
			for (int i = 0; i < arrayForSlope.length; i++) {
				LOGGER.info(arrayForSlope[i][0] + " , " + arrayForSlope[i][1]);
			}
			
			SimpleRegression simpleRegression = new SimpleRegression(true);

			simpleRegression.addData(arrayForSlope);
			double slopeOfTicker = simpleRegression.getSlope();
			LOGGER.info("slope = " + slopeOfTicker);
			quoteTickersMap.put(slopeOfTicker, ticker);
			LOGGER.info(ticker + ":" + slopeOfTicker);
		}
		LOGGER.info("Printing all slopes:");
		for (Object key : quoteTickersMap.keySet()) {
			LOGGER.info((Double) key + "  ,   " + quoteTickersMap.get(key));
		}
		LOGGER.info("Printing top 5 slopes:");
		JSONArray jsonArray = new JSONArray();
		JSONObject stockData = null;
		JSONObject stockListObject = new JSONObject();

		int counter = 0;

		for (Object key : quoteTickersMap.keySet()) {
			counter++;
			List quoteListForTicker = tickerQuoteListMap.get(quoteTickersMap.get(key));
			String stocksymbol = (String) quoteTickersMap.get(key);
			String stockprice = ((Quote) quoteListForTicker.get(quoteListForTicker.size() - 1)).getOpen();
			
			// slope, ticker, todaysOpenPrice
			LOGGER.info((Double) key + "  ,   " + quoteTickersMap.get(key) + "  ,   " + stockprice);

			stockData = new JSONObject();
			stockData.put("stocksymbol", stocksymbol);
			stockData.put("stockprice", stockprice);

			jsonArray.add(stockData);
			if (counter == NUMBER_OF_TOP_STOCKS) {
				break;
			}
		}
		stockListObject.put("stocks", jsonArray);
		LOGGER.info("stockListObject in the DAO:" + stockListObject);

		return stockListObject;
	}

	/*public static void main(String[] args) throws JSONException, IOException {
		new StockRecommendation().getStockRecommendations("LargeCap");
	}*/

}
