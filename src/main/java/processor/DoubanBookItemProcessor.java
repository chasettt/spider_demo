package processor;

import esmodel.EsDoubanBook;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.client.RequestOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import entity.DbBook;
import util.*;

public class DoubanBookItemProcessor {
    private Document document;

    private int id;

    private DbBook book;

    public DoubanBookItemProcessor(String url) {
        // 从url上获取图书id
        String[] urlItems = url.split("\\/");
        this.id = Integer.parseInt(urlItems[urlItems.length - 1]);
        String html = HttpClient.doGet(url);

        this.document = Jsoup.parse(html);
//        FileUtil.saveFile(html, "/Users/teng/Downloads/douban/books/" + id + ".txt");
//        this.document = Jsoup.parse(FileUtil.readFile(url));
    }

    public DbBook getItem() {
        Elements e = document.select("div[id=wrapper]");

        String title = e.select("div[id=mainpic] .nbg").attr("title");
        String pic   = e.select("div[id=mainpic] .nbg").attr("href");
//        String   author  = e.select("div[id=info] span a").text();
//        Elements details = e.select("div[id=info]");
//
//        // @todo 这里取的有问题，后期再优化
//        String publisher   = details.first().childNodes().get(5).toString().trim();
//        String publishTime = details.first().childNodes().get(9).toString().trim();
//        String priceStr    = details.first().childNodes().get(17).toString().trim();
//        double price       = Double.valueOf(priceStr.replace("元", ""));
        String ratingStr = e.select(".rating_num").text();
        double rating    = 0;
        if (!ratingStr.isEmpty()) {
            rating = Double.valueOf(ratingStr);
        }
        String starStr = e.select(".rating_right .ll").attr("class").replace("ll bigstar", "");
        double star    = 0;
        try {
            star = Double.valueOf(starStr) / 10;
        } catch (Exception ex) {

        }

        DbBook book = new DbBook();
        book.setId(id);
        book.setTitle(title);
        book.setPic(pic);
        book.setRating(rating);
        book.setStar(star);
        // @todo 这里取的有问题，后期再优化
        book.setAuthor("abc");
        book.setPublisher("hello");
        book.setPublishTime("2021-07");
        book.setPrice(12.6);
//        book.setAuthor(author);
//        book.setPublisher(publisher);
//        book.setPublishTime(publishTime);
//        book.setPrice(price);
        System.out.println(book);
        this.book = book;

        save();
        return book;
    }

    public Document getDocument() {
        return document;
    }

    public void save() {
        getItem();
        // 保存至mysql
        saveToDb();
        // 保存至es
        saveToEs();
    }

    private void saveToDb() {
        SqlSession session = null;
        try {
            session = MyBatisUtil.openSession();
            DbBook newbook = session.selectOne("doubanbook.selectOne", book.getId());
            if (newbook == null) {
                // 新增
                int num = session.insert("doubanbook.addBook", book);
            } else {
                // 更新
                int num = session.update("doubanbook.updateBook", book);
            }
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
        } finally {
            MyBatisUtil.closeSession(session);
        }
    }

    private void saveToEs() {
        EsDoubanBook esDoubanBook = new EsDoubanBook();
        BulkRequest  bulkRequest  = new BulkRequest();
        bulkRequest.add(esDoubanBook.add(book, String.valueOf(book.getId())));

        try {
            EsClient.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            System.out.println("添加失败:" + e);
        }
    }

    public static void main(String[] args) {
        DoubanBookItemProcessor processor  = new DoubanBookItemProcessor("/Users/teng/Downloads/douban/books/1007305.txt");
        DbBook                  doubanBook = processor.getItem();
    }
}
