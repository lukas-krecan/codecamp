package net.javacrumbs.codecamp.boot;


import net.javacrumbs.codecamp.boot.common.Message;
import net.javacrumbs.codecamp.boot.common.ReadableLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MessagesController.class)
public class MessagesControllerTest {

    public static final String TEST_MESSAGE = "Test message";

    @MockBean
    private ReadableLogger logger;

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldRenderMessages() throws Exception {
        when(logger.getMessages()).thenReturn(singletonList(new Message(Message.Severity.ERROR, TEST_MESSAGE)));


        mvc.perform(get("/messages"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(TEST_MESSAGE)));
    }

    @Test
    public void shouldAddMessage() throws Exception {
        mvc.perform(post("/messages")
            .param("severity", "INFO")
            .param("text", TEST_MESSAGE)
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string(HttpHeaders.LOCATION, "/messages"));

        verify(logger).addMessage(refEq(new Message(Message.Severity.INFO, TEST_MESSAGE), "time"));
    }

    @Test
    public void shouldRefuseBlankMessage() throws Exception {
        mvc.perform(post("/messages")
            .param("severity", "INFO")
        )
            .andExpect(status().isOk());

        verify(logger, never()).addMessage(anyObject());
    }

}