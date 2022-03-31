package com.citi.trade.authentication;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.citi.trade.dao.AutheticationDAO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AuthenticationServlet.class, AutheticationDAO.class })
public class AuthenticationServletTest {
	private static final Logger LOGGER = Logger.getLogger(AuthenticationServletTest.class.getName());

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse_loginSuccessful() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		AutheticationDAO autheticationDAO = Mockito.mock(AutheticationDAO.class);

		String str = "{\"user\": \"abc\", \"pass\" : \"abc\"}";

		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(str.getBytes());

		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		when(request.getReader()).thenReturn(br);

		// PrintWriter writer = Mockito.mock(PrintWriter.class);
		PrintWriter writer = new PrintWriter("D:/LoginSuccessful.txt");
		when(response.getWriter()).thenReturn(writer);

		JSONObject userData = new JSONObject();
		userData.put("userId", "1");

		PowerMockito.whenNew(AutheticationDAO.class).withNoArguments().thenReturn(autheticationDAO);
		when(autheticationDAO.authenticateUser(anyString(), anyString())).thenReturn(userData);

		new AuthenticationServlet().doPost(request, response);

		assertTrue(
				FileUtils.readFileToString(new File("D:/LoginSuccessful.txt"), "UTF-8").contains("{\"userId\":\"1\"}"));
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse_loginFailed() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		AutheticationDAO autheticationDAO = Mockito.mock(AutheticationDAO.class);

		String str = "{\"user\": \"abc\", \"pass\" : \"abc\"}";

		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(str.getBytes());

		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		when(request.getReader()).thenReturn(br);

		PrintWriter writer = new PrintWriter("D:/LoginFailed.txt");
		when(response.getWriter()).thenReturn(writer);

		PowerMockito.whenNew(AutheticationDAO.class).withNoArguments().thenReturn(autheticationDAO);
		when(autheticationDAO.authenticateUser(anyString(), anyString())).thenReturn(null);

		new AuthenticationServlet().doPost(request, response);

		assertTrue(FileUtils.readFileToString(new File("D:/LoginFailed.txt"), "UTF-8").isEmpty());

		LOGGER.info("response.getWriter():" + response.getWriter());
	}

}
