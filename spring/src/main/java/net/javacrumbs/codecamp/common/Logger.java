package net.javacrumbs.codecamp.common;

import java.util.List;

public interface Logger {
    List<Message> getMessages();

    void addMessage(Message message);

    void clear();

    void close();
}
