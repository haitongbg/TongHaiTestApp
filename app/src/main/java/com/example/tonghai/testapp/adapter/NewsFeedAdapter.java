package com.example.tonghai.testapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Barrier;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.tonghai.testapp.R;
import com.example.tonghai.testapp.model.CardInfo;
import com.example.tonghai.testapp.model.Image;
import com.example.tonghai.testapp.model.NewsFeed;
import com.example.tonghai.testapp.model.User;
import com.example.tonghai.testapp.model.Video;
import com.example.tonghai.testapp.utils.Constant;
import com.example.tonghai.testapp.utils.Utils;
import com.example.tonghai.testapp.view.ProgressWheel;
import com.example.tonghai.testapp.view.RippleViewLinear;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOADMORE = -1;
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_VIDEO = 1;
    private static final int TYPE_TEXT = 2;
    private static final int TYPE_IMAGE_SINGLE = 3;
    private static final int TYPE_IMAGE_2N3 = 31;
    private static final int TYPE_IMAGE_4N = 32;
    private Context mContext;
    private ArrayList<NewsFeed> mNewsFeeds;
    private RequestManager mRequestManager;
    private LayoutInflater mLayoutInflater;
    private int screenWidth;

    public NewsFeedAdapter(Context context, ArrayList<NewsFeed> newsFeeds) {
        this.mContext = context;
        this.mNewsFeeds = newsFeeds;
        this.mRequestManager = Glide.with(mContext.getApplicationContext());
        this.mLayoutInflater = LayoutInflater.from(mContext);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    @Override
    public int getItemViewType(int position) {
        NewsFeed newsFeed = mNewsFeeds.get(position);
        switch (newsFeed.getCard_type()) {
            case Constant.TYPE_VIDEO:
                return TYPE_VIDEO;
            case Constant.TYPE_TEXT:
                return TYPE_TEXT;
            case Constant.TYPE_IMAGE: {
                if (newsFeed.getImages() != null && newsFeed.getImages().size() > 1) {
                    if (newsFeed.getImages().size() < 4) return TYPE_IMAGE_2N3;
                    else return TYPE_IMAGE_4N;
                } else return TYPE_IMAGE_SINGLE;
            }
            case Constant.TYPE_LOADMORE:
                return TYPE_LOADMORE;
            default:
                return TYPE_EMPTY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_LOADMORE: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_loadmore, viewGroup, false);
                return new LoadmoreViewHolder(itemView);
            }
            case TYPE_VIDEO: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_type_1, viewGroup, false);
                return new VideoViewHolder(itemView);
            }
            case TYPE_TEXT: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_type_2, viewGroup, false);
                return new TextViewHolder(itemView);
            }
            case TYPE_IMAGE_SINGLE: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_type_3, viewGroup, false);
                return new ImageSingleViewHolder(itemView);
            }
            case TYPE_IMAGE_2N3: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_type_3_2n3, viewGroup, false);
                return new Image2n3ViewHolder(itemView);
            }
            case TYPE_IMAGE_4N: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_type_3_4n, viewGroup, false);
                return new Image4nViewHolder(itemView);
            }
            default: {
                View itemView = mLayoutInflater.inflate(R.layout.item_feed_empty, viewGroup, false);
                return new EmptyViewHolder(itemView);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoViewHolder) ((VideoViewHolder) holder).setData(position);
        else if (holder instanceof TextViewHolder) ((TextViewHolder) holder).setData(position);
        else if (holder instanceof ImageSingleViewHolder)
            ((ImageSingleViewHolder) holder).setData(position);
        else if (holder instanceof Image2n3ViewHolder)
            ((Image2n3ViewHolder) holder).setData(position);
        else if (holder instanceof Image4nViewHolder)
            ((Image4nViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return mNewsFeeds.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_option)
        ImageView btnOption;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_privacy)
        ImageView ivPrivacy;
        @BindView(R.id.barrier_header)
        Barrier barrierHeader;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.frame_player)
        FrameLayout framePlayer;
        @BindView(R.id.iv_video_thumb)
        ImageView ivVideoThumb;
        @BindView(R.id.btn_play)
        ImageView btnPlay;
        @BindView(R.id.ic_like)
        CircleImageView icLike;
        @BindView(R.id.ic_heart)
        CircleImageView icHeart;
        @BindView(R.id.ic_haha)
        CircleImageView icHaha;
        @BindView(R.id.ic_wow)
        CircleImageView icWow;
        @BindView(R.id.ic_sad)
        CircleImageView icSad;
        @BindView(R.id.ic_angry)
        CircleImageView icAngry;
        @BindView(R.id.tv_reaction_count)
        TextView tvReactionCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.layout_reaction_count)
        LinearLayout layoutReactionCount;
        @BindView(R.id.v_under_reaction_count)
        View vUnderReactionCount;
        @BindView(R.id.ripple_btn_like)
        RippleViewLinear rippleBtnLike;
        @BindView(R.id.ripple_btn_comment)
        RippleViewLinear rippleBtnComment;
        @BindView(R.id.ripple_btn_share)
        RippleViewLinear rippleBtnShare;
        @BindView(R.id.item_view)
        ConstraintLayout itemView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            NewsFeed newsFeed = mNewsFeeds.get(position);
            if (newsFeed != null) {
                User user = newsFeed.getUser();
                if (user != null) {
                    String name = user.getUsername();
                    if (name != null && !name.isEmpty()) tvName.setText(name);
                    else tvName.setText(mContext.getResources().getString(R.string.guest));
                    String avatar = user.getAvatar();
                    if (avatar != null && !avatar.isEmpty())
                        mRequestManager.load(avatar).into(ivAvatar);
                } else {
                    tvName.setText(mContext.getResources().getString(R.string.guest));
                }
                CardInfo cardInfo = newsFeed.getCard_info();
                if (cardInfo != null) {
                    tvTime.setText(Utils.converTimeMillisToTimeAgo(cardInfo.getCreated_at()));
                    switch (cardInfo.getPrivacy()) {
                        case 1: {
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_public);
                            break;
                        }
                        default:
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_onlyme);
                    }
                }
                String title = newsFeed.getTitle();
                if (title != null && !title.isEmpty()) {
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else tvTitle.setVisibility(View.GONE);
                Video video = newsFeed.getVideo();
                if (video != null) {
                    framePlayer.setVisibility(View.VISIBLE);
                    ivVideoThumb.setVisibility(View.VISIBLE);
                    btnPlay.setVisibility(View.VISIBLE);
                    int height = screenWidth * 16 / 9;
                    if (video.getWidth() > 0 && video.getHeight() > 0) {
                        height = screenWidth * video.getHeight() / video.getWidth();
                    }
                    framePlayer.getLayoutParams().width = screenWidth;
                    framePlayer.getLayoutParams().height = height;
                    ivVideoThumb.getLayoutParams().width = screenWidth;
                    ivVideoThumb.getLayoutParams().height = height;
                    String thumb = video.getThumb_url();
                    if (thumb != null && !thumb.isEmpty())
                        mRequestManager.load(thumb).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ivVideoThumb.setImageDrawable(resource);
                                return false;
                            }
                        }).into(screenWidth, height);
                } else {
                    framePlayer.setVisibility(View.GONE);
                    ivVideoThumb.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.GONE);
                }
                NewsFeed.LikeData likeData = newsFeed.getLikeData();
                if (likeData != null) {
                    icLike.setVisibility(View.GONE);
                    icHeart.setVisibility(View.GONE);
                    icHaha.setVisibility(View.GONE);
                    icWow.setVisibility(View.GONE);
                    icSad.setVisibility(View.GONE);
                    icAngry.setVisibility(View.GONE);
                    ArrayList<Integer> types = likeData.getType();
                    if (types != null && !types.isEmpty()) {
                        for (Integer type : types) {
                            if (type == 1) icLike.setVisibility(View.VISIBLE);
                            if (type == 2) icHeart.setVisibility(View.VISIBLE);
                            if (type == 3) icHaha.setVisibility(View.VISIBLE);
                            if (type == 4) icWow.setVisibility(View.VISIBLE);
                            if (type == 5) icSad.setVisibility(View.VISIBLE);
                            if (type == 6) icAngry.setVisibility(View.VISIBLE);
                        }
                        tvReactionCount.setText(likeData.getTotal() + "" + "");
                    }
                }
                if (newsFeed.getCommentData() != null && newsFeed.getCommentData().getTotal() > 0) {
                    tvCommentCount.setText(newsFeed.getCommentData().getTotal() + " bình luận");
                    tvCommentCount.setVisibility(View.VISIBLE);
                } else tvCommentCount.setVisibility(View.GONE);
                if (newsFeed.getShares() != null && newsFeed.getShares().size() > 0) {
                    tvShareCount.setText(newsFeed.getShares().size() + " chia sẻ");
                    tvShareCount.setVisibility(View.VISIBLE);
                } else tvShareCount.setVisibility(View.GONE);
            }
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_option)
        ImageView btnOption;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_privacy)
        ImageView ivPrivacy;
        @BindView(R.id.barrier_header)
        Barrier barrierHeader;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ic_like)
        CircleImageView icLike;
        @BindView(R.id.ic_heart)
        CircleImageView icHeart;
        @BindView(R.id.ic_haha)
        CircleImageView icHaha;
        @BindView(R.id.ic_wow)
        CircleImageView icWow;
        @BindView(R.id.ic_sad)
        CircleImageView icSad;
        @BindView(R.id.ic_angry)
        CircleImageView icAngry;
        @BindView(R.id.tv_reaction_count)
        TextView tvReactionCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.layout_reaction_count)
        LinearLayout layoutReactionCount;
        @BindView(R.id.v_under_reaction_count)
        View vUnderReactionCount;
        @BindView(R.id.ripple_btn_like)
        RippleViewLinear rippleBtnLike;
        @BindView(R.id.ripple_btn_comment)
        RippleViewLinear rippleBtnComment;
        @BindView(R.id.ripple_btn_share)
        RippleViewLinear rippleBtnShare;
        @BindView(R.id.item_view)
        ConstraintLayout itemView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            NewsFeed newsFeed = mNewsFeeds.get(position);
            if (newsFeed != null) {
                User user = newsFeed.getUser();
                if (user != null) {
                    String name = user.getUsername();
                    if (name != null && !name.isEmpty()) tvName.setText(name);
                    else tvName.setText(mContext.getResources().getString(R.string.guest));
                    String avatar = user.getAvatar();
                    if (avatar != null && !avatar.isEmpty())
                        mRequestManager.load(avatar).into(ivAvatar);
                } else {
                    tvName.setText(mContext.getResources().getString(R.string.guest));
                }
                CardInfo cardInfo = newsFeed.getCard_info();
                if (cardInfo != null) {
                    tvTime.setText(Utils.converTimeMillisToTimeAgo(cardInfo.getCreated_at()));
                    switch (cardInfo.getPrivacy()) {
                        case 1: {
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_public);
                            break;
                        }
                        default:
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_onlyme);
                    }
                }
                String title = newsFeed.getTitle();
                if (title != null && !title.isEmpty()) {
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else tvTitle.setVisibility(View.GONE);
                NewsFeed.LikeData likeData = newsFeed.getLikeData();
                if (likeData != null) {
                    icLike.setVisibility(View.GONE);
                    icHeart.setVisibility(View.GONE);
                    icHaha.setVisibility(View.GONE);
                    icWow.setVisibility(View.GONE);
                    icSad.setVisibility(View.GONE);
                    icAngry.setVisibility(View.GONE);
                    ArrayList<Integer> types = likeData.getType();
                    if (types != null && !types.isEmpty()) {
                        for (Integer type : types) {
                            if (type == 1) icLike.setVisibility(View.VISIBLE);
                            if (type == 2) icHeart.setVisibility(View.VISIBLE);
                            if (type == 3) icHaha.setVisibility(View.VISIBLE);
                            if (type == 4) icWow.setVisibility(View.VISIBLE);
                            if (type == 5) icSad.setVisibility(View.VISIBLE);
                            if (type == 6) icAngry.setVisibility(View.VISIBLE);
                        }
                        tvReactionCount.setText(likeData.getTotal() + "");
                    }
                }
                if (newsFeed.getCommentData() != null && newsFeed.getCommentData().getTotal() > 0) {
                    tvCommentCount.setText(newsFeed.getCommentData().getTotal() + " bình luận");
                    tvCommentCount.setVisibility(View.VISIBLE);
                } else tvCommentCount.setVisibility(View.GONE);
                if (newsFeed.getShares() != null && newsFeed.getShares().size() > 0) {
                    tvShareCount.setText(newsFeed.getShares().size() + " chia sẻ");
                    tvShareCount.setVisibility(View.VISIBLE);
                } else tvShareCount.setVisibility(View.GONE);
            }
        }
    }

    class ImageSingleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_option)
        ImageView btnOption;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_privacy)
        ImageView ivPrivacy;
        @BindView(R.id.barrier_header)
        Barrier barrierHeader;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.ic_like)
        CircleImageView icLike;
        @BindView(R.id.ic_heart)
        CircleImageView icHeart;
        @BindView(R.id.ic_haha)
        CircleImageView icHaha;
        @BindView(R.id.ic_wow)
        CircleImageView icWow;
        @BindView(R.id.ic_sad)
        CircleImageView icSad;
        @BindView(R.id.ic_angry)
        CircleImageView icAngry;
        @BindView(R.id.tv_reaction_count)
        TextView tvReactionCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.layout_reaction_count)
        LinearLayout layoutReactionCount;
        @BindView(R.id.v_under_reaction_count)
        View vUnderReactionCount;
        @BindView(R.id.ripple_btn_like)
        RippleViewLinear rippleBtnLike;
        @BindView(R.id.ripple_btn_comment)
        RippleViewLinear rippleBtnComment;
        @BindView(R.id.ripple_btn_share)
        RippleViewLinear rippleBtnShare;
        @BindView(R.id.item_view)
        ConstraintLayout itemView;

        public ImageSingleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            NewsFeed newsFeed = mNewsFeeds.get(position);
            if (newsFeed != null) {
                User user = newsFeed.getUser();
                if (user != null) {
                    String name = user.getUsername();
                    if (name != null && !name.isEmpty()) tvName.setText(name);
                    else tvName.setText(mContext.getResources().getString(R.string.guest));
                    String avatar = user.getAvatar();
                    if (avatar != null && !avatar.isEmpty())
                        mRequestManager.load(avatar).into(ivAvatar);
                } else {
                    tvName.setText(mContext.getResources().getString(R.string.guest));
                }
                CardInfo cardInfo = newsFeed.getCard_info();
                if (cardInfo != null) {
                    tvTime.setText(Utils.converTimeMillisToTimeAgo(cardInfo.getCreated_at()));
                    switch (cardInfo.getPrivacy()) {
                        case 1: {
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_public);
                            break;
                        }
                        default:
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_onlyme);
                    }
                }
                String title = newsFeed.getTitle();
                if (title != null && !title.isEmpty()) {
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else tvTitle.setVisibility(View.GONE);
                if (newsFeed.getImages() != null && !newsFeed.getImages().isEmpty()) {
                    Image image = newsFeed.getImages().get(0);
                    if (image.getLink() != null && !image.getLink().isEmpty()) {
                        ivImage.setVisibility(View.VISIBLE);
                        mRequestManager.load(image).into(ivImage);
                    } else ivImage.setVisibility(View.GONE);
                } else ivImage.setVisibility(View.GONE);
                NewsFeed.LikeData likeData = newsFeed.getLikeData();
                if (likeData != null) {
                    icLike.setVisibility(View.GONE);
                    icHeart.setVisibility(View.GONE);
                    icHaha.setVisibility(View.GONE);
                    icWow.setVisibility(View.GONE);
                    icSad.setVisibility(View.GONE);
                    icAngry.setVisibility(View.GONE);
                    ArrayList<Integer> types = likeData.getType();
                    if (types != null && !types.isEmpty()) {
                        for (Integer type : types) {
                            if (type == 1) icLike.setVisibility(View.VISIBLE);
                            if (type == 2) icHeart.setVisibility(View.VISIBLE);
                            if (type == 3) icHaha.setVisibility(View.VISIBLE);
                            if (type == 4) icWow.setVisibility(View.VISIBLE);
                            if (type == 5) icSad.setVisibility(View.VISIBLE);
                            if (type == 6) icAngry.setVisibility(View.VISIBLE);
                        }
                        tvReactionCount.setText(likeData.getTotal() + "");
                    }
                }
                if (newsFeed.getCommentData() != null && newsFeed.getCommentData().getTotal() > 0) {
                    tvCommentCount.setText(newsFeed.getCommentData().getTotal() + " bình luận");
                    tvCommentCount.setVisibility(View.VISIBLE);
                } else tvCommentCount.setVisibility(View.GONE);
                if (newsFeed.getShares() != null && newsFeed.getShares().size() > 0) {
                    tvShareCount.setText(newsFeed.getShares().size() + " chia sẻ");
                    tvShareCount.setVisibility(View.VISIBLE);
                } else tvShareCount.setVisibility(View.GONE);
            }
        }
    }

    class Image2n3ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_option)
        ImageView btnOption;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_privacy)
        ImageView ivPrivacy;
        @BindView(R.id.barrier_header)
        Barrier barrierHeader;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_2n3_1)
        ImageView iv2n31;
        @BindView(R.id.iv_2n3_2)
        ImageView iv2n32;
        @BindView(R.id.iv_2n3_3)
        ImageView iv2n33;
        @BindView(R.id.barrier_body)
        Barrier barrierBody;
        @BindView(R.id.ic_like)
        CircleImageView icLike;
        @BindView(R.id.ic_heart)
        CircleImageView icHeart;
        @BindView(R.id.ic_haha)
        CircleImageView icHaha;
        @BindView(R.id.ic_wow)
        CircleImageView icWow;
        @BindView(R.id.ic_sad)
        CircleImageView icSad;
        @BindView(R.id.ic_angry)
        CircleImageView icAngry;
        @BindView(R.id.tv_reaction_count)
        TextView tvReactionCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.layout_reaction_count)
        LinearLayout layoutReactionCount;
        @BindView(R.id.v_under_reaction_count)
        View vUnderReactionCount;
        @BindView(R.id.ripple_btn_like)
        RippleViewLinear rippleBtnLike;
        @BindView(R.id.ripple_btn_comment)
        RippleViewLinear rippleBtnComment;
        @BindView(R.id.ripple_btn_share)
        RippleViewLinear rippleBtnShare;
        @BindView(R.id.item_view)
        ConstraintLayout itemView;

        public Image2n3ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            NewsFeed newsFeed = mNewsFeeds.get(position);
            if (newsFeed != null) {
                User user = newsFeed.getUser();
                if (user != null) {
                    String name = user.getUsername();
                    if (name != null && !name.isEmpty()) tvName.setText(name);
                    else tvName.setText(mContext.getResources().getString(R.string.guest));
                    String avatar = user.getAvatar();
                    if (avatar != null && !avatar.isEmpty())
                        mRequestManager.load(avatar).into(ivAvatar);
                } else {
                    tvName.setText(mContext.getResources().getString(R.string.guest));
                }
                CardInfo cardInfo = newsFeed.getCard_info();
                if (cardInfo != null) {
                    tvTime.setText(Utils.converTimeMillisToTimeAgo(cardInfo.getCreated_at()));
                    switch (cardInfo.getPrivacy()) {
                        case 1: {
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_public);
                            break;
                        }
                        default:
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_onlyme);
                    }
                }
                String title = newsFeed.getTitle();
                if (title != null && !title.isEmpty()) {
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else tvTitle.setVisibility(View.GONE);
                iv2n31.getLayoutParams().height = screenWidth;
                ArrayList<Image> images = newsFeed.getImages();
                if (images.size() == 2) {
                    iv2n33.setVisibility(View.GONE);
                    iv2n32.getLayoutParams().height = screenWidth;
                    mRequestManager.load(images.get(0).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            iv2n31.setImageDrawable(resource);
                            return false;
                        }
                    }).into(screenWidth, screenWidth);
                    mRequestManager.load(images.get(1).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            iv2n32.setImageDrawable(resource);
                            return false;
                        }
                    }).into(screenWidth, screenWidth);
                } else {
                    iv2n32.getLayoutParams().height = (screenWidth - mContext.getResources().getDimensionPixelSize(R.dimen.size_2)) / 2;
                    iv2n33.setVisibility(View.VISIBLE);
                    mRequestManager.load(images.get(0).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            iv2n31.setImageDrawable(resource);
                            return false;
                        }
                    }).into(screenWidth, screenWidth);
                    mRequestManager.load(images.get(1).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            iv2n32.setImageDrawable(resource);
                            return false;
                        }
                    }).into(screenWidth / 2, screenWidth / 2);
                    mRequestManager.load(images.get(2).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            iv2n33.setImageDrawable(resource);
                            return false;
                        }
                    }).into(screenWidth / 2, screenWidth / 2);
                }
                NewsFeed.LikeData likeData = newsFeed.getLikeData();
                if (likeData != null) {
                    icLike.setVisibility(View.GONE);
                    icHeart.setVisibility(View.GONE);
                    icHaha.setVisibility(View.GONE);
                    icWow.setVisibility(View.GONE);
                    icSad.setVisibility(View.GONE);
                    icAngry.setVisibility(View.GONE);
                    ArrayList<Integer> types = likeData.getType();
                    if (types != null && !types.isEmpty()) {
                        for (Integer type : types) {
                            if (type == 1) icLike.setVisibility(View.VISIBLE);
                            if (type == 2) icHeart.setVisibility(View.VISIBLE);
                            if (type == 3) icHaha.setVisibility(View.VISIBLE);
                            if (type == 4) icWow.setVisibility(View.VISIBLE);
                            if (type == 5) icSad.setVisibility(View.VISIBLE);
                            if (type == 6) icAngry.setVisibility(View.VISIBLE);
                        }
                        tvReactionCount.setText(likeData.getTotal() + "");
                    }
                }
                if (newsFeed.getCommentData() != null && newsFeed.getCommentData().getTotal() > 0) {
                    tvCommentCount.setText(newsFeed.getCommentData().getTotal() + " bình luận");
                    tvCommentCount.setVisibility(View.VISIBLE);
                } else tvCommentCount.setVisibility(View.GONE);
                if (newsFeed.getShares() != null && newsFeed.getShares().size() > 0) {
                    tvShareCount.setText(newsFeed.getShares().size() + " chia sẻ");
                    tvShareCount.setVisibility(View.VISIBLE);
                } else tvShareCount.setVisibility(View.GONE);
            }
        }
    }

    class Image4nViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_option)
        ImageView btnOption;
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_privacy)
        ImageView ivPrivacy;
        @BindView(R.id.barrier_header)
        Barrier barrierHeader;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_4n_1)
        ImageView iv4n1;
        @BindView(R.id.iv_4n_2)
        ImageView iv4n2;
        @BindView(R.id.iv_4n_3)
        ImageView iv4n3;
        @BindView(R.id.iv_4n_4)
        ImageView iv4n4;
        @BindView(R.id.tv_more_image)
        TextView tvMoreImage;
        @BindView(R.id.barrier_body)
        Barrier barrierBody;
        @BindView(R.id.ic_like)
        CircleImageView icLike;
        @BindView(R.id.ic_heart)
        CircleImageView icHeart;
        @BindView(R.id.ic_haha)
        CircleImageView icHaha;
        @BindView(R.id.ic_wow)
        CircleImageView icWow;
        @BindView(R.id.ic_sad)
        CircleImageView icSad;
        @BindView(R.id.ic_angry)
        CircleImageView icAngry;
        @BindView(R.id.tv_reaction_count)
        TextView tvReactionCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.layout_reaction_count)
        LinearLayout layoutReactionCount;
        @BindView(R.id.v_under_reaction_count)
        View vUnderReactionCount;
        @BindView(R.id.ripple_btn_like)
        RippleViewLinear rippleBtnLike;
        @BindView(R.id.ripple_btn_comment)
        RippleViewLinear rippleBtnComment;
        @BindView(R.id.ripple_btn_share)
        RippleViewLinear rippleBtnShare;
        @BindView(R.id.item_view)
        ConstraintLayout itemView;

        public Image4nViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(int position) {
            NewsFeed newsFeed = mNewsFeeds.get(position);
            if (newsFeed != null) {
                User user = newsFeed.getUser();
                if (user != null) {
                    String name = user.getUsername();
                    if (name != null && !name.isEmpty()) tvName.setText(name);
                    else tvName.setText(mContext.getResources().getString(R.string.guest));
                    String avatar = user.getAvatar();
                    if (avatar != null && !avatar.isEmpty())
                        mRequestManager.load(avatar).into(ivAvatar);
                } else {
                    tvName.setText(mContext.getResources().getString(R.string.guest));
                }
                CardInfo cardInfo = newsFeed.getCard_info();
                if (cardInfo != null) {
                    tvTime.setText(Utils.converTimeMillisToTimeAgo(cardInfo.getCreated_at()));
                    switch (cardInfo.getPrivacy()) {
                        case 1: {
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_public);
                            break;
                        }
                        default:
                            ivPrivacy.setImageResource(R.drawable.ic_privacy_onlyme);
                    }
                }
                String title = newsFeed.getTitle();
                if (title != null && !title.isEmpty()) {
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                } else tvTitle.setVisibility(View.GONE);
                iv4n1.getLayoutParams().height = (screenWidth - mContext.getResources().getDimensionPixelSize(R.dimen.size_2)) * 2 / 3;
                iv4n2.getLayoutParams().height = (screenWidth - mContext.getResources().getDimensionPixelSize(R.dimen.size_2)) / 3;
                iv4n3.getLayoutParams().height = (screenWidth - mContext.getResources().getDimensionPixelSize(R.dimen.size_2)) / 3;
                iv4n4.getLayoutParams().height = (screenWidth - mContext.getResources().getDimensionPixelSize(R.dimen.size_2)) / 3;
                ArrayList<Image> images = newsFeed.getImages();
                mRequestManager.load(images.get(0).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        iv4n1.setImageDrawable(resource);
                        return false;
                    }
                }).into(screenWidth, screenWidth);
                mRequestManager.load(images.get(1).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        iv4n2.setImageDrawable(resource);
                        return false;
                    }
                }).into(screenWidth / 3, screenWidth / 3);
                mRequestManager.load(images.get(2).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        iv4n3.setImageDrawable(resource);
                        return false;
                    }
                }).into(screenWidth / 3, screenWidth / 3);
                mRequestManager.load(images.get(3).getLink()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        iv4n4.setImageDrawable(resource);
                        return false;
                    }
                }).into(screenWidth / 3, screenWidth / 3);
                if (images.size() == 4) {
                    tvMoreImage.setVisibility(View.GONE);
                } else {
                    tvMoreImage.setVisibility(View.VISIBLE);
                    tvMoreImage.setText("+" + (images.size() - 4));
                }
                NewsFeed.LikeData likeData = newsFeed.getLikeData();
                if (likeData != null) {
                    icLike.setVisibility(View.GONE);
                    icHeart.setVisibility(View.GONE);
                    icHaha.setVisibility(View.GONE);
                    icWow.setVisibility(View.GONE);
                    icSad.setVisibility(View.GONE);
                    icAngry.setVisibility(View.GONE);
                    ArrayList<Integer> types = likeData.getType();
                    if (types != null && !types.isEmpty()) {
                        for (Integer type : types) {
                            if (type == 1) icLike.setVisibility(View.VISIBLE);
                            if (type == 2) icHeart.setVisibility(View.VISIBLE);
                            if (type == 3) icHaha.setVisibility(View.VISIBLE);
                            if (type == 4) icWow.setVisibility(View.VISIBLE);
                            if (type == 5) icSad.setVisibility(View.VISIBLE);
                            if (type == 6) icAngry.setVisibility(View.VISIBLE);
                        }
                        tvReactionCount.setText(likeData.getTotal() + "");
                    }
                }
                if (newsFeed.getCommentData() != null && newsFeed.getCommentData().getTotal() > 0) {
                    tvCommentCount.setText(newsFeed.getCommentData().getTotal() + " bình luận");
                    tvCommentCount.setVisibility(View.VISIBLE);
                } else tvCommentCount.setVisibility(View.GONE);
                if (newsFeed.getShares() != null && newsFeed.getShares().size() > 0) {
                    tvShareCount.setText(newsFeed.getShares().size() + " chia sẻ");
                    tvShareCount.setVisibility(View.VISIBLE);
                } else tvShareCount.setVisibility(View.GONE);
            }
        }
    }

    class LoadmoreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_loadmore)
        ProgressWheel progressLoadmore;
        @BindView(R.id.tv_loadmore)
        TextView tvLoadmore;

        public LoadmoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressLoadmore.setProgress(0.5f);
            progressLoadmore.setCallback(new ProgressWheel.ProgressCallback() {
                @Override
                public void onProgressUpdate(float progress) {
                    if (progress == 0) {
                        progressLoadmore.setProgress(1.0f);
                    } else if (progress == 1.0f) {
                        progressLoadmore.setProgress(0.0f);
                    }
                }
            });
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
