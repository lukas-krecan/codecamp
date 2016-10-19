package net.javacrumbs.codecamp.solution;

import net.javacrumbs.codecamp.common.Logger;
import net.javacrumbs.codecamp.common.Message;
import net.javacrumbs.codecamp.common.ReadableLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoggerConfiguration.class)
public class NamedLoggerTest {

    @Autowired
    private ReadableLogger fileLogger;

    @Autowired
    private ReadableLogger inMemoryLogger;

    @Autowired
    private NamedLogger namedLogger;

    @Before
    public void clean() {
        fileLogger.clear();
    }

    @Test
    public void shouldLogToCorrectLogger() {
        Message message = new Message(Message.Severity.INFO, "aaaa");
        namedLogger.log("fileLogger", message);

        assertThat(fileLogger.getMessages()).contains(message);
        assertThat(inMemoryLogger.getMessages()).isEmpty();
    }


}