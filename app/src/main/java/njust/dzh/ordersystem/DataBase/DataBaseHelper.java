package njust.dzh.ordersystem.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 数据库访问类
public class DataBaseHelper extends SQLiteOpenHelper {
    // 数据库名称
    public static final String DATABASE = "ordersystem.db";
    // 数据库版本号
    public static final int VERSION = 1;

    // 创建用户表User
    public static final String CREATE_USER = "create table User ("
            + "account text primary key,"
            + "password text)";

    // 创建购物车表Cart
    public static final String CREATE_CART = "create table Cart ("
            + "name text primary key,"
            + "imageId integer,"
            + "price text,"
            + "num integer)";

    // 创建订单表MyOrder
    public static final String CREATE_ORDER = "create table MyOrder ("
            + "id integer primary key,"
            + "time text,"
            + "price text,"
            + "content text)";

    // 创建DB对象时的构造函数
    public DataBaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CART);
        db.execSQL(CREATE_ORDER);
    }
    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Cart");
        db.execSQL("drop table if exists MyOrder");
        onCreate(db);
    }
}
