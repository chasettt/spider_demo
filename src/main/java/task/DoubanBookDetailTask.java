package task;

import model.DoubanBook;
import processor.DoubanBookItemProcessor;

import java.util.ArrayList;

public class DoubanBookDetailTask extends Task {
    public DoubanBookDetailTask(String url) {
        super(url);
    }

    @Override
    public void run() {
        try {
            scrape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scrape() {
        System.out.println("detail_task:" + getUrl());
        // 爬取每本书的详情
        DoubanBookItemProcessor processor = new DoubanBookItemProcessor(getUrl());
        // 保存至es
        processor.save();
//        DoubanBook book = processor.getItem();
    }
}
