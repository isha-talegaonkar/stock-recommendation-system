package com.citi.trade.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.citi.trade.constant.TradeApplicationConstants;
import com.citi.trade.dao.StockDAO;

public class FetchSavedStockServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(FetchSavedStockServlet.class.getName());

	/**
	 * This method returns the saved stocks for user
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("In FetchSavedStockServlet");
		response.setContentType(TradeApplicationConstants.JSON_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String userId = request.getParameter(TradeApplicationConstants.USERID);
		LOGGER.info("userId from the request:" + userId);
		StockDAO stockDAO = new StockDAO();
		JSONObject stockListObject = stockDAO.getSavedStocks(userId);
		if (null != stockListObject) {
			out.println(stockListObject);
			out.flush();
		}
		LOGGER.info("stockListObject in servlet:" + stockListObject);
	}
}
