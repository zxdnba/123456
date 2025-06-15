package njust.dzh.ordersystem.Bean;

import java.io.Serializable;

import njust.dzh.ordersystem.R;

public class Food implements Serializable {

    private String name;
    private int imageId;
    private String price;
    private String score;
    private String comment;

    public Food(String name, int imageId, String price, String score, String comment) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
        this.score = score;
        this.comment = comment;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
