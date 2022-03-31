package com.citi.trade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.citi.trade.constant.TradeApplicationConstants;

public class AutheticationDAO {

	private static final Logger LOGGER = Logger.getLogger(AutheticationDAO.class.getName());
	private static final String SELECT_USER_QUERY = "SELECT USER_ID FROM USER_DATA WHERE USERNAME = ? AND PASSWORD = ?";
	private static final String USER_ID_COLUMN = "user_id";

	/**
	 * This method authenticates user details and returns userId if user is
	 * authenticated
	 * 
	 * @param userName
	 * @param password
	 * @return json object containing userid of the user
	 */
	public JSONObject authenticateUser(String userName, String password) {
		JSONObject userData = null;
		try {
			Connection con = DBConnection.createConnection();
			PreparedStatement pst = con.prepareStatement(SELECT_USER_QUERY);
			pst.setString(1, userName);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();

			if (null != rs) {
				while (rs.next()) {
					String userId = rs.getString(USER_ID_COLUMN);
					LOGGER.info("UserId for the current user:" + userId);
					userData = new JSONObject();
					userData.put(TradeApplicationConstants.USERID, userId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error occurred while authenticating user : " + e.getMessage());
		}
		return userData;
	}
}
