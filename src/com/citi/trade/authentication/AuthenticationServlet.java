package com.citi.trade.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.citi.trade.constant.TradeApplicationConstants;
import com.citi.trade.dao.AutheticationDAO;

public class AuthenticationServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(AuthenticationServlet.class.getName());
	private static final String PASSWORD_PARAMETER = "pass";
	private static final String USERNAME_PARAMETER = "user";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(TradeApplicationConstants.JSON_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String lineContent;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = request.getReader();
		while ((lineContent = br.readLine()) != null) {
			stringBuilder.append(lineContent);
		}
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(stringBuilder.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			LOGGER.info("Error occured while parsing stringBuilder using JSONParser :" + e.getMessage());
		}
		JSONObject jsonObject = (JSONObject) obj;
		String userName = (String) jsonObject.get(USERNAME_PARAMETER);
		String password = (String) jsonObject.get(PASSWORD_PARAMETER);

		LOGGER.info("userName in the request :" + userName);

		AutheticationDAO autheticationDAO = new AutheticationDAO();
		JSONObject userData = autheticationDAO.authenticateUser(userName, password);
		LOGGER.info("userData for the current user :" + userData);

		if (null != userData) {
			out.println(userData); // {"userId" : "1"}
			response.setStatus(200);
			out.flush();
		} else {
			response.setStatus(400);
		}
	}
}