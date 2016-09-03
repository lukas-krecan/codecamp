package net.javacrumbs.codecamp.spring1;

import net.javacrumbs.codecamp.common.Message;
import net.javacrumbs.codecamp.common.MessageStore;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatStatisticsTest {
    private static final String THREAD = "thread";
    public static final String NAME = "name";
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