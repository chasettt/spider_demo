package task;

import crawler.CrawlerQueue;
import processor.*;
import entity.DbBook;

import java.util.*;

public class DoubanBookListTask extends Task {
    private int page;

    private static final int PAGE_MAX = 1;

    public DoubanBookListTask(String url, int page) {
        super(url);
        this.page = page;
    }

    @Override
    public void run() {
        try {
            this.scrape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scrape() {
        System.out.println("list_task:" + getPageUrl());
        // 因为频繁访问会被网站反爬限制，暂时先用读取本地文件替代
        DoubanBookListProcessor processor = new DoubanBookListProcessor(getPageUrl());
        // 从url中解析出每本书详情的url，再生成任务放入队列中
        ArrayList<DbBook> bookList  = processor.getItem();
        CrawlerQueue          queue = getQueue();
        for (DbBook book : bookList) {
            System.out.println(book.getUrl());
            // 生成每一本书详情的任务，放入队列
            Task task = new DoubanBookDetailTask(book.getUrl());
            task.setQueue(queue);
            queue.add(task);
        }
        // 生成下一页的任务，放入队列中
        if (page < PAGE_MAX) {
            page++;
            DoubanBookListTask nextTask = new DoubanBookListTask(getUrl(), page);
            nextTask.setQueue(getQueue());
            getQueue().add(nextTask);
        }
    }

    public String getPageUrl() {
        String url = super.getUrl();
        return url + page + ".txt";
    }
}
