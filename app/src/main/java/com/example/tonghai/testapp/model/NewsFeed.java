package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsFeed implements Parcelable{
    @SerializedName("shares")
    private ArrayList<Share> shares;
    @SerializedName("comment")
    private CommentData commentData;
    @SerializedName("like")
    private LikeData likeData;
    @SerializedName("card_info")
    private CardInfo card_info;
    @SerializedName("user")
    private User user;
    @SerializedName("video")
    private Video video;
    @SerializedName("images")
    private ArrayList<Image> images;
    @SerializedName("card_type")
    private int card_type;
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private int id;

    public NewsFeed() {
    }

    protected NewsFeed(Parcel in) {
        shares = in.createTypedArrayList(Share.CREATOR);
        commentData = in.readParcelable(CommentData.class.getClassLoader());
        likeData = in.readParcelable(LikeData.class.getClassLoader());
        card_info = in.readParcelable(CardInfo.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
        video = in.readParcelable(Video.class.getClassLoader());
        images = in.createTypedArrayList(Image.CREATOR);
        card_type = in.readInt();
        title = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(shares);
        dest.writeParcelable(commentData, flags);
        dest.writeParcelable(likeData, flags);
        dest.writeParcelable(card_info, flags);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(video, flags);
        dest.writeTypedList(images);
        dest.writeInt(card_type);
        dest.writeString(title);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsFeed> CREATOR = new Creator<NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(Parcel in) {
            return new NewsFeed(in);
        }

        @Override
        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
        }
    };

    public ArrayList<Share> getShares() {
        return shares;
    }

    public void setShares(ArrayList<Share> shares) {
        this.shares = shares;
    }

    public CommentData getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentData commentData) {
        this.commentData = commentData;
    }

    public LikeData getLikeData() {
        return likeData;
    }

    public void setLikeData(LikeData likeData) {
        this.likeData = likeData;
    }

    public CardInfo getCard_info() {
        return card_info;
    }

    public void setCard_info(CardInfo card_info) {
        this.card_info = card_info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class CommentData implements Parcelable{
        @SerializedName("data")
        private ArrayList<Comment> data;
        @SerializedName("total")
        private int total;

        public CommentData() {
        }

        protected CommentData(Parcel in) {
            data = in.createTypedArrayList(Comment.CREATOR);
            total = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(data);
            dest.writeInt(total);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CommentData> CREATOR = new Creator<CommentData>() {
            @Override
            public CommentData createFromParcel(Parcel in) {
                return new CommentData(in);
            }

            @Override
            public CommentData[] newArray(int size) {
                return new CommentData[size];
            }
        };

        public ArrayList<Comment> getData() {
            return data;
        }

        public void setData(ArrayList<Comment> data) {
            this.data = data;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class LikeData implements Parcelable {
        @SerializedName("data")
        private ArrayList<Like> data;
        @SerializedName("type")
        private ArrayList<Integer> type;
        @SerializedName("total")
        private int total;

        protected LikeData(Parcel in) {
            data = in.createTypedArrayList(Like.CREATOR);
            total = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(data);
            dest.writeInt(total);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<LikeData> CREATOR = new Creator<LikeData>() {
            @Override
            public LikeData createFromParcel(Parcel in) {
                return new LikeData(in);
            }

            @Override
            public LikeData[] newArray(int size) {
                return new LikeData[size];
            }
        };

        public ArrayList<Like> getData() {
            return data;
        }

        public void setData(ArrayList<Like> data) {
            this.data = data;
        }

        public ArrayList<Integer> getType() {
            return type;
        }

        public void setType(ArrayList<Integer> type) {
            this.type = type;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public LikeData() {

        }
    }
}
