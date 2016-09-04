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
package net.javacrumbs.codecamp.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Repository
public class FileMessageStore implements MessageStore {
    private final Logger logger = LoggerFactory.getLogger(FileMessageStore.class);

    private final Path dir = Paths.get(System.getProperty("java.io.tmpdir"), "codecamp-demo");

    private final ObjectMapper mapper;

    public FileMessageStore() {
        logger.info("action=initializing dir={}", dir);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Message> getMessagesIn(String thread) {
        Path path = getThreadPath(thread);
        if (Files.exists(path)) {
            try {
                List<Message> result = Files.lines(path).map(this::readLine).collect(Collectors.toList());
                Collections.reverse(result);
                return result;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void addMessage(String thread, Message message) {
        createDir();
        try {
            Files.write(getThreadPath(thread), (mapper.writeValueAsString(message) + "\n").getBytes(), APPEND, CREATE);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void clear() {
        if (Files.exists(dir)) {
            try {
                Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                });
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private Path getThreadPath(String thread) {
        return dir.resolve(thread + ".thread");
    }

    private Message readLine(String s) {
        try {
            return mapper.readValue(s, Message.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private void createDir() {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
