package org.androidtown.delightteammodule.SIGNUP;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.ChanRegionSelectDialog;
import org.androidtown.delightteammodule.Gloabal.AddressAndLocation.GlobalRegionData;
import org.androidtown.delightteammodule.Gloabal.DateAndTime.ChanDate;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformation;
import org.androidtown.delightteammodule.MYINFORMATION.MyInformationData;
import org.androidtown.delightteammodule.R;
import com.facebook.FacebookSdk;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragLinkAccount extends Fragment{

    CallbackManager callbackManager; //facebook
    LoginButton loginButtonForFacebook; //facebook
    com.kakao.usermgmt.LoginButton loginButtonForKakao; //kakao

    private SessionCallback callback; //Kakao
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() { //카카오 연동에서 로그인 성공시 카카오 연동 액티비티로 넘어갑니다.
            Log.d("Kakao", "onSessionOpened");
            redirectSignUpActivity();
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
                Log.d("Kakao", "onSessionFailed");
                return;
            }
            Log.d("Kakao", "onSessionFailed&NuLL");
            ((SignUp)getActivity()).setCurrentPage(0);
            Toast.makeText(getContext(),"오류가 발생했습니다. 다시 한번 시도해주세요.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data); //Facebook 인증 완료시 이 부분이 실행됨
                                                                        //이 부분이 실행되야지 정상적으로 Profile등을 뽑을 수 있다.
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.d("Kakao", "onActivityResult");
            //여기 실행안됨
            return;
        }
        if(resultCode == KakaoSignUp.KAKAO_SUCCESS)
            Log.d("Kakao", "Success");

        //카카오톡 연동시 아래안불리고, facebook연동시 아래가 불림.
        Log.d("FragLinkAccount", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void redirectSignUpActivity() //ForKakao
    {
        Intent intent = new Intent(getActivity(), KakaoSignUp.class);
        startActivity(intent);
        getActivity().finish();
    }
    public void redirectSignUpActivity(LoginResult loginResult) //ForFacebook
    {
        FacebookSignUp.setLoginResultFromLink(loginResult);
        Intent intent = new Intent(getContext(), FacebookSignUp.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void callOnClick(int value)
    {
        if(value == LinkAssorter.KAKAO)
        {
            loginButtonForKakao.callOnClick();
        }
        else if(value == LinkAssorter.FACEBOOK)
        {
            if(AccessToken.getCurrentAccessToken()!=null)
            {
                //Facebook으로 회원가입 하고자 하며 // 저번에 Facebook으로 이미 로그인을 한 번 했지만, 기초정보를 입력하지 못한경우
                Log.d("FragLinkAccount", "이미 페이스북으로 로그인 된 상태입니다.");
                Intent intent =new Intent(getContext(),FacebookSignUp.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
            loginButtonForFacebook.callOnClick();
        }
    }

    public void setUpKakaoThings(View view)
    {
        callback = new SessionCallback(); //따로 kakao 버튼에 대한 OnClick 을 만들 필요는 없는듯?
        Session.getCurrentSession().addCallback(callback);
        loginButtonForKakao = (com.kakao.usermgmt.LoginButton)view.findViewById(R.id.com_kakao_login);
        //Session.getCurrentSession().checkAndImplicitOpen();
        //위의 코드는 디버그시 문제가 되서 주석처리합니다.
        //이미 등록된 유저가 Session을 열려고하거나, 열 수 있을 경우 자동으로 로그인 버튼을 눌러버리는 상황이 발생합니다
        //즉, 개발중에 내 계정이 이 앱에 등록되어있다면
        //facebook 로그인을 시도할 기회없이, 자동으로 카카오연동으로 들어가버리기 때문에 주석처리 합니다.
    }
    public void setUpFacebookThings(View view)
    {

        loginButtonForFacebook = (LoginButton)view.findViewById(R.id.com_facebook_login);
        loginButtonForFacebook.setReadPermissions("public_profile"); //email 이 먼지 모르겠음 다른것도 존재함 user_friends
        // If using in a fragment
        loginButtonForFacebook.setFragment(this);
        // Other app specific specialization
        // Callback registration
        callbackManager = CallbackManager.Factory.create(); // Facebook sdk 용 callback manager
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        loginButtonForFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("onSucessFacebookSDK : ", loginResult.getAccessToken().getToken().toString());
                //AccessToken 을 볼려면 loginResult.getAccessToken().getToken() 일케해야함 loginResult.getAccessToken() 이것만으론 안됨
                //구글에 검색해보면 왜 그런지 이유 많이 나옴

                //Facebook 연동이 성공했을때, 성공한 결과를 Facebook 연동 회원가입 액티비티로 넘기고
                //액티비티를 실행합니다.
                redirectSignUpActivity(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("Error", exception.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_link_account, container, false);
        setUpKakaoThings(view);
        setUpFacebookThings(view);

        return view;
    }

    public FragLinkAccount() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


}
