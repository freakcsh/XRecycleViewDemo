package com.freak.android.xrecycleviewdemo.linearLayout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freak.android.xrecycleviewdemo.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class LinearLayoutActivity extends AppCompatActivity {
    private XRecyclerView mXRecyclerView;
    private LinearLayoutAdapter mAdapter;
    private ArrayList<String> mList;
    private int refreshTime = 0;
    private int times = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mXRecyclerView != null) {
            mXRecyclerView.destroy();
            mXRecyclerView = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mXRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(linearLayoutManager);
        //设置刷新样式
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置加载更多样式
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //设置下拉刷新箭头样式图片
        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mXRecyclerView.
                getDefaultRefreshHeaderView().//获取默认的刷新头部显示
                setRefreshTimeVisible(true);//设置显示刷新时间

        View head = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
        //添加头部
        mXRecyclerView.addHeaderView(head);
        //设置加载中的文字提示
        mXRecyclerView.getDefaultFootView().setLoadingHint("我的自定义加载中");
        //自定义加载完成提示
        mXRecyclerView.getDefaultFootView().setNoMoreHint("我的自定义加载完成提示");

        // if you use setFooterView,the default footerView will unUseful
//        TextView tv = new TextView(this);
//        tv.setText("自定义 footer");
//        mRecyclerView.setFootView(tv, new CustomFooterViewCallBack() {
//            @Override
//            public void onLoadingMore(View yourFooterView) {
//
//            }
//
//            @Override
//            public void onLoadMoreComplete(View yourFooterView) {
//
//            }
//
//            @Override
//            public void onSetNoMore(View yourFooterView, boolean noMore) {
//
//            }
//        });
        final int itemLimit = 5;
        //设置数字以控制调用加载更多，请参阅关于linearActivity的演示
        mXRecyclerView.setLimitNumberToCallLoadMore(2);
        //设置加载监听
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        for (int i = 0; i < itemLimit; i++) {
                            mList.add("item" + i + "after " + refreshTime + " times of refresh");
                        }
                        //刷新数据
                        mAdapter.notifyDataSetChanged();
                        if (mXRecyclerView != null) {
                            //刷新完成
                            mXRecyclerView.refreshComplete();
                        }
                    }
                }, 1000);
            }

            //上拉加载更多
            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < itemLimit; i++) {
                                mList.add("item" + (1 + mList.size()));
                            }
                            if (mXRecyclerView != null) {
                                //加载更多完成
                                mXRecyclerView.loadMoreComplete();
                                //刷新adapter
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < itemLimit; i++) {
                                mList.add("item" + (1 + mList.size()));
                            }
                            if (mXRecyclerView != null) {
                                //显示加载更多完成
                                mXRecyclerView.setNoMore(true);
                                //刷新数据
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times++;
            }
        });

        mList = new ArrayList<>();
        mAdapter=new LinearLayoutAdapter(mList);
        mAdapter.setClickCallBack(new LinearLayoutAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                mList.remove(pos);
                mXRecyclerView.notifyItemRemoved(mList,pos);
            }
        });
        mXRecyclerView.setAdapter(mAdapter);



    }
}
