package esmodel;

import entity.DbBook;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import util.EsClient;

import java.util.HashMap;
import java.util.Map;

public class EsDoubanBook {
    private DbBook book;

    public void save(DbBook book, String id) {
        BulkRequest  bulkRequest = new BulkRequest();
        IndexRequest request     = new IndexRequest("douban_book");

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
        bulkRequest.add(request);

        try {
            EsClient.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            System.out.println("添加失败:" + e);
        }
    }
}
