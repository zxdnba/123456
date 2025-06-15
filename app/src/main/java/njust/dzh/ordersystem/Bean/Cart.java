package njust.dzh.ordersystem.Bean;

public class Cart {
    private String name;
    private int imageId;
    private String price;
    private int num;

    public Cart(String name, int imageId, String price, int num) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
