package org.androidtown.delightteammodule.Connection;

/**
 * Created by Chan on 2016-07-19.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Chan on 2016-05-12.
 */
public class HttpUtilSend extends AsyncTask<String, String, String>{

    //출처 : http://cofs.tistory.com/179

    /* 2016.05.16 이 부분 추가
    * -> registerKey 등록 자동화 및, ACK, Client에서 발신 기능 추가 위해
    * */
    private Context context;
    private DataSetUpCallback callback;

    public static final String Http = "Http";
    public static final String URLBase = "http://footballnow.ap-northeast-2.elasticbeanstalk.com";

    public HttpUtilSend(Context context) {
        this.context = context;
    }
    public HttpUtilSend(DataSetUpCallback callback)
    {
        this.callback=callback;
    }

    @Override
    protected void onPreExecute() {
        // 호출 전
        Log.d(Http, "************************************************* 서버 호출 선행");
    }

    @Override
    //파라미터를 넣을때, url , 키1 , 값1 , 키2, 값2 ... 이런식으로 다 따로넣어야한다.
    public String doInBackground(String... params) {
        Log.d(Http,"************************************************* 서버 호출" );
        String paramString;
        String url = params[0];
        try {

            Map paramMap = new HashMap();
            for(int i=1;i<params.length; i+=2) //params[0]은 url이므로
                paramMap.put(params[i], params[i+1]);


            Log.d(Http,"************************************************* 서버 호출 url : " + url);
            paramString = urlEncodeUTF8(paramMap);

            URL obj = new URL(url + "?" + paramString); //params으로 도착한것을 짜집기해서 URL로 만든 것.
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); //url객체에서 openConnection을 한다.

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json"); //json 이용

//***************** 이 부분은 왜 있는걸까요? 이 코드만 다름..
            byte[] outputInBytes = params[0].getBytes("UTF-8"); //url에 저장된 값의 getBytes와 같은 문장.
            OutputStream os = conn.getOutputStream(); //연 Connection의 outputStream을 연결
            os.write( outputInBytes ); //발신
            os.close();
//*****************

            //이 HttpUtilSend 이라는건 송신말고 수신도 가능한것?

            int retCode = conn.getResponseCode(); //발신후 response를 int로 받음

            Log.d(Http,"************************************************* 서버 호출 결과 코드 : " + retCode );
            if (retCode == HttpURLConnection.HTTP_OK) { //ACK를 받는것 같음 HTTP 통신 기준으로
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                char[] buff = new char[512];
                int len = -1;
                StringBuffer response = new StringBuffer();
                while((len = br.read(buff)) != -1) { // ??
                    response.append(new String(buff, 0, len));  //읽어들인 것을 response에 붙인다.
                }
                br.close();
                Log.d(Http,"************************************************* 서버 호출 결과 text : " + response.toString());

                return response.toString(); //결과값 반환

            }else{
                Log.d(Http,"************************************************* 서버 호출 실패 code : " + retCode );
                //NCK가 도착했을때 결과 반환
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 호출이 끝난 후
    @Override
    protected void onPostExecute(String result) { //doInBackground 에서 도착
        Log.d(Http, "************************************************* 서버 완료 후 행");
        if (result.equals("") || result == null) {
            Log.d("HttpSend", "result 가 정상적이지 않습니다. 통신이 실패한것으로 보입니다.");
            return;
        }
        if (callback != null)
            callback.onDataReceived(result);

        //아래가 주석처리된 이유는 gcm을 이용해서 결과값을 받기 때문임. 2016-07-19
        /*
        try {
            // return 받은 Json 데이터
            rtn = URLDecoder.decode(object.getString("DATA"), "UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

    }
    //http://stackoverflow.com/questions/2809877/how-to-convert-map-to-url-query-string
    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
}
