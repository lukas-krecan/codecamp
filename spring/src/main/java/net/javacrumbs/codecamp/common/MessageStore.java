package net.javacrumbs.codecamp.common;

import java.util.List;

public interface MessageStore {
    List<Message> getMessagesIn(String thread);

    void addMessage(String thread, Message message);

    void clear();
}
