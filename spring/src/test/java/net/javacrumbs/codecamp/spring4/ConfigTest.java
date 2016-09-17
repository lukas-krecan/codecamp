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
package net.javacrumbs.codecamp.spring4;


import net.javacrumbs.codecamp.common.Logger;
import net.javacrumbs.codecamp.common.Message;
import net.javacrumbs.codecamp.service.LogStatistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static net.javacrumbs.codecamp.common.Message.Severity.DEBUG;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoggerConfiguration.class)
public class ConfigTest {

    @Autowired
    private LogStatistics logStatistics;

    @Autowired
    private Logger logger;

    @Test
    public void shouldReturnLongest() {
        Message message1 = new Message(DEBUG, "short,x");
        Message message2 = new Message(DEBUG, "long message");

        logger.addMessage(message1);
        logger.addMessage(message2);

        assertThat(logStatistics.findLongestMessage()).contains(message2);
    }

    @Before
    public void clean() {
        logger.clear();
    }
}
