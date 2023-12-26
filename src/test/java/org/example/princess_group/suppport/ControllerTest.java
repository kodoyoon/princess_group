package org.example.princess_group.suppport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
public class ControllerTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;
}
