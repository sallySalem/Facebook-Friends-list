package facebook.example.com.facebookfriendslist.ui.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import facebook.example.com.facebookfriendslist.ui.friendsList.FriendsListActivity;
import facebook.example.com.facebookfriendslist.R;


public class MainActivity extends ActionBarActivity implements MainView, View.OnClickListener {

    private MainPresenter mainPresenter;
    private TextView tvLoginResult;
    private LoginButton btnFBLogin;
    private Button btnShowFriendsList;
    private Button btnLoginFBLoginManager;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
    }

    @Override
    public void initializeFBSdk() {
        //init the callback manager
        callbackManager = CallbackManager.Factory.create();
        btnFBLogin.registerCallback(callbackManager, fbLoginCallback);
        LoginManager.getInstance().registerCallback(callbackManager, fbLoginCallback);
    }

    @Override
    public void initializeView() {
        tvLoginResult = (TextView) findViewById(R.id.tv_LoginResult);
        btnFBLogin = (LoginButton) findViewById(R.id.fbButton);
        btnShowFriendsList = (Button) findViewById(R.id.btn_showFriendsList);
        btnLoginFBLoginManager = (Button) findViewById(R.id.but_LoginFBLoginManager);
        btnShowFriendsList.setOnClickListener(this);
        btnLoginFBLoginManager.setOnClickListener(this);
    }

    @Override
    public void showFriendsList() {
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFBLoginResult(AccessToken fbAccessToken) {
        btnShowFriendsList.setVisibility(View.VISIBLE);
        tvLoginResult.setText( getString(R.string.success_login) + "\n"+
                getString(R.string.user) + fbAccessToken.getUserId() + "\n" +
                getString(R.string.token) + fbAccessToken.getToken()
        );
    }

    @Override
    public void loginUsingFBManager() {
        //"user_friends" this will return only the common friends using this app
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "user_friends", "email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //to pass Results to your facebook callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_showFriendsList:
                mainPresenter.onShowFriendsListButtonClicked();
                break;
            case R.id.but_LoginFBLoginManager:
                mainPresenter.onLoginUsingFBManagerClicked();
                break;
        }
    }


    private final FacebookCallback fbLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mainPresenter.onFBLoginSuccess(loginResult);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };
}
