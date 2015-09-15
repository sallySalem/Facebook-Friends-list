package facebook.example.com.facebookfriendslist;

import android.app.DownloadManager;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
import com.facebook.GraphRequest.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import facebook.example.com.facebookfriendslist.adapter.FriendsAdapter;
import facebook.example.com.facebookfriendslist.model.FriendItem;


public class FBFriendsListActivity extends ActionBarActivity {

    private AccessToken fbToken = null;
    private ArrayList<FriendItem> friendsList = new ArrayList<FriendItem>();
    private SwipeRefreshLayout swipeLayout;
    private ListView lvFriendsList;
    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbfriends_list);

        lvFriendsList = (ListView) findViewById(R.id.lv_FriendsList);
        friendsAdapter = new FriendsAdapter(getApplicationContext(), friendsList);
        friendsAdapter.notifyDataSetChanged();
        lvFriendsList.setAdapter(friendsAdapter);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        }, 1000);
        swipeLayout.setRefreshing(true);

        fbToken = MainActivity.fbToken;
        getFBFriendsList();
    }

    private void getFBFriendsList() {
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/taggable_friends", new Callback() {

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
                        item.setUserId(friendsArray.getJSONObject(i).get(
                                "id")
                                + "");

                        item.setUserName(friendsArray.getJSONObject(i).get(
                                "name")
                                + "");
                        JSONObject picObject = new JSONObject(friendsArray
                                .getJSONObject(i).get("picture") + "");
                        String picURL = (String) (new JSONObject(picObject
                                .get("data").toString())).get("url");
                        item.setPictureURL(picURL);
                        friendsList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String next = friends.getJSONObject("paging")
                        .getString("next");
                if (next != null) {
                    getFBFriendsList(next);
                } else {
                    loadFriendsList();
                }
            }
        } catch (JSONException e1) {
            loadFriendsList();
            e1.printStackTrace();
        }
    }

    private void loadFriendsList() {
        swipeLayout.setRefreshing(false);
        if ((friendsList != null) && (friendsList.size() > 0)) {
            lvFriendsList.setVisibility(View.VISIBLE);
            friendsAdapter.notifyDataSetChanged();
        } else {
            lvFriendsList.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fbfriends_list, menu);
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
}
