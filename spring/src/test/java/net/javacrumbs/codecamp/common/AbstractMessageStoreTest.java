package net.javacrumbs.codecamp.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.javacrumbs.codecamp.common.Message.Severity.DEBUG;
import static net.javacrumbs.codecamp.common.Message.Severity.INFO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public abstract class AbstractMessageStoreTest {

    private final Logger logger = createMessageStore();

    protected abstract Logger createMessageStore();

    @Before
    public void clear() {
        logger.clear();
    }

    @After
    public void close() {
        logger.close();
    }

    @Test
    public void newThreadShouldBeEmpty() {
        assertThat(logger.getMessages()).isEmpty();
    }

    @Test
    public void shouldBeAbleToAddAndRetreiveInReverseOrder() {
        Message message1 = new Message(DEBUG, "text1");
        Message message2 = new Message(INFO, "text2");
        logger.addMessage(message1);
        logger.addMessage(message2);
        assertThat(logger.getMessages()).containsExactly(message2, message1);
    }
}