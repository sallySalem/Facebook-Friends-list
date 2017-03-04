package facebook.example.com.facebookfriendslist.ui.friendsList;

import java.util.ArrayList;

import facebook.example.com.facebookfriendslist.model.FriendItem;

/**
 * Created by Sally on 01-Jan-17.
 */

public interface FriendsListView {
    void initializeView();

    void loadFriendsList(ArrayList<FriendItem> friendsList);
}
