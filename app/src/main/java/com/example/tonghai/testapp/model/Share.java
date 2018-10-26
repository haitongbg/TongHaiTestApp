package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Share implements Parcelable {
    @SerializedName("username")
    private String username;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("created_at")
    private int created_at;

    public Share() {
    }

    protected Share(Parcel in) {
        username = in.readString();
        avatar = in.readString();
        user_id = in.readInt();
        created_at = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(avatar);
        dest.writeInt(user_id);
        dest.writeInt(created_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Share> CREATOR = new Creator<Share>() {
        @Override
        public Share createFromParcel(Parcel in) {
            return new Share(in);
        }

        @Override
        public Share[] newArray(int size) {
            return new Share[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
