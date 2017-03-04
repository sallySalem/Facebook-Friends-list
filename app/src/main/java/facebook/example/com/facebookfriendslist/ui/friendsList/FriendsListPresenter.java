package facebook.example.com.facebookfriendslist.ui.friendsList;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import facebook.example.com.facebookfriendslist.data.model.FriendItemData;
import facebook.example.com.facebookfriendslist.data.model.FriendsListResponse;
import facebook.example.com.facebookfriendslist.usecase.GetFriendsList;
import retrofit2.Call;
import retrofit2.Callback;

import static com.facebook.FacebookSdk.getApplicationContext;
import static facebook.example.com.facebookfriendslist.util.Constants.PAGE_SIZE;

/**
 * Created by Sally on 01-Jan-17.
 */

public class FriendsListPresenter {
    private String userId;
    private AccessToken fbToken;
    private FriendsListView view;
    private ArrayList<FriendItemData> friendsList = new ArrayList<FriendItemData>();
    private GetFriendsList friendsListUseCase = new GetFriendsList();
    private String afterPageId = null;

    public FriendsListPresenter(FriendsListView view) {
        this.view = view;
        view.initializeView();
    }

    public void onGetFBFriendsList() {
        fbToken = AccessToken.getCurrentAccessToken();
        userId = AccessToken.getCurrentAccessToken().getUserId();
        friendsListUseCase.getFBFriendsList(userId, fbToken.getToken(), PAGE_SIZE, afterPageId, friendsListCallback);
    }

    private final Callback<FriendsListResponse> friendsListCallback = new Callback<FriendsListResponse>() {
        @Override
        public void onResponse(Call<FriendsListResponse> call, retrofit2.Response<FriendsListResponse> response) {
            view.loadFriendsList(response.body().getFriendsDataList());
        }

        @Override
        public void onFailure(Call<FriendsListResponse> call, Throwable t) {
            //TODO Show error message
        }
    };
}
