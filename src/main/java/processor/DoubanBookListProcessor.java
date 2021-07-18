package processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import entity.DbBook;
import util.FileUtil;

import java.util.*;

public class DoubanBookListProcessor {
    private Document document;

    public DoubanBookListProcessor(String dir) {
        System.out.println(dir);
        this.document = Jsoup.parse(FileUtil.readFile(dir));
    }

    public ArrayList<DbBook> getItem() {
        Elements  items    = document.getElementsByClass("subject-item");
        ArrayList bookList = new ArrayList<DbBook>();

        for (Element e : items) {
            DbBook book = new DbBook();

            String url = e.select(".info h2 >a").attr("href");

            book.setUrl(url);

            bookList.add(book);
        }
        return bookList;
    }

    public Document getDocument() {
        return document;
    }

    public static void main(String[] args) {
        DoubanBookListProcessor processor = new DoubanBookListProcessor("/Users/teng/Downloads/douban/booklist/0.txt");
        processor.getItem();
    }
}
