package com.example.tonghai.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardInfo implements Parcelable {
    @SerializedName("created_at")
    private int created_at;
    @SerializedName("views")
    private int views;
    @SerializedName("privacy")
    private int privacy;
    @SerializedName("liked")
    private int liked;
    @SerializedName("location")
    private String location;
    @SerializedName("feeling")
    private String feeling;
    @SerializedName("tags")
    private ArrayList<String> tags;

    public CardInfo() {
    }

    protected CardInfo(Parcel in) {
        created_at = in.readInt();
        views = in.readInt();
        privacy = in.readInt();
        liked = in.readInt();
        location = in.readString();
        feeling = in.readString();
        tags = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(created_at);
        dest.writeInt(views);
        dest.writeInt(privacy);
        dest.writeInt(liked);
        dest.writeString(location);
        dest.writeString(feeling);
        dest.writeStringList(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardInfo> CREATOR = new Creator<CardInfo>() {
        @Override
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        @Override
        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
