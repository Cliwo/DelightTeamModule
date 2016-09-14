package org.androidtown.delightteammodule.SQLite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MENU.MenuActivity;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.SIGNUP.LinkAssorter;
import org.androidtown.delightteammodule.SIGNUP.SignUp;
import org.androidtown.delightteammodule.SIGNUP.SignUpData;
import org.androidtown.delightteammodule.SIGNUP.Welcome;

/**
 * Created by Chan on 2016-08-14.
 */

// http://loveiskey.tistory.com/185 을 참조했습니다.
// Reference : http://loveiskey.tistory.com/185


public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;

    Context mContext;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }
    public static DBHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        //항상 '가장 먼저 실행되는 액티비티' 에서 이 메소드를 호출한 다음,
        //아래의 getInstance를 호출해야지 정상작동한다.
        if(dbHelper == null) {
            dbHelper = new DBHelper(context, name, factory, version);
            Log.d("DBHelper", "DBhelper first Init");
        }
        return dbHelper;
    }

    public static DBHelper getInstance()
    {
        if(dbHelper == null)
            Log.d("DBHelper", "NULL");
        return dbHelper;
    }

    // DB가 존재하지 않을 때 딱 한 번 실행된다. DB생성

    //첫번째 테이블 이름 : TEST_TABLE
    //두번째 테이블 이름 : MY_INFORMATION_TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //onCreate가 호출되는 순간에 이미 db는 만들어져있는 것.
        //아래코드는 db에 테이블을 추가하는 코드이고, db를 만드는 코드가 아니다.
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE ");
        sb.append("TEST_TABLE"+ " ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, " );
 //       sb.append(" NAME TEXT, ");
        sb.append(" BIRTH TEXT, ");
        sb.append(" SOCIAL_ID TEXT, ");
        sb.append(" SOCIAL_TYPE TEXT, ");
        sb.append(" NICKNAME TEXT, ");
 //       sb.append(" BIRTH_DATE TEXT, ");
        sb.append(" PROFILE_IMG TEXT, ");
        sb.append(" REGIONONE TEXT, ");
        sb.append(" REGIONTWO TEXT, ");
        sb.append(" REGIONTHREE TEXT); ");

        db.execSQL(sb.toString());

        Log.d("DB", "TABLE 생성 완료");

        Log.d("DB", "DB 생성 완료");

        Log.d("DB", "Redirect 작동, 회원 가입");
        Intent intent = new Intent(mContext, Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

    //App의 버전이 올라가 Table 구조가 달라졌을때 실행된다.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //2016-09-10 상으로 지금 region의 regionName만 저장하는대, 원래는 코드를 저장해야함.
    //추후에 수정해야함.
    public void addSignUpData(SignUpData data)
    {
        SQLiteDatabase db = getWritableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO TEST_TABLE ( ");
        sb.append(" BIRTH, ");
        sb.append("SOCIAL_ID, SOCIAL_TYPE,NICKNAME ,PROFILE_IMG, REGIONONE , REGIONTWO , REGIONTHREE )");
        sb.append(" VALUES ( '#BIRTH#' ,");
        sb.append("  '#SOCIAL_ID#' , '#SOCIAL_TYPE#' , '#NICKNAME#'  , '#PROFILE_IMG#' , '#REGIONONE#' , '#REGIONTWO#' , '#REGIONTHREE#')");


        String query = sb.toString();
        //query= query.replace("#NAME#", ""+data.nickName);
        query= query.replace("#BIRTH#", ""+data.birth.getDateByFormat(ChanDate.SLASH_SLASH_SLASH_WITHOUT_ZERO));
        query= query.replace("#SOCIAL_ID#", ""+data.socialID);
        query= query.replace("#SOCIAL_TYPE#", ""+ (data.linkAssort== LinkAssorter.KAKAO? "KAKAO" : "FACEBOOK"));
        query= query.replace("#NICKNAME#", ""+data.nickName);
        //query= query.replace("#BIRTH_DATE#", ""+data.nickName);
        query= query.replace("#PROFILE_IMG#", ""+data.imageURL);
        if(data.regions[0] != null)
            query= query.replace("#REGIONONE#", ""+data.regions[0].regionName);
        else
            query= query.replace("#REGIONONE#", ""+"NULL");
        if(data.regions[1] != null)
            query= query.replace("#REGIONTWO#", ""+data.regions[1].regionName);
        else
            query= query.replace("#REGIONTWO#", ""+"NULL");
        if(data.regions[2] != null)
            query= query.replace("#REGIONTHREE#", ""+data.regions[2].regionName);
        else
            query= query.replace("#REGIONTHREE#", ""+"NULL");

        db.execSQL(query);
        //입력에선 보통 execSQL을 주로쓴다.
        Log.d("DB", query);
        //Log.d("DB", "입력되지 않은 정보 있음, accessToken :" + data.accessToken + " 등등.. ");
        //MyInformationData.setImageUri(data.imageURL);
    }


    public String getSignUpData()
    {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT _ID, BIRTH, SOCIAL_ID , SOCIAL_TYPE , NICKNAME , PROFILE_IMG , REGIONONE , REGIONTWO, REGIONTHREE FROM TEST_TABLE ");
            SQLiteDatabase db = getReadableDatabase();
            if(db==null)
            {
                return null; //앱이 처음실행되는 경우를 예상함.
            }
            Cursor cursor = db.rawQuery(sb.toString(), null);
            Gson gson = new Gson();
            StringBuilder result = new StringBuilder();

            while(cursor.moveToNext())
            {
                //id birth social_id social_type nickname profileURL region 1 2 3
                String socialId = cursor.getString(2);
                String birth = cursor.getString(1);
                String social_type = cursor.getString(3);
                String nickName = cursor.getString(4);
                String imageURL = cursor.getString(5);
                String[] regions = new String[3];
                int j =0 ;
                for(int i = 6; i<9; i++)
                {
                    String str = cursor.getString(i);
                    if(str==null)
                        continue;
                    else
                        regions[j++]=str;
                }
                SignUpData data = new SignUpData(socialId, birth, imageURL, nickName, social_type, regions);
                result.append(gson.toJson(data) + "   ");
            }
            return result.toString();
        }
        catch(SQLiteCantOpenDatabaseException e)
        {
            e.printStackTrace();
            Log.d("SQLite", "테이블 읽기 실패");
            return null;
        }
        //조회시 rawQuery 사용한다.
    }

    public void makeTable(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE ");
        sb.append(name+ " ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, " );
        sb.append(" NAME TEXT, ");
        sb.append(" BIRTH TEXT); ");

        db.execSQL(sb.toString());

        Log.d("DB", "TABLE 생성 완료");
    }
    public void dropTable(String name)
    {
        //http://blog.naver.com/shylove2456/150112924093
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(name);

        try{
            db.execSQL(sb.toString());
            Log.d("DB","Table(" + name + ") 삭제 완료!");
        }catch (SQLException se) {
            Log.d("dropTable()Error! : ", se.toString());
        }

    }
}
