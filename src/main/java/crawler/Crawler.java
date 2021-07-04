package crawler;

import java.util.concurrent.*;
import task.DoubanBookListTask;
import task.Task;

public class Crawler {
    private static final int THREAD_NUM = 5;

    private CrawlerQueue taskQueue;

    private ExecutorService exec;

    public Crawler() {
        this.taskQueue = new CrawlerQueue();
        exec = Executors.newFixedThreadPool(THREAD_NUM);
    }
    // 设置开启的第一个任务
    public void setStartTask(DoubanBookListTask task) {
        exec.execute(task);
    }

    public CrawlerQueue getQueue() {
        return taskQueue;
    }

    public void start() throws Exception {
        Task task;
        int i = 0;
        while (true) {
            task = taskQueue.poll();
            System.out.println("poll:" + task);
            if (task == null) {
                Thread.sleep(500);
                if (i++ > 120) {
                    // 如果1分钟都没有从队列中取到数据，退出
                    System.exit(0);
                }
                continue;
            }
            i = 0;
            task.setQueue(taskQueue);
            // 从队列中取到任务，去执行
            // 这里有可能是书列表的任务，有可能是书详情的任务
            exec.execute(task);
        }
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        DoubanBookListTask task = new DoubanBookListTask("/Users/teng/Downloads/douban/booklist/", 0);
        task.setQueue(crawler.getQueue());
        crawler.setStartTask(task);
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
