package njust.dzh.ordersystem.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.ordersystem.Bean.Order;

public class OrderDao {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public OrderDao(Context context) {
        this.context = context;
    }

    public void openDB() throws SQLiteException{
        // 获取数据库访问对象
        dataBaseHelper = new DataBaseHelper(context);
        try {
            database = dataBaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            database = dataBaseHelper.getReadableDatabase();
        }
    }

    public void closeDB() {
        if (database != null) {
            database.close();
            database = null;
        }
    }

    // 删除订单
    public void deleteOrder(Order order) {
        database.delete("MyOrder", "id = ?", new String[] {order.getId()});
    }

    // 得到所有的订单对象
    public List<Order> getAllOrder() {
        List<Order> orderList = new ArrayList<>();
        Cursor cursor = database.query("MyOrder", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Order order = new Order(id, time, price, content);
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }

}
