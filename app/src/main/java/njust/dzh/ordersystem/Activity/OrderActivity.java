package njust.dzh.ordersystem.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.ordersystem.Adapter.OrderAdapter;
import njust.dzh.ordersystem.Bean.Cart;
import njust.dzh.ordersystem.Bean.Order;
import njust.dzh.ordersystem.DataBase.OrderDao;
import njust.dzh.ordersystem.R;

public class OrderActivity extends AppCompatActivity {
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private OrderDao orderDao;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }

    private void initView() {
        // 滚动布局
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // 下拉刷新
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.Lavender);
        // 自定义工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initOrder();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshOrder();
            }
        });
    }

    // 初始化数据（获取数据源）
    private void initOrder() {
        orderList.clear();
        orderDao = new OrderDao(this);
        orderDao.openDB();
        List<Order> tempList = orderDao.getAllOrder();
        orderDao.closeDB();
        for (int i = 0; i < tempList.size(); i++) {
            orderList.add(tempList.get(i));
        }
    }

    // 刷新订单信息
    private void refreshOrder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initOrder();
                        orderAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(OrderActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}