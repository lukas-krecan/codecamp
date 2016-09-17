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

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class FileMessageStore implements MessageStore {
    private final Logger logger = LoggerFactory.getLogger(FileMessageStore.class);

    private final DB db;
    private final ConcurrentMap<String, List<Message>> messages;

    private final byte[] lock = new byte[0];

    public FileMessageStore() {
        this(new File("messages.db"));
    }

    public FileMessageStore(File file) {
        logger.info("action=initializing file={}", file);
        db = DBMaker.fileDB(file).make();
        messages = (ConcurrentMap<String, List<Message>>) db.hashMap("map").createOrOpen();
    }

    @Override
    public List<Message> getMessagesIn(String thread) {
        synchronized (lock) {
            return Collections.unmodifiableList(messages.getOrDefault(thread, Collections.emptyList()));
        }
    }

    @Override
    public void addMessage(String threadName, Message message) {
        synchronized (lock) {
            List<Message> thread = getOrCreateThread(threadName);
            thread.add(0, message);
            messages.put(threadName, thread);
            db.commit();
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            messages.clear();
        }
    }

    @Override
    @PreDestroy
    public void close() {
        logger.info("action=closeDb");
        synchronized (lock) {
            db.close();
        }
    }

    private List<Message> getOrCreateThread(String thread) {
        return messages.computeIfAbsent(thread, t -> Collections.synchronizedList(new LinkedList<>()));
    }
}
