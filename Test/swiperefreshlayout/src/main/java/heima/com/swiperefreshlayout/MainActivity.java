package heima.com.swiperefreshlayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private List<ItemBean> mList;
    private int[] images = new int[]{R.drawable.h42, R.drawable.p1, R.drawable.p2, R.drawable.p3,
            R.drawable.p4, R.drawable.p5, R.drawable.p7, R.drawable.p8, R.drawable.p11};
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_swipe);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ItemBean bean = new ItemBean();
            bean.id = images[i];
            bean.title = "这是第" + i + "个图片";
            mList.add(bean);
        }
        //获取RecyclerView对象
        //设置布局管理器
//        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        //设置适配器
        recyclerview.setAdapter(new MyRecyclerAdapter(this, mList));

        //对SwipeRefreshLayout的设置
       initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        //设置进度条的背景
        swiperefresh.setProgressBackgroundColorSchemeColor(Color.GRAY);
        //设置进度条中箭头的颜色
        swiperefresh.setColorSchemeColors(Color.RED,Color.BLUE,Color.YELLOW);
        //设置下拉刷新监听
        Log.e(TAG, "initSwipeRefresh: " +"swiperefresh.isRefreshing()" + swiperefresh.isRefreshing());
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "initSwipeRefresh: " +"swiperefresh.isRefreshing()" + swiperefresh.isRefreshing());
                Toast.makeText(MainActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
                //模拟数据加载
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载完数据后隐藏
                        swiperefresh.setRefreshing(false);
                    }
                },3000);

            }
        });
    }
}
