package model;

public class DoubanBook {
    // id
    private int    id;
    // 标题
    private String title;
    // 价格
    private double price;
    // 评分
    private double rating;
    // 星级
    private double star;
    // pub
    private String pub;
    // 简介
    private String desc;
    // 图片
    private String pic;
    // 作者
    private String author;
    // 出版社
    private String publisher;
    // 出版日期
    private String publishTime;

    private String url;

    public DoubanBook() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String Pub) {
        this.pub = pub;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
//        return "/Users/teng/Downloads/douban/books/0.txt";
    }

    @Override
    public String toString() {
        return this.getTitle() + " : " + this.author + this.publisher;
    }
}
