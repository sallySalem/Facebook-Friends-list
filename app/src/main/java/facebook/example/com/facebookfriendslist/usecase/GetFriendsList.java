package facebook.example.com.facebookfriendslist.usecase;

import android.util.Log;

import com.android.volley.Response;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;

import facebook.example.com.facebookfriendslist.data.ApiService;
import facebook.example.com.facebookfriendslist.data.model.FriendItemData;
import facebook.example.com.facebookfriendslist.data.model.FriendsListResponse;
import facebook.example.com.facebookfriendslist.data.service.FacebookFriendList;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Sally on 08-Jan-17.
 */

public class GetFriendsList {

   /* public void getTaggableFriends(AccessToken fbToken, GraphRequest.Callback graphRequestCallback) {
        //fbToken return from login with facebook
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/taggable_friends", graphRequestCallback
        ).executeAsync();
    }*/

    public void getFBFriendsList(String userId, String accessToken, int limit, String afterPage, Callback<FriendsListResponse> friendsListCallback) {
        FacebookFriendList facebookListService = ApiService.getService().create(FacebookFriendList.class);
        Call<FriendsListResponse> call = facebookListService.getFriendsList(userId, accessToken, limit, afterPage);
        call.enqueue(friendsListCallback);
    }
}
