package njust.dzh.ordersystem.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.ordersystem.Bean.Cart;
import njust.dzh.ordersystem.Bean.Food;

public class FoodDao {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FoodDao (Context context) {
        this.context = context;
    }

    // 打开数据库
    public void openDB() throws SQLiteException {
        dataBaseHelper = new DataBaseHelper(context);
        try {
            database = dataBaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            database = dataBaseHelper.getReadableDatabase();
        }
    }

    // 关闭数据库
    public void closeDB() {
        if(database != null) {
            database.close();
            database = null;
        }
    }

    // 添加食物到购物车
    public void addFood(Food food) {
        // 没有在购物车中，则插入数据
        if (!queryFood(food.getName())) {
            ContentValues values = new ContentValues();
            // 组装一条数据
            values.put("name", food.getName());
            values.put("imageId", food.getImageId());
            values.put("price", food.getPrice());
            values.put("num", 1);
            // 插入一条数据
            database.insert("Cart", null, values);
        } else {
            // 已经在购物车中，更新数据
            ContentValues values = new ContentValues();
            int num = queryFoodNum(food.getName());
            if (num != 0) {
                values.put("num", num + 1);
                double singlePrice = Double.parseDouble(food.getPrice());
                double price = singlePrice * (num + 1);
                values.put("price", price);
                database.update("Cart", values, "name = ?", new String[] {food.getName()});
            }
        }
    }

    // 查询食物是否已经加入购物车
    public boolean queryFood(String name) {
        // 只查询食物名
        Cursor cursor = database.query("Cart", null, null, null, null, null,null);
        if (cursor.moveToFirst()) {
            do {
                String foodName = cursor.getString(cursor.getColumnIndex("name"));
                if (foodName.equals(name)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    // 查询食物数量
    public int queryFoodNum(String name) {
        Cursor cursor = database.query("Cart", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String foodName = cursor.getString(cursor.getColumnIndex("name"));
                int foodNum = cursor.getInt(cursor.getColumnIndex("num"));
                if (foodName.equals(name)) {
                    return foodNum;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

}
