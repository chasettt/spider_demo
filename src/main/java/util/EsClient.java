package util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class EsClient {
    private static final int                 PORT   = 9200;
    private static final String              HOST   = "127.0.0.1";
    private static       RestHighLevelClient client = null;

    private EsClient() {
    }

    public static RestHighLevelClient getClient() {
        if (client == null) {
            client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(HOST, PORT, "http"))
            );
        }
        return client;
    }
}
