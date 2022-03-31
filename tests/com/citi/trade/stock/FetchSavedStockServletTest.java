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

import com.citi.trade.dao.StockDAO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ FetchSavedStockServlet.class, StockDAO.class })
public class FetchSavedStockServletTest {

	private static final Logger LOGGER = Logger.getLogger(FetchSavedStockServletTest.class.getName());

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse_SuccessfulData() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StockDAO stockDAO = Mockito.mock(StockDAO.class);

		when(request.getParameter("userId")).thenReturn("abc");

		PrintWriter writer = new PrintWriter("D:/fetchSavedStock.txt");
		when(response.getWriter()).thenReturn(writer);

		JSONArray list = new JSONArray();
		JSONObject stockListObject = new JSONObject();

		JSONObject stockData = new JSONObject();
		stockData.put("stocksymbol", "APPL");
		stockData.put("stockprice", "100.00");
		stockData.put("stockquantity", "10");

		list.add(stockData);
		stockListObject.put("stocks", list);

		PowerMockito.whenNew(StockDAO.class).withNoArguments().thenReturn(stockDAO);
		when(stockDAO.getSavedStocks(anyString())).thenReturn(stockListObject);

		new FetchSavedStockServlet().doGet(request, response);

		assertTrue(FileUtils.readFileToString(new File("D:/fetchSavedStock.txt"), "UTF-8")
				.contains("\"stocksymbol\":\"APPL\""));
	}

	@Test
	public void testDoGetHttpServletRequestHttpServletResponse_NoData() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StockDAO stockDAO = Mockito.mock(StockDAO.class);

		when(request.getParameter("userId")).thenReturn("abc");

		PrintWriter writer = new PrintWriter("D:/fetchSavedStock.txt");
		when(response.getWriter()).thenReturn(writer);

		PowerMockito.whenNew(StockDAO.class).withNoArguments().thenReturn(stockDAO);
		when(stockDAO.getSavedStocks(anyString())).thenReturn(null);

		new FetchSavedStockServlet().doGet(request, response);

		assertTrue(FileUtils.readFileToString(new File("D:/fetchSavedStock.txt"), "UTF-8").isEmpty());
	}

}
