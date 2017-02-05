package facebook.example.com.facebookfriendslist.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import facebook.example.com.facebookfriendslist.data.model.FriendItemData;
import facebook.example.com.facebookfriendslist.data.model.FriendsListResponse;
import facebook.example.com.facebookfriendslist.util.FriendItemDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sally on 08-Jan-17.
 */

public class ApiService {
    public static final String BASE_URL = "https://graph.facebook.com/";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(FriendsListResponse.class, new FriendItemDeserializer())
            .create();

    public static Retrofit getService() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        return retrofit;
    }
}
