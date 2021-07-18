package crawler;

import task.Task;

import java.util.concurrent.*;

public class CrawlerQueue {
    private LinkedBlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();

    public CrawlerQueue() {

    }

    public void add(Task task) {
        taskQueue.add(task);
    }

    public Task poll() {
        return taskQueue.poll();
    }

    public int getSize() {
        return taskQueue.size();
    }
}
