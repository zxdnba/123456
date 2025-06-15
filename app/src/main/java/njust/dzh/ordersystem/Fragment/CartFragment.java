package njust.dzh.ordersystem.Fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.ordersystem.Activity.OrderActivity;
import njust.dzh.ordersystem.Adapter.CartAdapter;
import njust.dzh.ordersystem.Bean.Cart;
import njust.dzh.ordersystem.Bean.Food;
import njust.dzh.ordersystem.DataBase.CartDao;
import njust.dzh.ordersystem.DataBase.FoodDao;
import njust.dzh.ordersystem.R;

public class CartFragment extends Fragment{
    public static final int UPDATE_CART = 1;
    private List<Cart> cartList = new ArrayList<>();;
    private CartAdapter cartAdapter;
    private CartDao cartDao;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initCart();
        // 当购物车为空时执行，否则跳过
        if (cartList.isEmpty()) {
            View view = inflater.inflate(R.layout.cart_empty, container, false);
            return view;
        }
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        // 获取控件实例
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.Lavender);
        // 设置布局管理器，一列显示
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        // 创建适配器
        cartAdapter = new CartAdapter(cartList);
        // 设置适配器
        recyclerView.setAdapter(cartAdapter);
        // 下拉刷新，重新加载数据源
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refershCart();
            }
        });
        // 订单提交
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("提示")
                        .setIcon(R.drawable.ic_order)
                        .setMessage("您确定要提交订单吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cartDao.openDB();
                                cartDao.commitOrder();
                                cartDao.clearCart();
                                cartDao.closeDB();
                                Toast.makeText(getContext(), "下单成功！请下拉刷新页面~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "订单已取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        return view;
    }

    // 初始化数据源，从数据库中获取所有记录的List对象，然后遍历存到cartList中
    private void initCart() {
        cartList.clear();
        cartDao = new CartDao(getContext());
        cartDao.openDB();
        List<Cart> tempList = cartDao.getAllCart();
        cartDao.closeDB();
        for (int i = 0; i < tempList.size(); i++) {
            cartList.add(tempList.get(i));
        }
    }

    // 刷新购物车
    private void refershCart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initCart();
                        cartAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }


    // 多线程，异步消息机制
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_CART:
                    cartAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

}
