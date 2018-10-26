package com.example.tonghai.testapp.service.response;

import com.example.tonghai.testapp.model.Meta;
import com.example.tonghai.testapp.model.NewsFeed;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetNewsFeedResponse {
    @SerializedName("result")
    private Result result;
    @SerializedName("meta")
    private Meta meta;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static class Result {
        @SerializedName("totalPage")
        private int totalPage;
        @SerializedName("data")
        private ArrayList<NewsFeed> data;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public ArrayList<NewsFeed> getData() {
            return data;
        }

        public void setData(ArrayList<NewsFeed> data) {
            this.data = data;
        }
    }
}
