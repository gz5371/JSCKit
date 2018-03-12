package jsc.exam.jsckit;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import jsc.exam.jsckit.widget.CustomItemLayoutView;
import jsc.kit.refreshlayout.RefreshLayout;

public class RefreshLayoutActivity extends AppCompatActivity {

    RefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);

        refreshLayout = findViewById(R.id.refresh_layout);
//        refreshLayout.setPullRatioY(0.55f);
//        refreshLayout.setPullToRefreshRatio(0.45f);
//        refreshLayout.setReleaseToRefreshRatio(0.65f);
        refreshLayout.setOnScrollListener(new RefreshLayout.OnScrollListener() {
            @Override
            public void onScroll(View headerView, int headerHeight, float pullToRefreshRatio, float releaseToRefreshRatio, int scrollY, boolean isRefreshing) {
                //从底部向上滑动，不做任何处理
                if (scrollY > 0 || isRefreshing)
                    return;
                TextView tvScrollTips = headerView.findViewById(R.id.tv_scroll_tips);
                if (Math.abs(scrollY) >= headerHeight * releaseToRefreshRatio)
                    tvScrollTips.setText("Release To Refresh");
                else if (Math.abs(scrollY) >= headerHeight * pullToRefreshRatio)
                    tvScrollTips.setText("Pull To Refresh");
                else
                    tvScrollTips.setText("Surprise");
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {

            @Override
            public void onStartRefresh(View headerView) {
                TextView tvScrollTips = headerView.findViewById(R.id.tv_scroll_tips);
                tvScrollTips.setText("Refreshing...");
                headerView.findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
            }

            @Override
            public void onRefresh() {
                // TODO:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                }, 4000);
            }

            @Override
            public void onEndRefresh(View headerView) {
                TextView tvScrollTips = headerView.findViewById(R.id.tv_scroll_tips);
                tvScrollTips.setText("Refresh Successfully.");
                headerView.findViewById(R.id.loading_view).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void widgetClick(View view){
        if (view instanceof CustomItemLayoutView)
            Toast.makeText(this, ((CustomItemLayoutView) view).getLabel(), Toast.LENGTH_SHORT).show();
    }
}
