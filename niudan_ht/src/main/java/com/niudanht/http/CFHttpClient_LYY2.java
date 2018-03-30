package com.niudanht.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONException;

import com.niudanht.util.Md5;

public class CFHttpClient_LYY2 {

	public final static String api_key = "1001";
	public final static String key = "54f74fbfb94518a527a36474dc904c25";

	// private final static String HTTP_URL =
	// "http://211.154.164.55:20011/AutoDevice/AutoDevice/";
	private final static String HTTP_URL = "http://bz.kuailetongyao.com:20011/AutoDevice/AutoDevice/";
	//
	private static String sign;
	//
	private static String request_time;

	//
	// private static String str;
	// private static String str1;

	public static String setData(String Code) throws JSONException {
		// str = URLEncoder.encode(JSON.toJSONString(Code));
		// str1 = JSON.toJSONString(Code);

		request_time = getTime();
		Md5 md5 = new Md5();
		String paramString = String.format(
				"api_key=%s&device_info=%s&request_time=%s&key=%s", api_key,
				Code, request_time, key);
		System.out.println("-----=" + paramString);
		try {
			sign = md5.getMd5(paramString.getBytes());
			// sign = "39653E9A4BBC3088404A0E6941EB8AD4";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 多设备查询
		String Url = String
				.format("%sdeviceStatus?api_key=%s&sign=%s&device_info=%s&request_time=%s",
						HTTP_URL, api_key, sign, Code, request_time);
		String result = sendGetRequest(Url);

		return result;
	}

	public static String setStartup(String Code) throws JSONException {
		// str = URLEncoder.encode(JSON.toJSONString(Code));
		// str1 = JSON.toJSONString(Code);
		request_time = getTime();
		Md5 md5 = new Md5();
		String paramString = String
				.format("api_key=%s&coins=0&device_info=%s&machine_info=1&order_id=%s&request_time=%s&key=%s",
						api_key, Code, request_time, request_time, key);
		try {
			System.out.println("----1---paramString=" + paramString);
			sign = md5.getMd5(paramString.getBytes());
			System.out.println("----1---sign=" + sign);
			// sign = "39653E9A4BBC3088404A0E6941EB8AD4";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 多设备查询
		String Url = String
				.format("%sstartDevice?api_key=%s&sign=%s&device_info=%s&coins=0&machine_info=1&order_id=%s&request_time=%s",
						HTTP_URL, api_key, sign, Code, request_time,
						request_time);
		System.out.println("----1---Url=" + Url);
		String result = sendGetRequest(Url);

		return result;
	}

	public static String sendGetRequest(String getUrl) throws JSONException {
		StringBuffer sb = new StringBuffer();
		// InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			URL url = new URL(getUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setAllowUserInteraction(false);
			// isr = new InputStreamReader(url.openStream());
			// br = new BufferedReader(isr);
			br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				// if (isr != null)
				// isr.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		String dateName = df.format(calendar.getTime());
		return dateName;
	}
}
