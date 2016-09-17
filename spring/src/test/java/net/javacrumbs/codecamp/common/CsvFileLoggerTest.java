package net.javacrumbs.codecamp.common;

public class CsvFileLoggerTest extends AbstractMessageStoreTest {
    protected ReadableLogger createLogger() {
        return new CsvFileLogger();
    }
}