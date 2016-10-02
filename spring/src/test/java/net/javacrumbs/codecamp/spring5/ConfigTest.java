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
import net.javacrumbs.codecamp.common.ReadableLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static net.javacrumbs.codecamp.common.Message.Severity.DEBUG;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoggerConfiguration.class)
public class ConfigTest {

    @Autowired
    @Qualifier("aggregate")
    private Logger aggregateLogger;

    @Autowired
    private List<ReadableLogger> loggers;

    @Autowired
    private Map<String, Logger> loggerMap;

    @Test
    public void shouldWriteToAllLoggers() {
        Message message1 = new Message(DEBUG, "short,x");
        Message message2 = new Message(DEBUG, "long message");

        aggregateLogger.addMessage(message1);
        aggregateLogger.addMessage(message2);

        assertThat(loggers).hasSize(2);
        loggers.forEach( l-> {
            assertThat(l.getMessages()).containsExactly(message2, message1);
        });

        assertThat(loggerMap).hasSize(3);
    }

    @Before
    public void clean() {
        aggregateLogger.clear();
    }
}
