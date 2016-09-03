package net.javacrumbs.codecamp.common;

import org.junit.Test;

import static net.javacrumbs.codecamp.common.TestSupport.THREAD;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class InMemoryMessageStoreTest {
    private final InMemoryMessageStore messageStore = new InMemoryMessageStore();

    @Test
    public void newThreadShouldBeEmpty() {
        assertThat(messageStore.getMessagesIn(THREAD)).isEmpty();
    }

    @Test
    public void shouldBeAbleToAddAndRetreive() {
        Message message = new Message("text", "name");
        messageStore.addMessage(THREAD, message);
        assertThat(messageStore.getMessagesIn(THREAD)).containsExactly(message);
    }
}