package net.javacrumbs.codecamp.common;

public class InMemoryMessageStoreTest extends AbstractMessageStoreTest {
    protected MessageStore createMessageStore() {
        return new InMemoryMessageStore();
    }
}