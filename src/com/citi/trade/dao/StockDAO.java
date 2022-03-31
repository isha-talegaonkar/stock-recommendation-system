package com.citi.trade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StockDAO {

	

	private static final Logger LOGGER = Logger.getLogger(StockDAO.class.getName());

	private static final String SELECT_STOCK_FOR_MARKETCAP_QUERY = "SELECT * FROM  STOCK_FOR_CAP WHERE MARKETCAP = ?";
	private static final String SELECT_SAVED_STOCK_QUERY = "SELECT * FROM  USER_SAVED_STOCK WHERE USER_ID = ?";
	private static final String INSERT_STOCK_QUERY = "INSERT INTO USER_SAVED_STOCK VALUES(?,?,?,?)";

	private static final String STOCK_QUANTITY = "stockquantity";
	private static final String STOCK_PRICE = "stockprice";
	private static final String STOCK_SYMBOL = "stocksymbol";
	private static final String STOCKS = "stocks";
	
	/**
	 * This method saves the stocks selected by user
	 * 
	 * @param userId
	 * @param stockArray
	 * @return true if stocks are saved successfully into database
	 */
	public boolean saveStocks(String userId, JSONArray stockArray) {
		for (int i = 0; i < stockArray.size(); i++) {
			Object object = stockArray.get(i);
			LOGGER.info("Object from Array::" + object);
			JSONObject stockData = (JSONObject) object;
			String stockSymbol = (String) stockData.get(STOCK_SYMBOL);
			LOGGER.info("stockSymbol:" + stockSymbol);
			String stockPrice = (String) stockData.get(STOCK_PRICE);
			LOGGER.info("stockPrice:" + stockPrice);
			int stockQuantity = Integer.parseInt((String) stockData.get(STOCK_QUANTITY));
			LOGGER.info("stockQuantity:" + stockQuantity);
			try {
				Connection con = DBConnection.createConnection();
				PreparedStatement pst = con.prepareStatement(INSERT_STOCK_QUERY);
				pst.setString(1, userId);
				pst.setString(2, stockSymbol);
				pst.setString(3, stockPrice);
				pst.setInt(4, stockQuantity);
				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("Error occured while saving the user selected stocks : " + e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * This method gets the user selected stocks from the database
	 * 
	 * @param userId
	 * @return json object containing user selected sock
	 */
	public JSONObject getSavedStocks(String userId) {
		JSONArray stockList = new JSONArray();
		JSONObject stockData = null;
		JSONObject stockListObject = new JSONObject();
		try {
			Connection con = DBConnection.createConnection();
			PreparedStatement pst = con.prepareStatement(SELECT_SAVED_STOCK_QUERY);
			pst.setString(1, userId);
			ResultSet rs = pst.executeQuery();

			if (null != rs) {
				while (rs.next()) {
					String stockSymbol = rs.getString(STOCK_SYMBOL);
					LOGGER.info("stocksymbol:" + stockSymbol);
					String stockPrice = rs.getString(STOCK_PRICE);
					LOGGER.info("stockprice:" + stockPrice);
					String stockQuantity = rs.getString(STOCK_QUANTITY);
					LOGGER.info("stockquantity:" + stockQuantity);

					stockData = new JSONObject();
					stockData.put(STOCK_SYMBOL, stockSymbol);
					stockData.put(STOCK_PRICE, stockPrice);
					stockData.put(STOCK_QUANTITY, stockQuantity);

					stockList.add(stockData);
					stockListObject.put(STOCKS, stockList);
				}
			}
			LOGGER.info("stockListObject in the DAO:" + stockListObject);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error occured while getting the user selected stocks : " + e.getMessage());
		}
		return stockListObject;
	}

	/**
	 * This method gets the stocks configured for the selected market cap
	 * 
	 * @param marketCap
	 * @return list of stock symbol for the selected cap
	 */
	public List getStocksForMarketCap(String marketCap) {
		List stockList = new ArrayList();
		try {
			Connection con = DBConnection.createConnection();
			PreparedStatement pst = con.prepareStatement(SELECT_STOCK_FOR_MARKETCAP_QUERY);
			pst.setString(1, marketCap);
			ResultSet rs = pst.executeQuery();

			if (null != rs) {
				while (rs.next()) {
					String stockSymbol = rs.getString(STOCK_SYMBOL);
					stockList.add(stockSymbol);
				}
			}
			LOGGER.info("stockList for selected cap:" + stockList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error occured while getting the stocks for selected market cap : " + e.getMessage());
		}
		return stockList;
	}
}
