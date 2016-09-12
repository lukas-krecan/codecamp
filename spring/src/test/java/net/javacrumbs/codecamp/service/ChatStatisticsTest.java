package net.javacrumbs.codecamp.service;

import net.javacrumbs.codecamp.common.Message;
import net.javacrumbs.codecamp.common.MessageStore;
import org.junit.Test;

import static java.util.Arrays.asList;
import static net.javacrumbs.codecamp.common.TestSupport.NAME;
import static net.javacrumbs.codecamp.common.TestSupport.THREAD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatStatisticsTest {
    private final MessageStore messageStore = mock(MessageStore.class);
    private final ChatStatistics chatStatistics = new ChatStatistics(messageStore);

    @Test
    public void shouldReturnEmptyIfThreadNotFound() {
        assertThat(chatStatistics.findLongestMessageInThread(THREAD).isPresent()).isFalse();
    }

    @Test
    public void shouldReturnLongest() {
        Message message1 = new Message("short", NAME);
        Message message2 = new Message("long message", NAME);
        when(messageStore.getMessagesIn(THREAD)).thenReturn(asList(message1, message2));

        assertThat(chatStatistics.findLongestMessageInThread(THREAD)).containsSame(message2);
    }


}