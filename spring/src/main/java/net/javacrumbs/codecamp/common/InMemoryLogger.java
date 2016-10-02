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

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

//@Component
public class InMemoryLogger implements ReadableLogger {
    private final Deque<Message> messages = new ConcurrentLinkedDeque<>();

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(InMemoryLogger.class);

    public InMemoryLogger() {
        logger.info("action=initializing");
    }

    @Override
    public List<Message> getMessages() {
        return new LinkedList<>(messages);
    }

    @Override
    public void addMessage(Message message) {
        messages.addFirst(message);
    }

    @Override
    public void clear() {
        messages.clear();
    }

    @Override
    public void close() {

    }
}
