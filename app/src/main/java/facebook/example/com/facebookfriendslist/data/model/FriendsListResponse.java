package facebook.example.com.facebookfriendslist.data.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sally on 08-Jan-17.
 */

public class FriendsListResponse {
    @SerializedName("data")
    private ArrayList<FriendItemData> data;
    @SerializedName("paging")
    private Object paging;

    public Object getPaging() {
        return paging;
    }

    public void setPaging(Object paging) {
        this.paging = paging;
    }

    public ArrayList<FriendItemData> getData() {
        return data;
    }

    public void setData(ArrayList<FriendItemData> data) {
        this.data = data;
    }
}
