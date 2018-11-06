package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tonghai.testapp.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFeed implements Parcelable {
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
    @SerializedName("card_type")
    private int card_type;
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private int id;
    @SerializedName("data")
    private JsonElement data;
    private ArrayList<Video> videos;
    private ArrayList<Image> images;
    private Gson gson = new Gson();

    public NewsFeed() {
    }

    public NewsFeed(int card_type) {
        this.card_type = card_type;
    }

    protected NewsFeed(Parcel in) {
        shares = in.createTypedArrayList(Share.CREATOR);
        commentData = in.readParcelable(CommentData.class.getClassLoader());
        likeData = in.readParcelable(LikeData.class.getClassLoader());
        card_info = in.readParcelable(CardInfo.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
        card_type = in.readInt();
        title = in.readString();
        id = in.readInt();
        videos = in.createTypedArrayList(Video.CREATOR);
        images = in.createTypedArrayList(Image.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(shares);
        dest.writeParcelable(commentData, flags);
        dest.writeParcelable(likeData, flags);
        dest.writeParcelable(card_info, flags);
        dest.writeParcelable(user, flags);
        dest.writeInt(card_type);
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeTypedList(videos);
        dest.writeTypedList(images);
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

    public ArrayList<Video> getVideos() {
        if (videos == null && this.card_type == Constant.TYPE_VIDEO && this.data != null) {
            videos = new ArrayList<>();
            if (data.isJsonArray()) {
                JsonArray jsonArray = data.getAsJsonArray();
                if (jsonArray.size() > 0) {
                    for (int i = 0, z=jsonArray.size(); i<z; i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        Video video = gson.fromJson(jsonObject.toString(), Video.class);
                        if (video != null) this.videos.add(video);
                    }
                }
            }
        }
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<Image> getImages() {
        if (images == null && this.card_type == Constant.TYPE_IMAGE && this.data != null) {
            images = new ArrayList<>();
            if (data.isJsonArray()) {
                JsonArray jsonArray = data.getAsJsonArray();
                if (jsonArray.size() > 0) {
                    for (int i = 0, z=jsonArray.size(); i<z; i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        Image image = gson.fromJson(jsonObject.toString(), Image.class);
                        if (image != null) this.images.add(image);
                    }
                }
            }
        }
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public static class CommentData implements Parcelable {
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
