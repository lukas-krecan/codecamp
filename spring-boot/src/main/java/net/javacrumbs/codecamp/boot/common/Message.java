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

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Message implements Serializable {
    public enum Severity {
        DEBUG,
        INFO,
        ERROR
    }
    private final Severity severity;
    private final String message;
    private final Instant time;

    public Message(Severity severity, String message) {
        this(severity, message, Instant.now());
    }

    public Message(Severity severity, String message, Instant time) {
        this.severity = severity;
        this.message = message;
        this.time = time;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Message{" +
            "severity=" + severity +
            ", message='" + message + '\'' +
            ", time=" + time +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return severity == message1.severity &&
            Objects.equals(message, message1.message) &&
            Objects.equals(time, message1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(severity, message, time);
    }
}
