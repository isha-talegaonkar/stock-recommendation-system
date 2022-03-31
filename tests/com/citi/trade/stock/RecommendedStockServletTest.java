package com.citi.trade.stock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RecommendedStockServlet.class, StockRecommendation.class })

public class RecommendedStockServletTest {

	private static final Logger LOGGER = Logger.getLogger(RecommendedStockServlet.class.getName());

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse_SuccessfulData() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StockRecommendation stockRecommendation = Mockito.mock(StockRecommendation.class);

		when(request.getParameter("selectedCap")).thenReturn("smallCap");

		PrintWriter writer = new PrintWriter("D:/recommendedStock.txt");
		when(response.getWriter()).thenReturn(writer);

		JSONArray list = new JSONArray();
		org.json.JSONObject stockListObject = new org.json.JSONObject();

		JSONObject stockData = new JSONObject();
		stockData.put("stocksymbol", "APPL");
		stockData.put("stockprice", "100.00");
		stockData.put("stockquantity", "10");

		list.add(stockData);
		stockListObject.put("stocks", list);

		PowerMockito.whenNew(StockRecommendation.class).withNoArguments().thenReturn(stockRecommendation);
		when(stockRecommendation.getStockRecommendations(anyString())).thenReturn(stockListObject);

		new RecommendedStockServlet().doGet(request, response);

		assertTrue(FileUtils.readFileToString(new File("D:/recommendedStock.txt"), "UTF-8")
				.contains("\"stocksymbol\":\"APPL\""));
	}

}
