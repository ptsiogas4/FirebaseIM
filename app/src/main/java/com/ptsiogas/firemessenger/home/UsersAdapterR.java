package com.ptsiogas.firemessenger.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ptsiogas.firemessenger.R;
import com.ptsiogas.firemessenger.beans.User;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UsersAdapterR extends RecyclerView.Adapter<UsersAdapterR.UserViewHolder> {

    interface UsersAdapterRListener {
        void onClick(User user);
    }


    private final Context context;
    private ArrayList<User> mData = new ArrayList<>();
    private UsersAdapterRListener mListener;

    public UsersAdapterR(Context context, ArrayList<User> mData, UsersAdapterRListener mListener) {
        this.context = context;
        this.mData = mData;
        this.mListener = mListener;
    }

    public void update(ArrayList<User> data) {
        mData = new ArrayList<>();
        mData.addAll(data);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mData.get(position);
        holder.position = position;
        holder.setUser(user);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position = 0;
        @BindView(R.id.item_user_image_view)
        ImageView itemUserImageView;
        @BindView(R.id.item_friend_name_text_view)
        TextView itemFriendNameTextView;
        @BindView(R.id.item_friend_email_text_view)
        TextView itemFriendEmailTextView;
        @BindView(R.id.item_user_parent)
        CardView itemUserParent;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemUserParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User user = mData.get(position);
            if (mListener != null) {
                mListener.onClick(user);
            }
        }

        void setUser(User user) {
            itemFriendNameTextView.setText(user.getDisplayName());
            itemFriendEmailTextView.setText(user.getEmail());
            Glide.with(context)
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.placeholder_user)
                    .centerCrop()
                    .dontAnimate()
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(itemUserImageView);
        }
    }

}
