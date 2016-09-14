package org.androidtown.delightteammodule.Connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.androidtown.delightteammodule.ChanRecycleAdapter.AdapterItemData;
import org.androidtown.delightteammodule.ChanRecycleAdapter.CustomViewHolder;
import org.androidtown.delightteammodule.ChanRecycleAdapter.RecyclerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Chan on 2016-07-19.
 */
public class ConnectionManager<V extends  CustomViewHolder, D extends AdapterItemData> {

    private static AtomicInteger messageID = new AtomicInteger(0);

    final static String SERVER_URL = "http://172.30.1.254:808/hello_jsp/receiver.jsp";

    private Class<D> cls;
    private RecyclerAdapter<V, D> adapter;
    //의문점 : message에 json String 을 그냥 넣어버리면 서버에서 어떻게 받는가
    //제대로 받지 않는다면 HttpUtilSend 의 message 처리 부분 코드를 수정해야함.
    //아마 안 될것같음
    //sendJsonToServer 라는 method 를 따로 만들자.
    //2016-07-19

    public static ConnectionManager ConnectionManagerBuilder(Class<?> cl, RecyclerAdapter<?, ?> adapter)
    { //Data class와 Data로 set할 adapter 를 설정한다.
        return new ConnectionManager(cl,adapter);
    }

    private ConnectionManager(Class<D> cl, RecyclerAdapter<V, D> adapter)
    {
        cls=cl;
        this.adapter=adapter;
    }
    public static void sendMessageToServer(Context context, String message)
    {
        HttpUtilSend httpUtilSend = new HttpUtilSend(context);
        String id = Integer.toString(messageID.incrementAndGet());
        String[] string_parameters = {SERVER_URL, "KEY:"+id, "message:"+message};
        httpUtilSend.execute(string_parameters);
    }

    public static void sendJsonToServer(Context context, String json)
    {
        /*이 메소드는 데이터를 전송 한 후 callBack 작동이 필요 없을 때 사용, 콜백이 필요한경우 HttpUtilSend를 직접 제어하는게 나을듯.*/
    }

    public static String DataToJson(AdapterItemData item)
    {
        Gson gson = new Gson();
        return gson.toJson(item);
    }

    public static String DataToJson(Object item)
    {
        Gson gson = new Gson();
        return gson.toJson(item);
    }

    // 단일 객체만 포함한 Json 이 날라왔을 때 가능
    public static AdapterItemData DataFromJson(String Json, Class<AdapterItemData> cl)
    {
        Gson gson = new Gson();
        return gson.fromJson(Json, cl);
    }

    /*
    public static AdapterItemData[] DataArrayFromJson(String Json,Class<AdapterItemData> cl, String keyWord)
    {

    }
    */
    public static boolean setUpRecyclerViewWithJson(String json , RecyclerAdapter<CustomViewHolder, AdapterItemData> adapter)
    {
        //1. URL을 통해서 서버에서 JSON 받기
        //2. 받은 Json 을 DataArrayFromJson 또는 DataFromJson 메소드로 AdapterItemData 가져오기
        //3. adapter 에 addItem() 호출 (Array 이면 여러번 호출)
        //4. 만약 1이 실패하면 return false;
        return true;
    }



    //Builder에 의해 생성된 객체에서 사용하는 것들
    private void DataFromJson(String Json)
    {
        if(adapter==null || cls==null) {
            Log.d("ConnectionManager", "adapter or cls is null");
            return;
        }
        Gson gson = new Gson();
        adapter.addItem(gson.fromJson(Json, cls));
    }


    private void DataArrayFromJson(String Json , String keyWord)
    {
        if(adapter==null || cls==null) {
            Log.d("ConnectionManager", "adapter or cls is null");
            return;
        }
        Gson gson = new Gson();
        try {
            JSONObject jason = new JSONObject(Json);
            JSONArray jArray = jason.getJSONArray(keyWord); //미리 결정된 키워드 ex)select , 어쩌구..
            Log.d("DataArrayFromJson", "length =" + jArray.length());
            if (jArray.length() > 0) {
                for (int i = 0; i < jArray.length(); i++) {
                    //2016-07-20
                    //지금 이 방식은 JSON Array 로 만든 다음에 JSON Object 하나씩 뽑아내서 다시 string 으로 바꾸는데
                    //그냥 string 으로 작업하는 건 없을까? JSON Array 로 만들지 말고
                    adapter.addItem(gson.fromJson(jArray.getJSONObject(i).toString() , cls));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean setUpRecyclerViewWithJsonObject(String json) //이 json은 우리가 넣는게아님, HttpUtilReceive 에서 넣어주는거
    {
        //1. URL을 통해서 서버에서 JSON 받기 , 2016 07 20 현재 HttpUtilReceive object 가 그 역할을 함.
        //2. 받은 Json 을 DataFromJson 메소드로 AdapterItemData 가져오기
        DataFromJson(json); //아마 DataArrayFromJson 이걸로 교체되야 할 듯.

        //3. 만약 1이 실패하면 return false;
        return true;
    }
    public boolean setUpRecyclerViewWithJsonArray(String json, String keyword)
    {
        //1. URL을 통해서 서버에서 JSON 받기 , 2016 07 20 현재 HttpUtilReceive object 가 그 역할을 함.
        //2. 받은 Json 을 DataArrayFromJson 메소드로 AdapterItemData 가져오기
        DataArrayFromJson(json, keyword); //

        //3. 만약 1이 실패하면 return false;
        return true;
    }

    //ConnectionManager 와 HttpUtilReceive 동시 사용예제
    /*
    Adapter => adapter;
    통신할 URL => url
    HttpUtilReceive util = new HttpUtilReceive(ConnectionManager.ConnectionManagerBuilder(TeamSimpleData.class, adapter));
    util.execute(url);
     */
}
