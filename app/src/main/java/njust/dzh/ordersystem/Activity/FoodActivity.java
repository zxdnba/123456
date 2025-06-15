package njust.dzh.ordersystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import njust.dzh.ordersystem.Bean.Food;
import njust.dzh.ordersystem.DataBase.FoodDao;
import njust.dzh.ordersystem.R;

public class FoodActivity extends AppCompatActivity {
    public static final String FOOD_DATA = "food_data";
    private FloatingActionButton fab;
    private FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
    }

    private void initView() {
        foodDao = new FoodDao(this);
        Intent intent = getIntent();
        // 获取通过参数传递过来的序列化对象，向下转型为Food
        Food food = (Food)intent.getSerializableExtra(FOOD_DATA);
        // 获取对象的属性
        String foodName = food.getName();
        int foodImageId = food.getImageId();
        String foodComment = food.getComment();
        // 获取工具栏、折叠栏等实例
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView foodImageView = findViewById(R.id.food_image_view);
        TextView foodContentText = findViewById(R.id.food_content_text);
        fab = findViewById(R.id.fab);
        // 设置自定义工具栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // 设置默认返回按钮
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(foodName);
        Glide.with(this).load(foodImageId).into(foodImageView);
        String foodContent = generateFoodContent(foodComment);
        foodContentText.setText(foodContent);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                foodDao.openDB();
                foodDao.addFood(food);
                Toast.makeText(FoodActivity.this, "成功将"+foodName+"加入购物车！", Toast.LENGTH_SHORT).show();
                foodDao.closeDB();
            }
        });
    }

    // 生成食物内容
    private String generateFoodContent(String foodComment) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            stringBuilder.append(foodComment);
        }
        return stringBuilder.toString();
    }
    // 菜单栏返回按钮的点击事件
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