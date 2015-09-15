package facebook.example.com.facebookfriendslist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import facebook.example.com.facebookfriendslist.R;
import facebook.example.com.facebookfriendslist.model.FriendItem;

/**
 * Created by Sally on 04-Sep-15.
 */
public class FriendsAdapter extends ArrayAdapter<FriendItem> {

    private Context context;
    private ArrayList<FriendItem> friendsList;

    static class ViewHolder {
        public ImageView ivUser;
        public TextView tvUserName;
    }

    /**
     * @param context
     * @param friendsList
     */
    public FriendsAdapter(Context context,
                            ArrayList<FriendItem> friendsList) {
        super(context, R.layout.item_friend, friendsList);
        this.context = context;
        this.friendsList = friendsList;
    }

    @Override
    public FriendItem getItem(int position) {
        // TODO Auto-generated method stub
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_friend, null);
            viewHolder = new ViewHolder();
            viewHolder.ivUser = (ImageView) convertView
                    .findViewById(R.id.iv_User);
            viewHolder.tvUserName = (TextView) convertView
                    .findViewById(R.id.tv_UserName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // String imageURL = notificationsList.get(position).getImage();
        String text = "";

        text += friendsList.get(position).getUserName();
        viewHolder.tvUserName.setText(text);
        String imageURL = "";

            imageURL = friendsList.get(position).getPictureURL();

        if (imageURL != null) {
            if (imageURL.trim().length() > 0) {
                Picasso.with(context).load(imageURL).into(viewHolder.ivUser);
            }
        }

        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        viewHolder.ivUser.setAnimation(anim);



        return convertView;

    }
}
