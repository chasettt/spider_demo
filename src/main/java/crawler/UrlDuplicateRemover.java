package crawler;

import task.Task;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UrlDuplicateRemover {
    // 所有爬取过的网页，用于去重
    private Set<String> urls = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    public boolean isDuplicate(Task task) {
        return urls.add(task.getUrl());
    }
}
