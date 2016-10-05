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
package net.javacrumbs.codecamp.spring5;

import net.javacrumbs.codecamp.common.Logger;
import net.javacrumbs.codecamp.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("aggregate")
public class AggregateLogger implements Logger {
    private final List<? extends Logger> wrappedLoggers;

    @Autowired
    public AggregateLogger(List<? extends Logger> wrappedLoggers) {
        this.wrappedLoggers = wrappedLoggers;
    }

    @Override
    public void addMessage(Message message) {
        wrappedLoggers.forEach(l -> l.addMessage(message));
    }

    @Override
    public void clear() {
        wrappedLoggers.forEach(Logger::clear);
    }

    @Override
    public void close() {
        wrappedLoggers.forEach(Logger::close);
    }
}
