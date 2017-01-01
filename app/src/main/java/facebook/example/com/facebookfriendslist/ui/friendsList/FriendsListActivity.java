package facebook.example.com.facebookfriendslist.ui.friendsList;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
import java.util.concurrent.RunnableFuture;

import facebook.example.com.facebookfriendslist.R;
import facebook.example.com.facebookfriendslist.adapter.FriendsAdapter;
import facebook.example.com.facebookfriendslist.model.FriendItem;
import facebook.example.com.facebookfriendslist.ui.main.MainActivity;


public class FriendsListActivity extends ActionBarActivity implements FriendsListView{
    private ArrayList<FriendItem> friendsList = new ArrayList<FriendItem>();
    private SwipeRefreshLayout swipeLayout;
    private ListView lvFriendsList;
    private FriendsAdapter friendsAdapter;
    private FriendsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbfriends_list);

        presenter = new FriendsListPresenter(this);
        presenter.onGetFBFriendsList();
    }

    @Override
    public void initializeView() {
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
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void loadFriendsList(ArrayList<FriendItem> fLst) {
        friendsList.removeAll(friendsList);
        this.friendsList.addAll(fLst);
        swipeLayout.setRefreshing(false);

        if ((friendsList != null) && (friendsList.size() > 0)) {
            lvFriendsList.setVisibility(View.VISIBLE);
            friendsAdapter.notifyDataSetChanged();
        } else {
            lvFriendsList.setVisibility(View.GONE);
        }
    }
}
