package com.example.tonghai.testapp.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tonghai.testapp.R;

public class TestActivity extends AppCompatActivity {
    LinearLayout layoutTop;
    RelativeLayout layoutBottom;
    private float downY;
    private int lastHeight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initUI();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        layoutBottom = findViewById(R.id.layout_bottom);
        layoutTop = findViewById(R.id.layout_top);
        layoutBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downY = event.getRawY();
                        lastHeight = layoutTop.getLayoutParams().height;
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        int move = (int) (event.getRawY() - downY);
                        height = lastHeight + move;
                        if (height < getResources().getDimensionPixelSize(R.dimen.size_200)) height = getResources().getDimensionPixelSize(R.dimen.size_200);
                        else if (height > getResources().getDimensionPixelSize(R.dimen.size_450)) height = getResources().getDimensionPixelSize(R.dimen.size_450);
                        layoutTop.getLayoutParams().height = height;
                        layoutTop.requestLayout();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (height <= getResources().getDimensionPixelSize(R.dimen.size_300)) height = getResources().getDimensionPixelSize(R.dimen.size_200);
                        else height = getResources().getDimensionPixelSize(R.dimen.size_450);
                        layoutTop.getLayoutParams().height = height;
                        layoutTop.requestLayout();
                        break;
                    }
                    default:
                        return false;
                }
                return true;
            }
        });
    }
}
