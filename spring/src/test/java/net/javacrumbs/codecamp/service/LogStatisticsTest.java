package net.javacrumbs.codecamp.service;

import net.javacrumbs.codecamp.common.Message;
import net.javacrumbs.codecamp.common.ReadableLogger;
import org.junit.Test;

import static java.util.Arrays.asList;
import static net.javacrumbs.codecamp.common.Message.Severity.DEBUG;
import static net.javacrumbs.codecamp.common.Message.Severity.INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogStatisticsTest {
    private final ReadableLogger logger = mock(ReadableLogger.class);
    private final LogStatistics logStatistics = new LogStatistics(logger);

    @Test
    public void shouldReturnEmptyIfThreadNotFound() {
        assertThat(logStatistics.findLongestMessage()).isEmpty();
    }

    @Test
    public void shouldReturnLongest() {
        Message message1 = new Message(DEBUG, "short");
        Message message2 = new Message(INFO, "long message");
        when(logger.getMessages()).thenReturn(asList(message1, message2));

        assertThat(logStatistics.findLongestMessage()).containsSame(message2);
    }


}