package facebook.example.com.facebookfriendslist.ui.friendsList;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import facebook.example.com.facebookfriendslist.R;
import facebook.example.com.facebookfriendslist.adapter.FriendsAdapter;
import facebook.example.com.facebookfriendslist.data.model.FriendItemData;


public class FriendsListActivity extends ActionBarActivity implements FriendsListView{
    private ArrayList<FriendItemData> friendsList = new ArrayList<FriendItemData>();
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
    public void loadFriendsList(ArrayList<FriendItemData> fLst) {
        friendsList.removeAll(friendsList);
        friendsList.addAll(fLst);
        swipeLayout.setRefreshing(false);

        if ((friendsList != null) && (friendsList.size() > 0)) {
            lvFriendsList.setVisibility(View.VISIBLE);
            friendsAdapter.notifyDataSetChanged();
        } else {
            lvFriendsList.setVisibility(View.GONE);
        }
    }
}
