package net.javacrumbs.codecamp.solution;

import net.javacrumbs.codecamp.common.Logger;
import net.javacrumbs.codecamp.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NamedLogger {
    private final Map<String, Logger> loggers;

    @Autowired
    public NamedLogger(Map<String, Logger> loggers) {
        this.loggers = loggers;
    }

    public void log(String name, Message message) {
        loggers.get(name).addMessage(message);
    }
}
