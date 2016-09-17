package net.javacrumbs.codecamp.common;

public class InMemoryLoggerTest extends AbstractMessageStoreTest {
    protected Logger createMessageStore() {
        return new InMemoryLogger();
    }
}