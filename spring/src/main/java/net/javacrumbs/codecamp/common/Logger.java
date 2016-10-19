package net.javacrumbs.codecamp.common;

import javax.annotation.PreDestroy;

public interface Logger {
    void addMessage(Message message);

    void clear();

    @PreDestroy
    void close();
}
