package net.javacrumbs.codecamp.common;

import javax.annotation.PreDestroy;
import java.util.List;

public interface Logger {
    void addMessage(Message message);

    void clear();

    @PreDestroy
    void close();
}
