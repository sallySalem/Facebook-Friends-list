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
    private String nextPageId;
    private GetFriendsList friendsListUseCase = new GetFriendsList();
    private String previousPageId;
    private boolean isLoadingMore = false;

    public FriendsListPresenter(FriendsListView view) {
        this.view = view;
        view.initializeView();
    }

    public void onGetFBFriendsList() {
        fbToken = AccessToken.getCurrentAccessToken();
        userId = AccessToken.getCurrentAccessToken().getUserId();
        friendsListUseCase.getFBFriendsList(userId, fbToken.getToken(), PAGE_SIZE, nextPageId, friendsListCallback);
    }

    public void onLoadMore(int totalItemsCount, int visibleItemsCount, int firstVisibleItemPosition) {
        if ((nextPageId != null) && !isLoadingMore && (visibleItemsCount + firstVisibleItemPosition >= totalItemsCount)) {
            //loadMore
            isLoadingMore = true;
            friendsListUseCase.getFBFriendsList(userId, fbToken.getToken(), PAGE_SIZE, nextPageId, friendsListCallback);
        }
    }

    private final Callback<FriendsListResponse> friendsListCallback = new Callback<FriendsListResponse>() {
        @Override
        public void onResponse(Call<FriendsListResponse> call, retrofit2.Response<FriendsListResponse> response) {
            isLoadingMore = false;
            if (response.isSuccessful()) {
                FriendsListResponse responseResult = response.body();
                nextPageId = responseResult.getNextPageId();
                previousPageId = responseResult.getPreviousPageId();
                ArrayList<FriendItemData> newFriendsList = responseResult.getFriendsDataList();

                //Get Correct insert Index
                int index = 0;
                if (friendsList.size() > 0) {
                    index = friendsList.size() - 1;
                }
                friendsList.addAll(index, newFriendsList);

                if ((nextPageId != null)) {
                    //Add Null object for loading more item
                    if (index == 0) {
                        friendsList.add(null);
                    }
                } else {
                    //Remove loading more item
                    index = friendsList.size() - 1;
                    if (friendsList.get(index) == null) {
                        friendsList.remove(index);
                    }
                }

                view.loadFriendsList(friendsList);
            } else {
                //TODO show error message
            }
        }

        @Override
        public void onFailure(Call<FriendsListResponse> call, Throwable t) {
            isLoadingMore = false;
            //TODO Show error message
        }
    };
}
