package net.javacrumbs.codecamp.boot.common;

import java.util.List;


/**
 * Logger you can read from.
 */
public interface ReadableLogger extends Logger{
    List<Message> getMessages();
}
