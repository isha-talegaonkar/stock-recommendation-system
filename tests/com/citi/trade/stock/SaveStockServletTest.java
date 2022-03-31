package com.citi.trade.stock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.citi.trade.dao.StockDAO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SaveStockServlet.class, StockDAO.class })
public class SaveStockServletTest {

	private static final Logger LOGGER = Logger.getLogger(SaveStockServletTest.class.getName());

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse_loginSuccessful() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		StockDAO stockDAO = Mockito.mock(StockDAO.class);

		String str = "{\"stocks\":[{\"stockprice\":\"100.00\",\"stocksymbol\":\"APPL\",\"stockquantity\":\"10\"}]}";

		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(str.getBytes());

		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		when(request.getReader()).thenReturn(br);

		PrintWriter writer = new PrintWriter("D:/saveStock.txt");
		when(response.getWriter()).thenReturn(writer);

		PowerMockito.whenNew(StockDAO.class).withNoArguments().thenReturn(stockDAO);
		when(stockDAO.saveStocks(anyString(), any(JSONArray.class))).thenReturn(true);

		new SaveStockServlet().doPost(request, response);
		
	}

}
