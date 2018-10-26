package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Comment implements Parcelable {
    @SerializedName("items")
    private ArrayList<Comment> items;
    @SerializedName("total_like")
    private int total_like;
    @SerializedName("user")
    private User user;
    @SerializedName("created_at")
    private int created_at;
    @SerializedName("content")
    private String content;
    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private int id;

    public Comment() {
    }

    protected Comment(Parcel in) {
        items = in.createTypedArrayList(Comment.CREATOR);
        total_like = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        created_at = in.readInt();
        content = in.readString();
        type = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
        dest.writeInt(total_like);
        dest.writeParcelable(user, flags);
        dest.writeInt(created_at);
        dest.writeString(content);
        dest.writeString(type);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
