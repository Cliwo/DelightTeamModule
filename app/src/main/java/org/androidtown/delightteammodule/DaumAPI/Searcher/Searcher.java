package org.androidtown.delightteammodule.DaumAPI.Searcher;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 실제로는 DaumAPI 내부에 들어있어야하는데, 없어서 강제 추가함. 추후에
// DaumAPI 업데이트로 Searcher 가 생기게 되면, 이 클래스를 대체할 것.
public class Searcher {
    // http://dna.daum.net/apis/local
	public static final String DAUM_MAPS_LOCAL_KEYWORD_SEARCH_API_FORMAT = "https://apis.daum.net/local/v1/search/keyword.json?query=%s&location=%f,%f&radius=%d&page=%d&apikey=%s";
	public static final String DAUM_MAPS_LOCAL_KEYWORD_SEARCH_WITHOUT_POINT_AND_RADIUS_API_FORMAT = "https://apis.daum.net/local/v1/search/keyword.json?query=%s&page=%d&apikey=%s";
	public static final String DAUM_MAPS_LOCAL_CATEGORY_SEARCH_API_FORMAT = "https://apis.daum.net/local/v1/search/category.json?code=%s&location=%f,%f&radius=%d&page=%d&apikey=%s";
	/** category codes
	MT1 대형마트
	CS2 편의점
	PS3 어린이집, 유치원
	SC4 학교
	AC5 학원
	PK6 주차장
	OL7 주유소, 충전소
	SW8 지하철역
	BK9 은행
	CT1 문화시설
	AG2 중개업소
	PO3 공공기관
	AT4 관광명소
	AD5 숙박
	FD6 음식점
	CE7 카페
	HP8 병원
	PM9 약국
	 */
	
	private static final String HEADER_NAME_X_APPID = "x-appid";
	private static final String HEADER_NAME_X_PLATFORM = "x-platform";
	private static final String HEADER_VALUE_X_PLATFORM_ANDROID = "android";
	
	OnFinishSearchListener onFinishSearchListener;
	SearchTask searchTask;
	String appId;
	
	private class SearchTask extends AsyncTask<String, Void, Void> {

		Boolean isPointBased;
		public SearchTask(Boolean isPointBased)
		{
			this.isPointBased=isPointBased;
		}

		@Override
		protected Void doInBackground(String... urls) {
			String url = urls[0];
			Map<String, String> header = new HashMap<String, String>();
			header.put(HEADER_NAME_X_APPID, appId);
			header.put(HEADER_NAME_X_PLATFORM, HEADER_VALUE_X_PLATFORM_ANDROID);
			String json = fetchData(url, header);
			List<Item> itemList;
			if(isPointBased)
				itemList = parse(json);
			else
				itemList = parseWithoutPointAndRadius(json);
			if (onFinishSearchListener != null) {
				if (itemList == null) {
					onFinishSearchListener.onFail();
				} else {
					onFinishSearchListener.onSuccess(itemList);
				}
			}
			return null;
		}
	}
	//외부에서 사용하는 메소드는 총 2개
	//아래의 1. searchKeyword 와
	public void searchKeyword(Context applicationContext, String query, double latitude, double longitude, int radius, int page, String apikey, OnFinishSearchListener onFinishSearchListener) {
    	this.onFinishSearchListener = onFinishSearchListener;
    	//검색 방식은, query 문을 이용해서 latitude, longitude 를 기준으로 radius 떨어진 거리 만큼을 search 해주는 것
		//페이지는 현재 잘 모르겟음 (16-08-28)
		if (searchTask != null) {
			searchTask.cancel(true);
			searchTask = null;
		}
		
		if (applicationContext != null) {
			appId = applicationContext.getPackageName();
		}
		String url = buildKeywordSearchApiUrlString(query, latitude, longitude, radius, page, apikey);
		searchTask = new SearchTask(true);
		searchTask.execute(url);
    }
	public void searchKeywordWithoutPointAndRadius(Context applicationContext, String query,  int page, String apikey, OnFinishSearchListener onFinishSearchListener) {
		this.onFinishSearchListener = onFinishSearchListener;
		//검색 방식은, query 문을 이용해서 latitude, longitude 를 기준으로 radius 떨어진 거리 만큼을 search 해주는 것
		//페이지는 현재 잘 모르겟음 (16-08-28)
		if (searchTask != null) {
			searchTask.cancel(true);
			searchTask = null;
		}

		if (applicationContext != null) {
			appId = applicationContext.getPackageName();
		}
		String url = buildKeywordSearchApiUrlStringWithoutPointAndRadius(query, page, apikey);
		searchTask = new SearchTask(false);
		searchTask.execute(url);
	}
	//2. searchCategory 이다.
	//이 두개의 메소드를 이용해서 검색하면 된다.
	public void searchCategory(Context applicationContext, String categoryCode, double latitude, double longitude, int radius, int page, String apikey, OnFinishSearchListener onFinishSearchListener) {
		this.onFinishSearchListener = onFinishSearchListener;
		
		if (searchTask != null) {
			searchTask.cancel(true);
			searchTask = null;
		}
		
		if (applicationContext != null) {
			appId = applicationContext.getPackageName();
		}
		String url = buildCategorySearchApiUrlString(categoryCode, latitude, longitude, radius, page, apikey);
		searchTask = new SearchTask(true);
		searchTask.execute(url);
	}
    
