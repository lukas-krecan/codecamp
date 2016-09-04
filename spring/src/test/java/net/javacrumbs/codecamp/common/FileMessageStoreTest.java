package net.javacrumbs.codecamp.common;

public class FileMessageStoreTest extends AbstractMessageStoreTest {
    protected MessageStore createMessageStore() {
        return new FileMessageStore();
    }
}