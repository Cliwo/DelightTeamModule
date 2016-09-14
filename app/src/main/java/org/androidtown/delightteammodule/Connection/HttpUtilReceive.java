package org.androidtown.delightteammodule.Connection;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chan on 2016-07-20.
 */
public class HttpUtilReceive extends AsyncTask<String, Void, String> {

    private ConnectionManager connectionManager;
    private DataSetUpCallback callback;

    public HttpUtilReceive(ConnectionManager cm, DataSetUpCallback callback)
    {
        this.connectionManager=cm;
        this.callback=callback;
    }

    public HttpUtilReceive(ConnectionManager cm)
    {
        this.connectionManager=cm;
    }

    public HttpUtilReceive(DataSetUpCallback callback)
    {
        this.callback=callback;
    }
    @Override
    protected String doInBackground(String... params) {
        //url을 처음에 넣어줘야함.
        String json = null;
        try {
            URL url = new URL(params[0]);
            Log.d("HttpUtilReceive", "Do in background");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                //연결 제한 시간을 1/1000 초 단위로 지정합니다.
                //0이면 무한 대기입니다.
                conn.setConnectTimeout(10000);
                //캐쉬 사용여부를 지정합니다.
                conn.setUseCaches(false);
                //http 연결의 경우 요청방식을 지정할수 있습니다.
                //지정하지 않으면 디폴트인 GET 방식이 적용됩니다.
                //conn.setRequestMethod("GET" | "POST");

                //서버에 요청을 보내가 응답 결과를 받아옵니다.
                int resCode = conn.getResponseCode();
                Log.d("HttpUtilReceive","resCode=" +resCode);
                //요청이 정상적으로 전달되엇으면 HTTP_OK(200)이 리턴됩니다.
                //URL이 발견되지 않으면 HTTP_NOT_FOUND(404)가 리턴됩니다.
                //인증에 실패하면 HTTP_UNAUTHORIZED(401)가 리턴됩니다.
                if (resCode == HttpURLConnection.HTTP_OK) {
                    //요청에 성공했으면 getInputStream 메서드로 입력 스트림을 얻어 서버로부터 전송된 결과를 읽습니다.
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    //스트림을 직접읽으면 느리고 비효율 적이므로 버퍼를 지원하는 BufferedReader 객체를 사용합니다.
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (; ; ) {
                        String line = br.readLine();
                        if (line == null) break;
                        stringBuilder.append(line + "\n");
                    }
                    //JSONArray jasonArray = new JSONArray(stringBuilder.toString());
                    br.close();
                }
            }
            conn.disconnect();
        }
        catch(MalformedURLException e)
        {

        }
        catch(Exception e)
        {

        }
        return json;
    }

    @Override
    protected void onPostExecute(String result) {
        if(connectionManager!=null) {
            connectionManager.setUpRecyclerViewWithJsonObject(result);
            //Json Object가 아니라 Json Array가 담겨있다고 추정하고 method 호출하는거임
            //2016 07 31 기준으로 이상함... WithJsonArray 라는 메소드를 호출해야하고 , keyword를 상황에맞게 넣어주는걸로 코드 다시짜야함
            //퓨퓨
        }
        if(callback != null)
        {
            callback.onDataReceived(result);
        }

    }
}
