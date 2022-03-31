package com.citi.trade.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StockDAO.class, DBConnection.class })
public class StockDAOTest {

	@Test
	public void testSaveStocks() throws SQLException {

		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatemet = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		PowerMockito.mockStatic(DBConnection.class);
		PowerMockito.when(DBConnection.createConnection()).thenReturn(connection);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatemet);

		when(preparedStatemet.executeUpdate()).thenReturn(1);

		JSONArray jsonArray = new JSONArray();
		JSONObject stockData = new JSONObject();
		stockData.put("stocksymbol", "APPL");
		stockData.put("stockprice", "100");
		stockData.put("stockquantity", "10");

		jsonArray.add(stockData);
		boolean isStockeSaved = new StockDAO().saveStocks("userName", jsonArray);
		assertTrue(isStockeSaved);

	}

	@Test
	public void testGetSavedStocks() throws SQLException {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatemet = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		PowerMockito.mockStatic(DBConnection.class);
		PowerMockito.when(DBConnection.createConnection()).thenReturn(connection);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatemet);

		when(preparedStatemet.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true, false);
		when(resultSet.getString(anyString())).thenReturn("1");

		JSONObject jsonObj = new StockDAO().getSavedStocks("userId");
		JSONArray jsonArray = (JSONArray) jsonObj.get("stocks");
		JSONObject stockData = (JSONObject) jsonArray.get(0);
		assertTrue(stockData.get("stockquantity").equals("1"));
	}

	@Test
	public void testGetStocksForMarketCap() throws SQLException {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatemet = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		PowerMockito.mockStatic(DBConnection.class);
		PowerMockito.when(DBConnection.createConnection()).thenReturn(connection);

		when(connection.prepareStatement(anyString())).thenReturn(preparedStatemet);

		when(preparedStatemet.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true, false);
		when(resultSet.getString(anyString())).thenReturn("APPL");

		List stocksForMarketCap = new StockDAO().getStocksForMarketCap("smallCap");
		assertTrue(stocksForMarketCap.get(0).equals("APPL"));
		System.out.println("stocksForMarketCap:" + stocksForMarketCap);
	}

}
