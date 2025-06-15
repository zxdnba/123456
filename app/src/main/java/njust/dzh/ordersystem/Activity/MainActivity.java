package njust.dzh.ordersystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import njust.dzh.ordersystem.Fragment.CartFragment;
import njust.dzh.ordersystem.Fragment.HomeFragment;
import njust.dzh.ordersystem.Fragment.PersonFragment;
import njust.dzh.ordersystem.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        replaceFragment(new HomeFragment());

        // 获取底部导航栏实例
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.home);

        // 底部导航栏监听器
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.cart) {
                    replaceFragment(new CartFragment());
                    return true;
                } else if (item.getItemId() == R.id.person) {
                    replaceFragment(new PersonFragment());
                    return true;
                }
                return false;
            }
        });
    }

    // 动态替换碎片
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        // 在碎片中模拟返回栈
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}