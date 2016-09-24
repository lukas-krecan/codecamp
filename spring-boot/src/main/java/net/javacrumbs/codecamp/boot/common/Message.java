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

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Message implements Serializable {


    public enum Severity {
        DEBUG,
        INFO,
        ERROR
    }

    private Severity severity;

    @NotEmpty
    private String text;
    private ZonedDateTime time;

    public Message() {
        this(Severity.INFO, "");
    }

    public Message(Severity severity, String text) {
        this(severity, text, ZonedDateTime.now());
    }

    public Message(Severity severity, String text, ZonedDateTime time) {
        this.severity = severity;
        this.text = text;
        this.time = time;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
            "severity=" + severity +
            ", message='" + text + '\'' +
            ", time=" + time +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return severity == message1.severity &&
            Objects.equals(text, message1.text) &&
            Objects.equals(time, message1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(severity, text, time);
    }
}
