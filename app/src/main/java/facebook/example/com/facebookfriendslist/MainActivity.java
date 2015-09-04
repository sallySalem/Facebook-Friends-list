package facebook.example.com.facebookfriendslist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    private TextView tvLoginResult;
    private LoginButton btnFBLogin;
    private Button btnFBLoginCustom;
    private Button btnShowFriendsList;

    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        tvLoginResult = (TextView) findViewById(R.id.tv_LoginResult);
        btnFBLogin = (LoginButton) findViewById(R.id.fbButton);
        btnShowFriendsList = (Button) findViewById(R.id.but_ShowFriendsList);

        //init the callback manager
        callbackManager = CallbackManager.Factory.create();

        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                btnShowFriendsList.setVisibility(View.VISIBLE);
                tvLoginResult.setText( "Success Login" + "\n"+
                        "User = " + loginResult.getAccessToken().getUserId() + "\n" +
                                "Token = " + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                btnShowFriendsList.setVisibility(View.VISIBLE);
                tvLoginResult.setText( "Success Login" + "\n"+
                                "User = " + loginResult.getAccessToken().getUserId() + "\n" +
                                "Token = " + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        //to pass Results to your facebook callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fbLoginUsingLoginManager(View view) {
        //"user_friends" this will return only the common friends using this app
        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "user_friends", "email"));
    }
}
