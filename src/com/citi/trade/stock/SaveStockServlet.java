package com.citi.trade.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.citi.trade.constant.TradeApplicationConstants;
import com.citi.trade.dao.StockDAO;

public class SaveStockServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(SaveStockServlet.class.getName());
	private static final String USER_SELECTED_STOCKS = "selectedStocks";

	/**
	 * This method saves stock selected by user
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("In SaveStockServlet");
		response.setContentType(TradeApplicationConstants.JSON_CONTENT_TYPE);
		String selectedStocks = request.getParameter(USER_SELECTED_STOCKS);
		String userId = request.getParameter(TradeApplicationConstants.USERID);
		String lineContent;
		LOGGER.info("selectedStocks:" + selectedStocks);
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = request.getReader();
		while ((lineContent = br.readLine()) != null) {
			stringBuilder.append(lineContent);
			LOGGER.info("stringBuilder : " + stringBuilder);
		}

		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(stringBuilder.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			LOGGER.info("Error occured while parsing the string using json parser : " + e.getMessage());
		}
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray selectedStockJSONArray = (JSONArray) jsonObject.get(USER_SELECTED_STOCKS);

		StockDAO stockDAO = new StockDAO();
		boolean isSuccessful = stockDAO.saveStocks(userId, selectedStockJSONArray);
		if (isSuccessful) {
		} else {
			response.setStatus(400);
		}
	}
}
