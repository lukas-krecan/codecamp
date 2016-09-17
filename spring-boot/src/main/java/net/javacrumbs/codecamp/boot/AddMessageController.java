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
package net.javacrumbs.codecamp.boot;

import net.javacrumbs.codecamp.boot.common.Message;
import net.javacrumbs.codecamp.boot.common.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AddMessageController {
    private final MessageStore messageStore;

    @Autowired
    public AddMessageController(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @RequestMapping(path = "/threads/{thread}")
    public List<Message> getMessages(@PathVariable String thread) {
        return messageStore.getMessagesIn(thread);
    }

}
