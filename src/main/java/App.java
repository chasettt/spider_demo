import crawler.Crawler;
import task.DoubanBookListTask;

public class App {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();

        DoubanBookListTask task = new DoubanBookListTask("/Users/teng/Downloads/douban/booklist/", 0);
        task.setQueue(crawler.getQueue());
        // 开启第一个抓取的线程，从列表页获取每一本数的详情url,再放入队列
        crawler.setStartTask(task);
        try {
            crawler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
