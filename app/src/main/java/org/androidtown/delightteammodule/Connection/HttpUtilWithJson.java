package org.androidtown.delightteammodule.Connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by Chan on 2016-08-28.
 */
public class HttpUtilWithJson extends AsyncTask<String, String ,String> {
    private DataSetUpCallback callback;

    public static final String Http = "Http";
    public static final String URLBase = "http://footballnow.ap-northeast-2.elasticbeanstalk.com";

    public HttpUtilWithJson(DataSetUpCallback callback)
    {
        this.callback=callback;
    }
    public HttpUtilWithJson()
    {

    }

    @Override
    protected void onPreExecute() {
        // 호출 전
        Log.d(Http, "************************************************* 서버 호출 선행");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(Http,"************************************************* 서버 호출" );
        String paramString;
        String url = params[0];
        try {
            paramString = getQueryString(params[1]);
            Log.d("HttpUtilSend", "Query : " + paramString);

            Log.d(Http,"************************************************* 서버 호출 url : " + url);
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
        Log.d(Http,"************************************************* 서버 완료 후 행");
        if(result.equals("") || result == null)
        {
            Log.d("HttpSend", "result 가 정상적이지 않습니다. 통신이 실패한것으로 보입니다.");
            return ;
        }
        if(callback!=null)
            callback.onDataReceived(result);

    }

    //method from stackoverflow ..
    public String getQueryString(String unparsedString) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject json = new JSONObject(unparsedString);
            Iterator<String> keys = json.keys();
            sb.append("?"); //start of query args
            while (keys.hasNext()) {
                String key = keys.next();
                sb.append(key);
                sb.append("=");
                sb.append(json.get(key));
                sb.append("&"); //To allow for another argument.
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            Log.d("GetQueryStringfrom제이슨", "에러 발생");
        }
        return sb.toString();
    }


}
