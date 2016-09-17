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

import net.javacrumbs.codecamp.boot.common.FileMessageStore;
import net.javacrumbs.codecamp.boot.common.MessageStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    @Bean
    public MessageStore messageStore(@Value("${db.file}") File file) { // Type conversion
        return new FileMessageStore(file);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

