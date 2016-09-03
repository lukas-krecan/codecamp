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
package net.javacrumbs.codecamp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private static final Map<String, List<Message>> messages = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String thread = req.getParameter("thread");
        if (thread == null) {
            writer.write("You have to specify thread parameter");
            return;
        }
        List<Message> messages = getMessages(thread);

        // head
        writer.append("<!DOCTYPE html>\n<html><head><title>").append(thread).append("</title></head><body>");
        // form
        String name = req.getParameter("name");
        writer.append("<form method=\"POST\">")
            .append("Message: <input type=\"text\" name=\"text\"><br>")
            .append("Name: <input type=\"text\" name=\"name\" value=\"").append(name != null ? name : "").append("\"><br>")
            .append("<input type=\"hidden\" name=\"thread\" value=\"").append(thread).append("\">")
            .append("<input type=\"submit\" value=\"Submit\">")
            .append("</form>");

        //messages
        messages.forEach(message -> writer.append("<p>")
            .append(message.getName()).append(": ")
            .append(message.getText())
            .append(" (").append(message.getTime().toString()).append(")")
            .append("</p>"));

        writer.append("</body></html>");
    }

    private List<Message> getMessages(String thread) {
        return ChatServlet.messages.computeIfAbsent(thread, s -> Collections.synchronizedList(new LinkedList<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String text = req.getParameter("text");
        String thread = req.getParameter("thread");
        List<Message> messages = getMessages(thread);
        messages.add(0, new Message(text, name));
        doGet(req, resp);
    }

    private static class Message {
        private final String text;
        private final String name;
        private final LocalDateTime time;

        private Message(String text, String name) {
            this.text = text;
            this.name = name;
            this.time = LocalDateTime.now();
        }

        public String getText() {
            return text;
        }

        public String getName() {
            return name;
        }

        public LocalDateTime getTime() {
            return time;
        }
    }
}
