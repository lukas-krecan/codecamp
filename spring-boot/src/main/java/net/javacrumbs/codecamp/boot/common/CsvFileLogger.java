/**
 * Copyright 2009-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.codecamp.boot.common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class CsvFileLogger implements ReadableLogger {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CsvFileLogger.class);
    private final File file;

    public CsvFileLogger() {
        this(new File("messages.db"));
    }

    public CsvFileLogger(File file) {
        logger.info("action=initializing file={}", file);
        this.file = file;
    }

    @Override
    public List<Message> getMessages() {
        try(CSVParser parser = CSVFormat.RFC4180.parse(new FileReader(file))) {
            List<Message> result = parser.getRecords().stream().map(CsvFileLogger::parseRecord).collect(toList());
            Collections.reverse(result);
            return result;
        } catch (FileNotFoundException e) {
            return emptyList();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Message parseRecord(CSVRecord record) {
        return new Message(Message.Severity.valueOf(record.get(0)), record.get(1), Instant.parse(record.get(2)));
    }

    @Override
    public void addMessage(Message message) {
        try {
            String newRow = CSVFormat.RFC4180.format(message.getSeverity(), message.getMessage(), message.getTime());
            Files.write(file.toPath(), singletonList(newRow), APPEND, CREATE);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        logger.info("action=closing");
    }

}
