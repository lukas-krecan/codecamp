package net.javacrumbs.codecamp.common;

import java.util.List;

public interface MessageStore {
    List<Message> getMessagesIn(String thread);
}
