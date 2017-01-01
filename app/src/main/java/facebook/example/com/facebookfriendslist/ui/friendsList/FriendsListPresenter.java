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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sally on 01-Jan-17.
 */

public class FriendsListPresenter {
    private FriendsListView view;
    private ArrayList<FriendItem> friendsList = new ArrayList<FriendItem>();

    public FriendsListPresenter(FriendsListView view) {
        this.view = view;

        initialize();
    }

    private void initialize() {
        view.initializeView();
    }

    public void onGetFBFriendsList() {
        AccessToken fbToken = AccessToken.getCurrentAccessToken();

        //fbToken return from login with facebook
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/taggable_friends", new GraphRequest.Callback() {

                    @Override
                    public void onCompleted(GraphResponse response) {
                        parseResponse(response.getJSONObject());
                    }
                }
        ).executeAsync();
    }

    private void parseResponse(JSONObject friends ) {

        try {
//            JSONObject friends = response.getJSONObject();
//            JSONObject friends = new JSONObject(response);
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
                String next = friends.getJSONObject("paging")
                        .getString("next");
                if (next != null) {
                    getFBFriendsList(next);
                } else {
                    view.loadFriendsList(friendsList);
                }
            }
        } catch (JSONException e1) {
            view.loadFriendsList(friendsList);
            e1.printStackTrace();
        }
    }

    private void getFBFriendsList(String next) {
        //here i used volley to get next page
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, next,
                new Response.Listener<String>() {
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(sr);
    }

}
