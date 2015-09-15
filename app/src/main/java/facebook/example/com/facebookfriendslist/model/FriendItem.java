package facebook.example.com.facebookfriendslist.model;

import java.io.Serializable;

/**
 * Created by Sally on 04-Sep-15.
 */
@SuppressWarnings("serial")
public class FriendItem implements Serializable {
    private String userId;
    private String userName;
    private String pictureURL;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
