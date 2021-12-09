package com.example.hello.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hello.R;
import com.example.hello.data.model.Image;
import com.example.hello.data.model.Tweet;
import com.example.hello.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MomentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int sITEM_TYPE_NORMAL = 0;
    private static final int sITEM_TYPE_HEADER = 1;
    private static final int sITEM_TYPE_FOOTER = 2;

    private final static int HEAD_COUNT = 1;
    private final static int FOOT_COUNT = 0;

    List<Tweet> mTweetList;
    User user;
    Context mContext;

    public MomentsAdapter(Context mContext) {
        this.mTweetList = new ArrayList<>();
        this.user = new User();
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTweets(List<Tweet> tweets) {
        this.mTweetList.clear();
        this.mTweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUser(User user) {
        this.user = user;
        notifyDataSetChanged();
    }

    public boolean isHead(int position) {
        return HEAD_COUNT != 0 && position == 0;
    }

    public boolean isFoot(int position) {
        return FOOT_COUNT != 0 && position == mTweetList.size() + HEAD_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHead(position)) { // 头部
            return sITEM_TYPE_HEADER;
        } else if (isFoot(position)) {
            return sITEM_TYPE_FOOTER;
        } else {
            return sITEM_TYPE_NORMAL;
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View tweetView = inflater.inflate(R.layout.blog_custom_row, parent, false);
        View userView = inflater.inflate(R.layout.blog_header_layout, parent, false);

        if (viewType == sITEM_TYPE_HEADER) {
            return new UserViewHolder(userView);
        } else {
            return new MomentViewHolder(tweetView);
        }
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);

        if (type == sITEM_TYPE_HEADER) {
            ((UserViewHolder) holder).userName.setText(user.getNick());
            Glide.with(mContext).load(user.getAvatar()).into(((UserViewHolder) holder).userAvatar);
            Glide.with(mContext).load(user.getProfileImage()).into(((UserViewHolder) holder).userProfile);
            return;
        } else if (type == sITEM_TYPE_FOOTER) {
            return;
        }

        // Get the data model based on position
        Tweet tweet = mTweetList.get(position);

        // Set item views based on your views and data model
        ((MomentViewHolder) holder).senderName.setText(tweet.getSender() == null ? "" : tweet.getSender().getNick());
        ((MomentViewHolder) holder).contentText.setText(tweet.getContent() == null ? "" : tweet.getContent());
        Glide.with(mContext).load(tweet.getSender().getAvatar()).into(((MomentViewHolder) holder).senderAvatar);

        ImageAdapter imageAdapter = new ImageAdapter(mContext);
        ((MomentViewHolder) holder).imagesGrid.setAdapter(imageAdapter);
        ((MomentViewHolder) holder).imagesGrid.setLayoutManager(new GridLayoutManager(mContext, 3));
        ((MomentViewHolder) holder).imagesGrid.setVisibility(View.VISIBLE);
        imageAdapter.setImageUrls(tweet.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));

        CommentAdapter commentAdapter = new CommentAdapter(mContext);
        ((MomentViewHolder) holder).commentsList.setAdapter(commentAdapter);
        ((MomentViewHolder) holder).commentsList.setLayoutManager(new LinearLayoutManager(mContext));
        ((MomentViewHolder) holder).commentsList.setVisibility(View.VISIBLE);
        commentAdapter.setComment(tweet.getComments());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTweetList.size();
    }

    public static class MomentViewHolder extends RecyclerView.ViewHolder {
        public TextView senderName;
        public TextView contentText;
        public ImageView senderAvatar;
        public RecyclerView imagesGrid;
        public RecyclerView commentsList;

        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.blog_tweet_sender);
            contentText = itemView.findViewById(R.id.blog_tweet_content);
            senderAvatar = itemView.findViewById(R.id.blog_tweet_avatar);
            imagesGrid = itemView.findViewById(R.id.blog_tweet_images_recycler_view);
            commentsList = itemView.findViewById(R.id.blog_tweet_comments_recycler_view);
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView userAvatar;
        public ImageView userProfile;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.blog_user_name);
            userAvatar = itemView.findViewById(R.id.blog_user_avatar);
            userProfile = itemView.findViewById(R.id.profile_image);
        }
    }
}
