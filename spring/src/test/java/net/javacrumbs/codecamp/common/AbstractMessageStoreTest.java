package net.javacrumbs.codecamp.common;

import org.junit.Before;
import org.junit.Test;

import static net.javacrumbs.codecamp.common.TestSupport.NAME;
import static net.javacrumbs.codecamp.common.TestSupport.THREAD;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public abstract class AbstractMessageStoreTest {

    private final MessageStore messageStore = createMessageStore();

    protected abstract MessageStore createMessageStore();

    @Before
    public void clear() {
        messageStore.clear();
    }

    @Test
    public void newThreadShouldBeEmpty() {
        assertThat(messageStore.getMessagesIn(THREAD)).isEmpty();
    }

    @Test
    public void shouldBeAbleToAddAndRetreiveInReverseOrder() {
        Message message1 = new Message("text1", NAME);
        Message message2 = new Message("text2", NAME);
        messageStore.addMessage(THREAD, message1);
        messageStore.addMessage(THREAD, message2);
        assertThat(messageStore.getMessagesIn(THREAD)).containsExactly(message2, message1);
    }
}