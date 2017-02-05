package facebook.example.com.facebookfriendslist.usecase;

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
import com.google.gson.JsonElement;
import com.squareup.picasso.Downloader;

import java.util.HashMap;
import java.util.Map;

import facebook.example.com.facebookfriendslist.data.ApiService;
import facebook.example.com.facebookfriendslist.data.model.FriendItemData;
import facebook.example.com.facebookfriendslist.data.model.FriendsListResponse;
import facebook.example.com.facebookfriendslist.data.service.FacebookFriendList;
import retrofit2.Call;
import retrofit2.Callback;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sally on 08-Jan-17.
 */

public class GetFriendsList {
    public void getTaggableFriends(AccessToken fbToken, GraphRequest.Callback graphRequestCallback) {
        //fbToken return from login with facebook
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/taggable_friends", graphRequestCallback
        ).executeAsync();
    }

    public void getFBFriendsList(String userId, String accessToken, int limit, String afterPage) {
        FacebookFriendList facebookListService = ApiService.getService().create(FacebookFriendList.class);
        Call<FriendItemData> call = facebookListService.getMovieDetails(userId, accessToken, limit, afterPage);
        call.enqueue(new Callback<FriendItemData>() {
            @Override
            public void onResponse(Call<FriendItemData> call, retrofit2.Response<FriendItemData> response) {

                Log.i("----", response.toString());
            }

            @Override
            public void onFailure(Call<FriendItemData> call, Throwable t) {

            }
        });

    }
    
    public void getFBFriendsList(String next, Response.Listener fbListCallback) {

        /*
        //here i used volley to get next page
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, next, fbListCallback, new Response.ErrorListener() {
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
        queue.add(sr);*/
    }
}
