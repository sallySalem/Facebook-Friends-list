package facebook.example.com.facebookfriendslist.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import facebook.example.com.facebookfriendslist.data.model.FriendItemData;

/**
 * Created by Sally on 15-Jan-17.
 */

public class FriendItemDeserializer implements JsonDeserializer<FriendItemData>{
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String PICTURE = "picture";

    @Override
    public FriendItemData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FriendItemData friendItem = new FriendItemData();
        JsonObject jsonObject = json.getAsJsonObject();
        friendItem.setId(jsonObject.get(ID).getAsString());
        friendItem.setName(jsonObject.get(NAME).getAsString());
        friendItem.setPicture(jsonObject.get(PICTURE).getAsString());
        return friendItem;
    }
}