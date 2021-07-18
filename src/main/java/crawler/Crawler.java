package crawler;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import task.DoubanBookListTask;
import task.Task;

// 主方法
public class Crawler {
    // 线程数
    private static final int THREAD_NUM = 5;

    private static final int MAX_QUEUE_NUM = 1000;
    // 任务队列
    private CrawlerQueue taskQueue;

    private UrlDuplicateRemover remover = new UrlDuplicateRemover();

    private ExecutorService exec;

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private static int i;

    public Crawler() {
        this.init();
    }
    // 初始化
    private void init() {
        taskQueue = new CrawlerQueue();
        exec = Executors.newFixedThreadPool(THREAD_NUM);
    }

    // 设置开启的第一个任务
    public void setStartTask(Task task) {
        exec.execute(task);
    }

    public CrawlerQueue getQueue() {
        return taskQueue;
    }

    private void waitNewTask() throws InterruptedException {
        Thread.sleep(1000);
        if (i++ > 60) {
            // 如果1分钟都没有从队列中取到数据，退出
            exec.shutdownNow();
        }
    }

    public void start() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            Task task = taskQueue.poll();
            System.out.println("poll:" + task);
            if (task == null) {
                waitNewTask();
                continue;
            }
            i = 0;
            // 执行任务前判断url是否重复
            boolean isDuplicate = remover.isDuplicate(task);
            if (!isDuplicate) {
                if (taskQueue.getSize() > MAX_QUEUE_NUM) {
                    // 如果队列数量超过上限，等待

                }
                task.setQueue(taskQueue);
                // 从队列中取到任务，去执行
                // 这里有可能是书列表的任务，有可能是书详情的任务
                exec.execute(task);
            }
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
