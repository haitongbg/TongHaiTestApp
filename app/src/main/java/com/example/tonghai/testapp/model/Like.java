package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Like implements Parcelable {
    @SerializedName("type")
    private int type;
    @SerializedName("username")
    private String username;
    @SerializedName("id")
    private int id;

    public Like() {
    }

    protected Like(Parcel in) {
        type = in.readInt();
        username = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(username);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Like> CREATOR = new Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel in) {
            return new Like(in);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
