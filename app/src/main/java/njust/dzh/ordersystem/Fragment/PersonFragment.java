package njust.dzh.ordersystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import njust.dzh.ordersystem.Activity.OrderActivity;
import njust.dzh.ordersystem.Adapter.OrderAdapter;
import njust.dzh.ordersystem.Bean.Order;
import njust.dzh.ordersystem.R;

public class PersonFragment extends Fragment implements View.OnClickListener{
    private TextView order, person, share;
    private CircleImageView circleImage;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        // 获取控件实例
        order = view.findViewById(R.id.order);
        person = view.findViewById(R.id.person);
        share = view.findViewById(R.id.share);
        circleImage = view.findViewById(R.id.circle_image);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);
        // 设置监听器
        order.setOnClickListener(this);
        person.setOnClickListener(this);
        share.setOnClickListener(this);
        circleImage.setOnClickListener(this);

        // 菜单项选中事件的监听器
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        return view;
    }

    // 分享这个软件到其他用户
    private void shareSoftware() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "想随时查找美食吗？快来下载点餐系统吧！";
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        startActivity(Intent.createChooser(intent,"分享到...."));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.person) {
            Toast.makeText(getContext(), "你查看了个人信息", Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(GravityCompat.START);
        } else if (view.getId() == R.id.order) {
            Toast.makeText(getContext(), "你查看了历史订单", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.share) {
            shareSoftware();
        }
    }
}