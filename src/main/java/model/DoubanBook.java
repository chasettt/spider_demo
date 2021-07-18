package model;

import entity.DbBook;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.ibatis.session.SqlSession;
import util.DbClient;
import util.MyBatisUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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

    /**
     * 写入数据库
     */
    public void add() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet   rs = null;
        QueryRunner qr = new QueryRunner(DbClient.getDataSource());
        try {
//            conn = DbClient.getConnection();
            String selectSql = "select id,title from douban_book where id=" + getId();
            DoubanBook book = qr.query(selectSql, new BeanHandler<DoubanBook>(DoubanBook.class));
            System.out.println(book);
//            System.exit(0);
//            pstmt = conn.prepareStatement(selectSql);
//            pstmt.setInt(1, getId());
//
//            rs = pstmt.executeQuery();
            pstmt = null;
            if (rs.next()) {
                // 更新
                String updateSql = "update douban_book set " +
                        "title='" + getTitle() +
                        "' ,author='" + getAuthor() +
                        "', price=" + getPrice() +
                        ", publisher='" + getPublisher() +
                        "', rating=" + getRating() +
                        ", star=" + getStar() +
                        ", pic='" + getPic() +
                        "',publishTime='" + getPublishTime() +
                        "', descs='" + getDesc() +
                        "' where id=" + getId();
                pstmt = conn.prepareStatement(updateSql);
                pstmt.executeUpdate();
            } else {
                // 新增
                String insertSql = "insert into douban_book(id,title,author,price,publisher,rating,star,pic,publishTime,descs) values(?,?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, getId());
                pstmt.setString(2, getTitle());
                pstmt.setString(3, getAuthor());
                pstmt.setDouble(4, getPrice());
                pstmt.setString(5, getPublisher());
                pstmt.setDouble(6, getRating());
                pstmt.setDouble(7, getStar());
                pstmt.setString(8, getPic());
                pstmt.setString(9, getPublishTime());
                pstmt.setString(10, getDesc());

                int cnt = pstmt.executeUpdate();
                System.out.println(cnt);
            }
            DbUtils.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
//        DoubanBook book = new DoubanBook();
//        book.setId(1);
//        book.setPrice(11.5);
//        book.setTitle("test");
//        book.setAuthor("th");
//        book.setPublisher("th press");
//        book.setRating(9.8);
//        book.setStar(4.5);
//        book.setPic("www.baidu.com");
//        book.setPublishTime("2021-6-1");
//        book.setDesc("ahahahahahha");

        SqlSession session = null;
        try {
            session = MyBatisUtil.openSession();
            List<DbBook> list = session.selectList("doubanbook.selectAll");
            for (DbBook b : list) {
                System.out.println(b.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MyBatisUtil.closeSession(session);
        }
    }
}
