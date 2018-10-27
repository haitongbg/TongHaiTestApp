package com.example.tonghai.testapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tonghai.testapp.R;
import com.example.tonghai.testapp.adapter.NewsFeedAdapter;
import com.example.tonghai.testapp.model.NewsFeed;
import com.example.tonghai.testapp.service.MainService;
import com.example.tonghai.testapp.service.response.GetNewsFeedResponse;
import com.example.tonghai.testapp.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedActivity extends AppCompatActivity {
    @BindView(R.id.rv_newsfeed)
    RecyclerView rvNewsfeed;
    private MainService mainService;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<NewsFeed> mNewsFeeds = new ArrayList<>();
    private NewsFeedAdapter mNewsFeedAdapter;
    private NewsFeed itemLoadmore = new NewsFeed(-1);
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainService = MyApplication.getInstance().getMainService();
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);
        initUI();
        getNewsFeed();
    }

    private void initUI() {
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvNewsfeed.setLayoutManager(mLayoutManager);
        rvNewsfeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mNewsFeedAdapter != null
                        && mLayoutManager.findLastVisibleItemPosition() != -1
                        && mLayoutManager.findLastVisibleItemPosition() == (mNewsFeedAdapter.getItemCount() - 1)
                        && mNewsFeeds.get(mNewsFeedAdapter.getItemCount() - 1).getCard_type() == Constant.TYPE_LOADMORE
                        && !isLoading) {
                    getNewsFeed();
                }
            }
        });
    }

    private void getNewsFeed() {
        isLoading = true;
        Call<GetNewsFeedResponse> getNewsFeed = mainService.getNewsFeed();
        getNewsFeed.enqueue(new Callback<GetNewsFeedResponse>() {
            @Override
            public void onResponse(Call<GetNewsFeedResponse> call, Response<GetNewsFeedResponse> response) {
                GetNewsFeedResponse getNewsFeedResponse = response.body();
                if (getNewsFeedResponse != null && getNewsFeedResponse.getResult() != null) {
                    ArrayList<NewsFeed> newsFeeds = getNewsFeedResponse.getResult().getData();
                    if (newsFeeds != null && !newsFeeds.isEmpty()) setData(newsFeeds);
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<GetNewsFeedResponse> call, Throwable t) {
                isLoading = false;
            }
        });
    }

    private void setData(ArrayList<NewsFeed> newsFeeds) {
        while (mNewsFeeds != null && !mNewsFeeds.isEmpty() && mNewsFeeds.get(mNewsFeeds.size() - 1).getCard_type() == Constant.TYPE_LOADMORE) {
            int removePosition = mNewsFeeds.size() - 1;
            mNewsFeeds.remove(removePosition);
            if (mNewsFeedAdapter != null) mNewsFeedAdapter.notifyItemRemoved(removePosition);
        }
        int insertPosition = mNewsFeeds.size();
        mNewsFeeds.addAll(newsFeeds);
        mNewsFeeds.add(itemLoadmore);
        if (mNewsFeedAdapter != null) mNewsFeedAdapter.notifyItemRangeInserted(insertPosition, newsFeeds.size()+1);
        else {
            mNewsFeedAdapter = new NewsFeedAdapter(NewsFeedActivity.this, mNewsFeeds);
            rvNewsfeed.setAdapter(mNewsFeedAdapter);
        }
    }
}
