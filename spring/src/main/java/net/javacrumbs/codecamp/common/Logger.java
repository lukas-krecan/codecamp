package net.javacrumbs.codecamp.common;

import javax.annotation.PreDestroy;
import java.util.List;

public interface Logger {
    List<Message> getMessages();

    void addMessage(Message message);

    void clear();

    @PreDestroy
    void close();
}
