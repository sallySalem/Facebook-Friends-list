package facebook.example.com.facebookfriendslist.ui.friendsList;

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

import facebook.example.com.facebookfriendslist.model.FriendItem;
import facebook.example.com.facebookfriendslist.usecase.GetFriendsList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sally on 01-Jan-17.
 */

public class FriendsListPresenter {
    private String userId;
    private AccessToken fbToken;
    private FriendsListView view;
    private ArrayList<FriendItem> friendsList = new ArrayList<FriendItem>();
    private GetFriendsList friendsListUseCase = new GetFriendsList();

    public FriendsListPresenter(FriendsListView view) {
        this.view = view;

        initialize();
    }

    private void initialize() {
        view.initializeView();
    }

    public void onGetFBFriendsList() {
        fbToken = AccessToken.getCurrentAccessToken();
        userId = AccessToken.getCurrentAccessToken().getUserId();
        friendsListUseCase.getTaggableFriends(fbToken, graphRequestCallback);
    }

    private void parseResponse(JSONObject friends) {
        try {
            JSONArray friendsArray = (JSONArray) friends.get("data");
            if (friendsArray != null) {
                for (int i = 0; i < friendsArray.length(); i++) {
                    FriendItem item = new FriendItem();
                    try {
                        item.setUserId(friendsArray.getJSONObject(i).get("id") + "");
                        item.setUserName(friendsArray.getJSONObject(i).get("name") + "");
                        JSONObject picObject = new JSONObject(friendsArray.getJSONObject(i).get("picture") + "");
                        String picURL = (String) (new JSONObject(picObject.get("data").toString())).get("url");
                        item.setPictureURL(picURL);
                        friendsList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // facebook use paging if have "next" this mean you still have friends if not start load fbFriends list
                String next = friends.getJSONObject("paging").getString("next");
                String after = friends.getJSONObject("paging").getJSONObject("cursors").getString("after");
                if (next != null) {
                    //friendsListUseCase.getFBFriendsList(next, fbListCallback);
                    friendsListUseCase.getFBFriendsList(userId, fbToken.getToken(), 25, after);
                } else {
                    view.loadFriendsList(friendsList);
                }
            }
        } catch (JSONException e1) {
            view.loadFriendsList(friendsList);
            e1.printStackTrace();
        }
    }

    private GraphRequest.Callback graphRequestCallback = new GraphRequest.Callback() {

        @Override
        public void onCompleted(GraphResponse response) {
            parseResponse(response.getJSONObject());
        }
    };

    private Response.Listener fbListCallback = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            JSONObject friends = null;
            try {
                friends = new JSONObject(response);
                parseResponse(friends);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
