package task;

import crawler.CrawlerQueue;

public class Task implements Runnable, Scraper {
    private String url;

    private CrawlerQueue queue;

    public Task() {
    }

    public Task(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            scrape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CrawlerQueue getQueue() {
        return queue;
    }

    public void setQueue(CrawlerQueue queue) {
        this.queue = queue;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void scrape() {

    }
}
