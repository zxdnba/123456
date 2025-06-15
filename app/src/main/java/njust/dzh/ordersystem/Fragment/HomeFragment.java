package njust.dzh.ordersystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import njust.dzh.ordersystem.Bean.Food;
import njust.dzh.ordersystem.Adapter.FoodAdapter;
import njust.dzh.ordersystem.R;

public class HomeFragment extends Fragment {
    private Food[] foods = {new Food("湖北热干面", R.drawable.p1, "18", "4.7", "热干面是武汉最出名的小吃之一，热干面色泽黄而油润，味道鲜美，由于热量高，也可以当作主食，营养早餐，补充人体所需的能量。"),
            new Food("上海生煎包", R.drawable.p2, "15", "4.5", "上海生煎包外皮底部煎得金黄色，上半部撒了一些芝麻、香葱。闻起来香香的，咬一口满嘴汤汁，颇受上海人喜爱。成品面白，软而松，肉馅鲜嫩，中有卤汁，咬嚼时有芝麻及葱香味，以出锅热吃为佳"),
            new Food("海南文昌鸡", R.drawable.p3, "58", "4.7", "海南文昌鸡因产自文昌故名文昌鸡，是海南省地方优良肉鸡品种，具有四百多年的传统名牌产品，以其体型方圆，脚胫细短，皮薄骨酥，肉质香甜嫩滑，营养丰富，具有色、香、味、型、营养俱佳，百吃不厌等特色，荣居海南“四大名菜”之首"),
            new Food("陕西羊肉泡馍", R.drawable.p4, "25", "4.6", "羊肉泡馍简称羊肉泡、泡馍，原料主要有羊肉、葱末、粉丝、糖蒜等，古称羊羹。养羹是用羊肉烹制的羹汤，西北美馔，烹制精细，料重味醇，肉烂汤浓，肥而不腻，营养丰富，香气四溢，诱人食欲，食后回味无穷。"),
            new Food("广东白切鸡", R.drawable.p5, "38", "4.4", "广东白切鸡起源于清代的岭南地区民间，在南方菜系中普遍存在。不仅广东人喜欢吃，在广西也很受欢迎，形状美观，皮黄肉白，肥嫩鲜美，滋味异常鲜美，十分可口"),
            new Food("湖南臭豆腐", R.drawable.p6, "12", "4.8", "湖南臭豆腐是湖南省长沙市的一道地方传统名吃，该菜品采取独特的工艺流程，采用祖传秘方，精制而成，源自长沙原汁原味的臭豆腐，其色、香、味俱佳，外酥里嫩，清香可口，奇特诱人、亦臭亦香，做工精细，口味极佳。"),
            new Food("山西刀削面", R.drawable.p7, "18", "4.9", "刀削面是山西的一种特色传统面食，因其风味独特，驰名中外。入口外滑内筋，软而不粘，越嚼越香。"),
            new Food("云南过桥米线", R.drawable.p8, "18", "4.5", "云南过桥米线是全国的经典特色小吃，酸辣特色老少皆宜，养胃开胃，四季可食。"),
            new Food("河南胡辣汤", R.drawable.p9, "16", "4.9", "河南胡辣汤，是中国北方早餐中常见的传统汤类名吃，特点是汤味浓郁、汤色靓丽、汤汁粘稠，香辣可口，十分适合配合其它早点进餐"),
            new Food("北京烤鸭", R.drawable.p10, "38", "4.7", "北京烤鸭是具有世界声誉的北京著名菜式，起源于中国南北朝时期，《食珍录》中已记有炙鸭，在当时是宫廷食品。用料为优质肉食鸭北京鸭，果木炭火烤制，色泽红润，肉质肥而不腻，外脆里嫩"),
            new Food("重庆辣子鸡", R.drawable.p11, "28", "4.9", "辣子鸡是一道经典的川菜，一般以整鸡为主料，加上葱、干辣椒、花椒、盐、胡椒、味精等多种材料精制而成，虽然是同一道菜，各地制作也各有特色。"),
            new Food("广西螺蛳粉", R.drawable.p12, "15", "4.6", "广西螺蛳粉是柳州最出名的，也是最受大众欢迎的小吃之一，辣、爽、鲜、酸、烫是它最大的特点，")
    };

    private List<Food> foodList = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // 创建数据源
        initFoods();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // 创建适配器，同时加载数据源
        foodAdapter = new FoodAdapter(foodList);
        // 设置适配器
        recyclerView.setAdapter(foodAdapter);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.Lavender);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFoods();
            }
        });
        return view;
    }

    private void initFoods() {
        foodList.clear();
        Random random = new Random();
        int index = random.nextInt(foods.length);
        for (int i = index; i < foods.length; i++) {
            foodList.add(foods[i]);
        }
        for (int i = 0; i < index; i++) {
            foodList.add(foods[i]);
        }
    }

    private void refreshFoods() {
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
                        initFoods();
                        foodAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
