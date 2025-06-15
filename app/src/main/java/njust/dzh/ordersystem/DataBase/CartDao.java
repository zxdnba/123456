package njust.dzh.ordersystem.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import njust.dzh.ordersystem.Bean.Cart;

public class CartDao {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public CartDao (Context context) {
        this.context = context;
    }

    public void openDB() throws SQLiteException {
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

    // 增加食物数量
    public void addCartNum(Cart cart) {
        ContentValues values = new ContentValues();
        int num = queryCartNum(cart.getName());
        if (num != 0) {
            // 单价 * 数量 = 总价
            double price = Double.parseDouble(cart.getPrice()) / cart.getNum() * (num + 1);
            values.put("price", price);
            values.put("num", num + 1);
            database.update("Cart", values, "name = ?", new String[] {cart.getName()});
        }

    }

    // 减少食物数量
    public void minusCartNum(Cart cart) {
        ContentValues values = new ContentValues();
        int num = queryCartNum(cart.getName());
        // 数量为0或为1时不能进行减少操作
        if (num > 1) {
            double price = Double.parseDouble(cart.getPrice()) / cart.getNum() * (num - 1);
            values.put("price", price);
            values.put("num", num - 1);
            database.update("Cart", values, "name = ?", new String[] {cart.getName()});
        }
    }

    // 从购物车删除食物（长按删除 or 提交订单）
    public void deleteCart(Cart cart) {
        database.delete("Cart", "name = ?", new String[] {cart.getName()});
    }

    public int queryCartNum(String name) {
        Cursor cursor = database.query("Cart", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String cartName = cursor.getString(cursor.getColumnIndex("name"));
                int cartNum = cursor.getInt(cursor.getColumnIndex("num"));
                if (cartName.equals(name)) {
                    return cartNum;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    // 获取购物车的所有食物，以列表形式返回
    public List<Cart> getAllCart() {
        Cursor cursor = database.query("Cart", null, null, null, null, null, null);
        List<Cart> cartList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                int num = cursor.getInt(cursor.getColumnIndex("num"));
                Cart cart = new Cart(name, imageId, price, num);
                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }

    // 增加购物车商品时的价格
    public String getPrice(Cart cart) {
        Cursor cursor = database.query("Cart", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                if (cart.getName().equals(name)) {
                    return price;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return "0";
    }

    // 清空购物车
    public void clearCart() {
        Cursor cursor = database.query("Cart", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
//                if (mListCartName.contains(name)) {
                    database.delete("Cart", "name = ?", new String[] {name});
//                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    // 提交订单时，内容保存到订单表中
    public void commitOrder() {
        // 获取表中所有购物车记录
        List<Cart> listCart = getAllCart();
        // 订单价格
        double price = 0.0;
        // 订单内容
        StringBuilder orderContent = new StringBuilder();
        for (int i = 0; i < listCart.size(); i++) {
            orderContent.append(listCart.get(i).getName());
            orderContent.append("x");
            orderContent.append(listCart.get(i).getNum());
            orderContent.append(" ");
            String orderPrice = listCart.get(i).getPrice();
            price += Double.parseDouble(orderPrice);
        }
        String content = orderContent.toString();
        // 订单时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formater.format(calendar.getTime());
        ContentValues values = new ContentValues();
        // 订单编号
        StringBuilder orderId = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            Random random = new Random();
            int num = random.nextInt(10);
            orderId.append(String.valueOf(num));
        }
        // 组装数据
        values.put("id", orderId.toString());
        values.put("time", time);
        values.put("price", price);
        values.put("content", content);
        // 插入数据
        database.insert("MyOrder", null, values);
    }

}
