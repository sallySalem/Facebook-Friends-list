package facebook.example.com.facebookfriendslist.ui.main;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

/**
 * Created by Sally on 01-Jan-17.
 */

public class MainPresenter {
    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;

        initialize();
    }

    private void initialize() {
        view.initializeView();
        view.initializeFBSdk();
        checkFBLoginStatus();
    }

    private void checkFBLoginStatus() {
        AccessToken fbAccessToken = AccessToken.getCurrentAccessToken();
        if (fbAccessToken != null) {
            view.showFBLoginResult(fbAccessToken);
        }
    }

    public void onShowFriendsListButtonClicked() {
        view.showFriendsList();
    }

    public void onFBLoginSuccess(LoginResult loginResult) {
        view.showFBLoginResult(loginResult.getAccessToken());
    }

    public void onLoginUsingFBManagerClicked() {
        view.loginUsingFBManager();
    }
}
