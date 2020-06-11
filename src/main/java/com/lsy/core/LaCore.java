package com.lsy.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LaCore {
	static String urlHome = "https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=";
	static String urlAjax = "https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false&page=2";
	static CookieStore cookieStore = new BasicCookieStore();
	static List<CompanyInfo> list = new ArrayList<CompanyInfo>();
	public static void main(String[] args) throws Exception {

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		for (int i = 1; i < 7; i++) {
			// CloseableHttpClient httpClient = HttpClients.createDefault();

			getCookie(httpClient);
			int page = i;
			String keyword = "JAVA";
			HttpPost httpPost = new HttpPost(urlAjax);
			List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
			nameValuePairList.add(new BasicNameValuePair("first", "true"));
			nameValuePairList.add(new BasicNameValuePair("pn", page + ""));
			nameValuePairList.add(new BasicNameValuePair("kd", keyword));
			// post添加请求body
			HttpEntity entity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
			httpPost.setEntity(entity);
			// 添加请求头
			httpPost.addHeader("Host", "www.lagou.com");
			httpPost.addHeader("Accept", "'application/json, text/javascript, */*; q=0.01");
			httpPost.addHeader("Referer",
					"'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
			httpPost.addHeader("User-Agent",
					"Mozilla/5.0(X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			CloseableHttpResponse response = httpClient.execute(httpPost);
			// 输出得到的结果
			//System.out.println(EntityUtils.toString(response.getEntity()));
			String resBody=EntityUtils.toString(response.getEntity());
			JSONObject responseBody = JSON.parseObject(resBody);
			JSONObject data = (JSONObject) responseBody.get("content");
			getCompanyInfo(data);

			// 关闭HttpEntity输出流
			EntityUtils.consume(response.getEntity());
			System.out.println(list.size()+"@@@@@@"+page);
		}
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		list.forEach(item->System.out.println(item));
	}

	// 获取搜索页的Cookie
	public static void getCookie(CloseableHttpClient httpClient) {
		HttpGet httpGet = new HttpGet(urlHome);
		// 清除Cookie起到复用的效果
		cookieStore.clear();
		httpGet.addHeader("Host", "www.lagou.com");
		httpGet.addHeader("Accept", "'application/json, text/javascript, */*; q=0.01");
		httpGet.addHeader("Referer",
				"'https://www.lagou.com/jobs/list_Java/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=");
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			System.out.println(EntityUtils.toString(response.getEntity()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + ":" + cookie.getValue());
		}
	}

	public static void getCompanyInfo(JSONObject data) {
		JSONObject MapJSon = (JSONObject) data.get("positionResult");
		// JSONObject MapJSons = (JSONObject) MapJSon.get("locationInfo");
		JSONArray jsonArray = MapJSon.getJSONArray("result");
		List<CompanyInfo> compList = new ArrayList<CompanyInfo>();
		// 公司相关信息以及基本要求
		compList = JSON.parseArray(jsonArray.toString(), CompanyInfo.class);
		compList.forEach(item->{list.add(item);System.out.println("每次请求："+item);});
	}
}
