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

import net.javacrumbs.codecamp.boot.common.CsvFileLogger;
import net.javacrumbs.codecamp.boot.common.Message;
import net.javacrumbs.codecamp.boot.common.ReadableLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.io.File;

import static net.javacrumbs.codecamp.boot.common.Message.Severity.INFO;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class Application {

    @Bean
    public ReadableLogger logger(@Value("${db.file}") File file) {
        CsvFileLogger logger = new CsvFileLogger(file);
        logger.addMessage(new Message(INFO, "Test message"));
        return logger;
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

