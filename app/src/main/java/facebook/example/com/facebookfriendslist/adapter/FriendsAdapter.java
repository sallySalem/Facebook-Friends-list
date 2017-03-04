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
import java.util.List;

import facebook.example.com.facebookfriendslist.R;
import facebook.example.com.facebookfriendslist.data.model.FriendItemData;

/**
 * Created by Sally on 04-Sep-15.
 */
public class FriendsAdapter extends ArrayAdapter<FriendItemData> {

    private Context context;
    private ArrayList<FriendItemData> friendsList;

    static class ViewHolder {
        public ImageView ivUser;
        public TextView tvUserName;
    }

    /**
     * @param context
     * @param friendsList
     */
    public FriendsAdapter(Context context, ArrayList<FriendItemData> friendsList) {
        super(context, R.layout.item_friend, friendsList);
        this.context = context;
        this.friendsList = friendsList;
    }

    @Override
    public FriendItemData getItem(int position) {
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
            viewHolder.ivUser = (ImageView) convertView.findViewById(R.id.iv_User);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_UserName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String friendName = friendsList.get(position).getName();
        viewHolder.tvUserName.setText(friendName);
        String imageUrl = friendsList.get(position).getPicture();
        if (imageUrl != null) {
            if (imageUrl.trim().length() > 0) {
                Picasso.with(context).load(imageUrl).into(viewHolder.ivUser);
            }
        }

        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        viewHolder.ivUser.setAnimation(anim);
        return convertView;
    }
}
