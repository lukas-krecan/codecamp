package net.javacrumbs.codecamp.common;

public class CsvFileLoggerTest extends AbstractMessageStoreTest {
    protected Logger createMessageStore() {
        return new CsvFileLogger();
    }
}