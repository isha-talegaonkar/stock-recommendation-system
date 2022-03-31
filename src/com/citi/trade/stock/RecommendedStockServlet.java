package com.citi.trade.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.citi.trade.constant.TradeApplicationConstants;

public class RecommendedStockServlet extends HttpServlet {
	
	
	private static final Logger LOGGER = Logger.getLogger(RecommendedStockServlet.class.getName());
	private static final String SELECTED_CAP = "selectedCap";

	/**
	 * This method returns stock recommendation for the selected market cap
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("In RecommendedStockServlet");
		response.setContentType(TradeApplicationConstants.JSON_CONTENT_TYPE);

		String marketCap = (String) request.getParameter(SELECTED_CAP);
		LOGGER.info("marketCap in the request : " + marketCap);
		JSONObject json = new StockRecommendation().getStockRecommendations(marketCap);
		LOGGER.info("json response in RecommendedStockServlet:" + json);

		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
	}

}