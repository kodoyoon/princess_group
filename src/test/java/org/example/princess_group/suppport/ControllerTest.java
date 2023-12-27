package org.example.princess_group.suppport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.princess_group.domain.auth.controller.AuthController;
import org.example.princess_group.domain.board.controller.BoardController;
import org.example.princess_group.domain.card.controller.CardController;
import org.example.princess_group.domain.comment.controller.CommentController;
import org.example.princess_group.domain.list.controller.ListApiController;
import org.example.princess_group.domain.user.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = {
        CardController.class,
        AuthController.class,
        BoardController.class,
        CommentController.class,
        ListApiController.class,
        UserController.class
    }
)
@AutoConfigureMockMvc
public class ControllerTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;
}
