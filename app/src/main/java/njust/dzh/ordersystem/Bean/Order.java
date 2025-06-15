package njust.dzh.ordersystem.Bean;

import java.util.Date;

public class Order {
    // 订单编号
    private String id;
    // 订单时间
    private String time;
    // 订单总价
    private String price;
    // 订单内容
    private String content;

    public Order(String id, String time, String price, String content) {
        this.id = id;
        this.time = time;
        this.price = price;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
