package com.citi.trade.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class StockUtils {

	/**
	 * This method reads data from reader and creates string
	 * 
	 * @param rd
	 * @return string of
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int characterValue;
		while ((characterValue = rd.read()) != -1) {
			sb.append((char) characterValue);
		}
		return sb.toString();
	}

	/**
	 * This method gets the response for the given url and returns json
	 * 
	 * @param url
	 * @return json object
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream inputStream = new URL(url).openStream();
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = readAll(bufferedReader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			inputStream.close();
		}
	}
}
