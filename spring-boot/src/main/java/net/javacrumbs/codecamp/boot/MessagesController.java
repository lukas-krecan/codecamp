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
import net.javacrumbs.codecamp.boot.common.ReadableLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/messages")
public class MessagesController {
    public static final String VIEW_NAME = "showMessages";
    private final ReadableLogger logger;

    @Autowired
    public MessagesController(ReadableLogger logger) {
        this.logger = logger;
    }

    @GetMapping
    public ModelAndView showMessages() {
        Map<String, Object> model = new HashMap<>();
        model.put("message", new Message());
        return new ModelAndView(VIEW_NAME, model);
    }

    @PostMapping
    public String addMessage(@Valid Message message, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_NAME;
        }

        logger.addMessage(message);
        return "redirect:/messages";
    }

    @ModelAttribute("messages")
    public List<Message> getMessages() {
        return logger.getMessages();
    }
}
