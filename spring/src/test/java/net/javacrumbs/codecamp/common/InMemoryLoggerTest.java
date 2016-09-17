package net.javacrumbs.codecamp.common;

public class InMemoryLoggerTest extends AbstractMessageStoreTest {
    protected ReadableLogger createLogger() {
        return new InMemoryLogger();
    }
}