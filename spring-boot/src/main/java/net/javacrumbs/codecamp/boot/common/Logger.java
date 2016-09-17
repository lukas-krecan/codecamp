package net.javacrumbs.codecamp.boot.common;

import javax.annotation.PreDestroy;

public interface Logger {
    void addMessage(Message message);

    void clear();

    @PreDestroy
    void close();
}
