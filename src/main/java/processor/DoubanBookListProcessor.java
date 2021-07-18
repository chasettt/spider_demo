package processor;

import esmodel.EsDoubanBook;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.client.RequestOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import model.DoubanBook;
import util.EsClient;
import util.FileUtil;

import java.util.*;

public class DoubanBookListProcessor {
    private Document document;

    public DoubanBookListProcessor(String dir) {
        System.out.println(dir);
        this.document = Jsoup.parse(FileUtil.readFile(dir));
    }

    public ArrayList<DoubanBook> getItem() {
        Elements  items    = document.getElementsByClass("subject-item");
        ArrayList bookList = new ArrayList<DoubanBook>();

        for (Element e : items) {
            DoubanBook book = new DoubanBook();

            String url = e.select(".info h2 >a").attr("href");

            book.setUrl(url);

            bookList.add(book);
        }
        return bookList;
    }

    public Document getDocument() {
        return document;
    }

//    public void save() {
//        EsDoubanBook          esDoubanBook = new EsDoubanBook();
//        ArrayList<DoubanBook> bookList     = this.getItem();
//
//        BulkRequest bulkRequest = new BulkRequest();
//
//        for (DoubanBook db : bookList) {
//            System.out.println(db.getTitle());
//            bulkRequest.add(esDoubanBook.add(db));
//        }
//        try {
//            EsClient.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            System.out.println("添加失败:" + e);
//        }
//    }

    public static void main(String[] args) {
        DoubanBookListProcessor processor = new DoubanBookListProcessor("/Users/teng/Downloads/douban/booklist/0.txt");
        processor.getItem();
    }
}