	private String buildKeywordSearchApiUrlString(String query, double latitude, double longitude, int radius, int page, String apikey) {
    	String encodedQuery = "";
		try {
			encodedQuery = URLEncoder.encode(query, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return String.format(DAUM_MAPS_LOCAL_KEYWORD_SEARCH_API_FORMAT, encodedQuery, latitude, longitude, radius, page, apikey);
    }
	private String buildKeywordSearchApiUrlStringWithoutPointAndRadius(String query, int page, String apikey) {
		String encodedQuery = "";
		try {
			encodedQuery = URLEncoder.encode(query, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return String.format(DAUM_MAPS_LOCAL_KEYWORD_SEARCH_WITHOUT_POINT_AND_RADIUS_API_FORMAT, encodedQuery, page, apikey);
	}

	
	private String buildCategorySearchApiUrlString(String categoryCode, double latitude, double longitude, int radius, int page, String apikey) {
		return String.format(DAUM_MAPS_LOCAL_CATEGORY_SEARCH_API_FORMAT, categoryCode, latitude, longitude, radius, page, apikey);
	}

	//만약, 네트워크 연결이 안될 때 강제종료 된다면..
	//http://hashcode.tistory.com/entry/안드로이드-다음-맵-API-사용하는-데-네트워크-연결이-안될-시-어떻게-처리해야-하나요
	//이 글 보기
	private String fetchData(String urlString, Map<String, String> header) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(4000 /* milliseconds */);
			conn.setConnectTimeout(7000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if (header != null) {
				for (String key : header.keySet()) {
					conn.addRequestProperty(key, header.get(key));
				}
			}
			conn.connect();
			InputStream is = conn.getInputStream();
			@SuppressWarnings("resource")
			Scanner s = new Scanner(is);
			s.useDelimiter("\\A");
			String data = s.hasNext() ? s.next() : "";
			is.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Item> parseWithoutPointAndRadius(String jsonString)
	{
		List<Item> itemList = new ArrayList<Item>();
		try {
			JSONObject reader = new JSONObject(jsonString);
			JSONObject channel = reader.getJSONObject("channel");
			JSONArray objects = channel.getJSONArray("item");
			for (int i = 0; i < objects.length(); i++) {
				JSONObject object = objects.getJSONObject(i);
				Item item = new Item();
				item.title = object.getString("title");
				item.imageUrl = object.getString("imageUrl");
				item.address = object.getString("address");
				item.newAddress = object.getString("newAddress");
				item.zipcode = object.getString("zipcode");
				item.phone = object.getString("phone");
				item.category = object.getString("category");
				item.latitude = object.getDouble("latitude");
				item.longitude = object.getDouble("longitude");
				//item.distance = object.getDouble("distance");
				item.direction = object.getString("direction");
				item.id = object.getString("id");
				item.placeUrl = object.getString("placeUrl");
				item.addressBCode = object.getString("addressBCode");
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return itemList;

	}


	private List<Item> parse(String jsonString) {
		List<Item> itemList = new ArrayList<Item>();
		try {
			JSONObject reader = new JSONObject(jsonString);
			JSONObject channel = reader.getJSONObject("channel");
			JSONArray objects = channel.getJSONArray("item");
			for (int i = 0; i < objects.length(); i++) {
				JSONObject object = objects.getJSONObject(i);
				Item item = new Item();
				item.title = object.getString("title");
				item.imageUrl = object.getString("imageUrl");
				item.address = object.getString("address");
				item.newAddress = object.getString("newAddress");
				item.zipcode = object.getString("zipcode");
				item.phone = object.getString("phone");
				item.category = object.getString("category");
				item.latitude = object.getDouble("latitude");
				item.longitude = object.getDouble("longitude");
				item.distance = object.getDouble("distance");
				item.direction = object.getString("direction");
				item.id = object.getString("id");
				item.placeUrl = object.getString("placeUrl");
				item.addressBCode = object.getString("addressBCode");
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return itemList;
	}
	
	public void cancel() {
		if (searchTask != null) {
			searchTask.cancel(true);
			searchTask = null;
		}
	}
}
