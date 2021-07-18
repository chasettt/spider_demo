package esmodel;

import entity.DbBook;
import org.elasticsearch.action.index.IndexRequest;

import java.util.HashMap;
import java.util.Map;

public class EsDoubanBook {
    public IndexRequest add(DbBook book, String id) {
        IndexRequest request = new IndexRequest("douban_book");
        request.id(id);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("title", book.getTitle());
        jsonMap.put("author", book.getAuthor());
        jsonMap.put("price", book.getPrice());
        jsonMap.put("publisher", book.getPublisher());
        jsonMap.put("rating", book.getRating());
        jsonMap.put("star", book.getStar());
        jsonMap.put("pic", book.getPic());
        jsonMap.put("publish_time", book.getPublishTime());
        jsonMap.put("desc", book.getDesc());

        request.source(jsonMap);

        return request;
    }

    public IndexRequest add(DbBook book) {
        IndexRequest request = new IndexRequest("douban_book");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("title", book.getTitle());
        jsonMap.put("author", book.getAuthor());
        jsonMap.put("price", book.getPrice());
        jsonMap.put("publisher", book.getPublisher());
        jsonMap.put("rating", book.getRating());
        jsonMap.put("star", book.getStar());
        jsonMap.put("pic", book.getPic());
        jsonMap.put("publish_time", book.getPublishTime());
        jsonMap.put("desc", book.getDesc());

        request.source(jsonMap);

        return request;
    }
}
