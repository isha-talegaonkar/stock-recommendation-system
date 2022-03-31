package com.citi.trade.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

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
@PrepareForTest({ AutheticationDAO.class, DBConnection.class })
public class AutheticationDAOTest {

	@Test
	public void testAuthenticateUser() throws SQLException {
		Connection connection = Mockito.mock(Connection.class);
		PreparedStatement preparedStatemet = Mockito.mock(PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);
		
		PowerMockito.mockStatic(DBConnection.class);
		PowerMockito.when(DBConnection.createConnection()).thenReturn(connection);
		
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatemet);
		
		when(preparedStatemet.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true,false);
		when(resultSet.getString(anyString())).thenReturn("1");
		
		JSONObject jsonObj = new AutheticationDAO().authenticateUser("userName", "password");
		assertTrue(jsonObj.get("userId").equals("1"));
		System.out.println("jsonObj:"+jsonObj);
		
	}

}
