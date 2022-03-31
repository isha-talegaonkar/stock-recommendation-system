package com.citi.trade.stock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.citi.trade.dao.StockDAO;
import com.citi.trade.util.StockUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StockRecommendation.class, StockDAO.class, StockUtils.class })
public class StockRecommendationTest {
	private static final Logger LOGGER = Logger.getLogger(StockRecommendation.class.getName());

	@Test
	public void testGetStockRecommendations() throws Exception {
		StockDAO stockDAO = Mockito.mock(StockDAO.class);
		PowerMockito.whenNew(StockDAO.class).withNoArguments().thenReturn(stockDAO);
		List stocks = new ArrayList();
		stocks.add("HSBC");
		stocks.add("HCA");
		stocks.add("MSFT");
		stocks.add("AAPL");
		stocks.add("GOOGL");
		Mockito.when(stockDAO.getStocksForMarketCap(anyString())).thenReturn(stocks);
		String jsonResponseStr = "{\"query\":{\"created\":\"2017-01-20T19:36:22Z\",\"count\":40,\"lang\":\"en-US\",\"results\":{\"quote\":[{\"High\":\"41.919998\",\"Low\":\"41.490002\",\"Volume\":\"2156000\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.669998\",\"Close\":\"41.669998\",\"Date\":\"2017-01-19\",\"Open\":\"41.830002\"},{\"High\":\"41.98\",\"Low\":\"41.560001\",\"Volume\":\"3055700\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.91\",\"Close\":\"41.91\",\"Date\":\"2017-01-18\",\"Open\":\"41.790001\"},{\"High\":\"41.720001\",\"Low\":\"41.150002\",\"Volume\":\"1982500\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.18\",\"Close\":\"41.18\",\"Date\":\"2017-01-17\",\"Open\":\"41.700001\"},{\"High\":\"41.34\",\"Low\":\"41.080002\",\"Volume\":\"1591100\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.16\",\"Close\":\"41.16\",\"Date\":\"2017-01-13\",\"Open\":\"41.080002\"},{\"High\":\"41.34\",\"Low\":\"41.040001\",\"Volume\":\"1311300\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.18\",\"Close\":\"41.18\",\"Date\":\"2017-01-12\",\"Open\":\"41.259998\"},{\"High\":\"41.349998\",\"Low\":\"40.82\",\"Volume\":\"1913500\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"41.349998\",\"Close\":\"41.349998\",\"Date\":\"2017-01-11\",\"Open\":\"40.990002\"},{\"High\":\"41.07\",\"Low\":\"40.709999\",\"Volume\":\"1754200\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"40.75\",\"Close\":\"40.75\",\"Date\":\"2017-01-10\",\"Open\":\"40.790001\"},{\"High\":\"40.790001\",\"Low\":\"40.619999\",\"Volume\":\"1891000\",\"Symbol\":\"HSBC\",\"Adj_Close\":\"40.66\",\"Close\":\"40.66\",\"Date\":\"2017-01-09\",\"Open\":\"40.759998\"},{\"High\":\"79.809998\",\"Low\":\"78.57\",\"Volume\":\"2560100\",\"Symbol\":\"HCA\",\"Adj_Close\":\"78.620003\",\"Close\":\"78.620003\",\"Date\":\"2017-01-19\",\"Open\":\"79.459999\"},{\"High\":\"81.330002\",\"Low\":\"79.639999\",\"Volume\":\"2845200\",\"Symbol\":\"HCA\",\"Adj_Close\":\"79.730003\",\"Close\":\"79.730003\",\"Date\":\"2017-01-18\",\"Open\":\"81.25\"},{\"High\":\"82.199997\",\"Low\":\"78.809998\",\"Volume\":\"3009600\",\"Symbol\":\"HCA\",\"Adj_Close\":\"80.93\",\"Close\":\"80.93\",\"Date\":\"2017-01-17\",\"Open\":\"80.980003\"},{\"High\":\"79.809998\",\"Low\":\"79.099998\",\"Volume\":\"1130800\",\"Symbol\":\"HCA\",\"Adj_Close\":\"79.400002\",\"Close\":\"79.400002\",\"Date\":\"2017-01-13\",\"Open\":\"79.239998\"},{\"High\":\"79.400002\",\"Low\":\"78.540001\",\"Volume\":\"1482900\",\"Symbol\":\"HCA\",\"Adj_Close\":\"79.120003\",\"Close\":\"79.120003\",\"Date\":\"2017-01-12\",\"Open\":\"79.139999\"},{\"High\":\"79.629997\",\"Low\":\"78.260002\",\"Volume\":\"1834700\",\"Symbol\":\"HCA\",\"Adj_Close\":\"79.309998\",\"Close\":\"79.309998\",\"Date\":\"2017-01-11\",\"Open\":\"78.639999\"},{\"High\":\"79.230003\",\"Low\":\"77.809998\",\"Volume\":\"2319500\",\"Symbol\":\"HCA\",\"Adj_Close\":\"78.269997\",\"Close\":\"78.269997\",\"Date\":\"2017-01-10\",\"Open\":\"78.839996\"},{\"High\":\"81.059998\",\"Low\":\"76.040001\",\"Volume\":\"4610500\",\"Symbol\":\"HCA\",\"Adj_Close\":\"78.919998\",\"Close\":\"78.919998\",\"Date\":\"2017-01-09\",\"Open\":\"76.040001\"},{\"High\":\"62.98\",\"Low\":\"62.200001\",\"Volume\":\"18435500\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.299999\",\"Close\":\"62.299999\",\"Date\":\"2017-01-19\",\"Open\":\"62.240002\"},{\"High\":\"62.700001\",\"Low\":\"62.119999\",\"Volume\":\"19646500\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.50\",\"Close\":\"62.50\",\"Date\":\"2017-01-18\",\"Open\":\"62.669998\"},{\"High\":\"62.700001\",\"Low\":\"62.029999\",\"Volume\":\"20620400\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.529999\",\"Close\":\"62.529999\",\"Date\":\"2017-01-17\",\"Open\":\"62.68\"},{\"High\":\"62.869999\",\"Low\":\"62.349998\",\"Volume\":\"19350400\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.700001\",\"Close\":\"62.700001\",\"Date\":\"2017-01-13\",\"Open\":\"62.619999\"},{\"High\":\"63.400002\",\"Low\":\"61.950001\",\"Volume\":\"20898900\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.610001\",\"Close\":\"62.610001\",\"Date\":\"2017-01-12\",\"Open\":\"63.060001\"},{\"High\":\"63.23\",\"Low\":\"62.43\",\"Volume\":\"21462800\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"63.189999\",\"Close\":\"63.189999\",\"Date\":\"2017-01-11\",\"Open\":\"62.610001\"},{\"High\":\"63.07\",\"Low\":\"62.279999\",\"Volume\":\"18547000\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.619999\",\"Close\":\"62.619999\",\"Date\":\"2017-01-10\",\"Open\":\"62.73\"},{\"High\":\"63.080002\",\"Low\":\"62.540001\",\"Volume\":\"20256600\",\"Symbol\":\"MSFT\",\"Adj_Close\":\"62.639999\",\"Close\":\"62.639999\",\"Date\":\"2017-01-09\",\"Open\":\"62.759998\"},{\"High\":\"120.089996\",\"Low\":\"119.370003\",\"Volume\":\"25295700\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.779999\",\"Close\":\"119.779999\",\"Date\":\"2017-01-19\",\"Open\":\"119.400002\"},{\"High\":\"120.50\",\"Low\":\"119.709999\",\"Volume\":\"23644700\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.989998\",\"Close\":\"119.989998\",\"Date\":\"2017-01-18\",\"Open\":\"120.00\"},{\"High\":\"120.239998\",\"Low\":\"118.220001\",\"Volume\":\"34078600\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"120.00\",\"Close\":\"120.00\",\"Date\":\"2017-01-17\",\"Open\":\"118.339996\"},{\"High\":\"119.620003\",\"Low\":\"118.809998\",\"Volume\":\"25938300\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.040001\",\"Close\":\"119.040001\",\"Date\":\"2017-01-13\",\"Open\":\"119.110001\"},{\"High\":\"119.300003\",\"Low\":\"118.209999\",\"Volume\":\"27002400\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.25\",\"Close\":\"119.25\",\"Date\":\"2017-01-12\",\"Open\":\"118.900002\"},{\"High\":\"119.93\",\"Low\":\"118.599998\",\"Volume\":\"27418600\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.75\",\"Close\":\"119.75\",\"Date\":\"2017-01-11\",\"Open\":\"118.739998\"},{\"High\":\"119.379997\",\"Low\":\"118.300003\",\"Volume\":\"24420800\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"119.110001\",\"Close\":\"119.110001\",\"Date\":\"2017-01-10\",\"Open\":\"118.769997\"},{\"High\":\"119.43\",\"Low\":\"117.940002\",\"Volume\":\"33387600\",\"Symbol\":\"AAPL\",\"Adj_Close\":\"118.989998\",\"Close\":\"118.989998\",\"Date\":\"2017-01-09\",\"Open\":\"117.949997\"},{\"High\":\"833.00\",\"Low\":\"823.960022\",\"Volume\":\"1068500\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"824.369995\",\"Close\":\"824.369995\",\"Date\":\"2017-01-19\",\"Open\":\"829.00\"},{\"High\":\"829.809998\",\"Low\":\"824.080017\",\"Volume\":\"1026000\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"829.02002\",\"Close\":\"829.02002\",\"Date\":\"2017-01-18\",\"Open\":\"829.799988\"},{\"High\":\"830.179993\",\"Low\":\"823.200012\",\"Volume\":\"1439700\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"827.460022\",\"Close\":\"827.460022\",\"Date\":\"2017-01-17\",\"Open\":\"830.00\"},{\"High\":\"834.650024\",\"Low\":\"829.52002\",\"Volume\":\"1288000\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"830.940002\",\"Close\":\"830.940002\",\"Date\":\"2017-01-13\",\"Open\":\"831.00\"},{\"High\":\"830.380005\",\"Low\":\"821.01001\",\"Volume\":\"1349500\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"829.530029\",\"Close\":\"829.530029\",\"Date\":\"2017-01-12\",\"Open\":\"828.380005\"},{\"High\":\"829.900024\",\"Low\":\"821.469971\",\"Volume\":\"1320200\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"829.859985\",\"Close\":\"829.859985\",\"Date\":\"2017-01-11\",\"Open\":\"826.619995\"},{\"High\":\"829.409973\",\"Low\":\"823.140015\",\"Volume\":\"1194500\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"826.01001\",\"Close\":\"826.01001\",\"Date\":\"2017-01-10\",\"Open\":\"827.070007\"},{\"High\":\"830.429993\",\"Low\":\"821.619995\",\"Volume\":\"1406800\",\"Symbol\":\"GOOGL\",\"Adj_Close\":\"827.179993\",\"Close\":\"827.179993\",\"Date\":\"2017-01-09\",\"Open\":\"826.369995\"}]}}}";
		PowerMockito.mockStatic(StockUtils.class);
		JSONObject jsonResponse = new JSONObject(jsonResponseStr);
		PowerMockito.when(StockUtils.readJsonFromUrl(anyString())).thenReturn(jsonResponse);

		JSONObject stockListObject = new StockRecommendation().getStockRecommendations("LargeCap");
		assertNotNull(stockListObject);
	}

}
