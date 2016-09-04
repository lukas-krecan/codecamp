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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryMessageStore implements MessageStore{
    private final Map<String, List<Message>> messages = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger(InMemoryMessageStore.class);

    public InMemoryMessageStore() {
        logger.info("action=initializing");
    }

    @Override
    public List<Message> getMessagesIn(String thread) {
        return Collections.unmodifiableList(messages.getOrDefault(thread, Collections.emptyList()));
    }

    @Override
    public void addMessage(String thread, Message message) {
        getOrCreateThread(thread).add(0, message);
    }

    private List<Message> getOrCreateThread(String thread) {
        return messages.computeIfAbsent(thread, t -> Collections.synchronizedList(new LinkedList<>()));
    }
}
