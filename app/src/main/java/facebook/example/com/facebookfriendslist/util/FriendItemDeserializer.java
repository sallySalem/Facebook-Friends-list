package facebook.example.com.facebookfriendslist.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import facebook.example.com.facebookfriendslist.data.model.FriendItemData;
import facebook.example.com.facebookfriendslist.data.model.FriendsListResponse;

/**
 * Created by Sally on 15-Jan-17.
 */

public class FriendItemDeserializer implements JsonDeserializer<FriendsListResponse> {
    private final String ID_KEY = "id";
    private final String NAME_KEY = "name";
    private final String PICTURE_KEY = "picture";
    private final String URL_KEY = "url";
    private final String AFTER_KEY = "after";
    private final String BEFORE_KEY = "before";
    private final String DATA_KEY = "data";
    private final String PAGING_KEY = "paging";
    private final String CURSORS_KEY = "cursors";
    private final String NEXT_KEY = "next";

    @Override
    public FriendsListResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FriendsListResponse response = new FriendsListResponse();
        JsonObject friends = json.getAsJsonObject();
        JsonArray friendsArray = (JsonArray) friends.get(DATA_KEY);
        ArrayList<FriendItemData> friendItemDataList = new ArrayList<>();
        if (friendsArray != null) {
            for (int i = 0; i < friendsArray.size(); i++) {
                JsonObject itemJsonObject = friendsArray.get(i).getAsJsonObject();
                FriendItemData item = new FriendItemData();
                item.setId(itemJsonObject.get(ID_KEY).getAsString());
                item.setName(itemJsonObject.get(NAME_KEY).getAsString());
                String picUrl = itemJsonObject.getAsJsonObject(PICTURE_KEY).getAsJsonObject(DATA_KEY).get(URL_KEY).getAsString();
                item.setPicture(picUrl);
                friendItemDataList.add(item);
            }
            JsonObject pagingObject = friends.getAsJsonObject(PAGING_KEY);
            if (pagingObject.has(NEXT_KEY)) {
                JsonObject cursorsObject = pagingObject.getAsJsonObject(CURSORS_KEY);
                String nextPageId = cursorsObject.get(AFTER_KEY).getAsString();
                String previousPageId = cursorsObject.get(BEFORE_KEY).getAsString();
                response.setNextPageId(nextPageId);
                response.setPreviousPageId(previousPageId);
            }
            response.setFriendsDataList(friendItemDataList);
        }
        return response;
    }
}