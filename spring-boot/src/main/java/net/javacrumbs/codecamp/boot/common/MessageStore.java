package net.javacrumbs.codecamp.boot.common;

import java.util.List;

public interface MessageStore {
    List<Message> getMessagesIn(String thread);

    void addMessage(String thread, Message message);

    void clear();

    void close();
}
