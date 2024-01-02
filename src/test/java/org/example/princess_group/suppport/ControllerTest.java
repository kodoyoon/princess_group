package org.example.princess_group.suppport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.princess_group.domain.auth.controller.AuthController;
import org.example.princess_group.domain.auth.service.AuthService;
import org.example.princess_group.domain.board.controller.BoardController;
import org.example.princess_group.domain.board.service.BoardService;
import org.example.princess_group.domain.card.controller.CardController;
import org.example.princess_group.domain.card.service.CardServiceImpl;
import org.example.princess_group.domain.comment.controller.CommentController;
import org.example.princess_group.domain.list.controller.ListsController;
import org.example.princess_group.domain.list.repository.ListsRepository;
import org.example.princess_group.domain.list.service.ListsService;
import org.example.princess_group.domain.user.controller.UserController;
import org.example.princess_group.domain.user.service.UserService;
import org.example.princess_group.global.config.WebSecurityConfig;
import org.example.princess_group.global.util.StatusUtil;
import org.example.princess_group.global.validation.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = {
        CardController.class,
        AuthController.class,
        BoardController.class,
        CommentController.class,
        ListsController.class,
        UserController.class,
    },
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected CardServiceImpl cardService;
    @MockBean
    protected ListsService listsService;
    @MockBean
    protected ListsRepository repository;
    @MockBean
    protected BoardService boardService;
    @MockBean
    protected StatusUtil statusUtil;
    @MockBean
    protected AuthValidator authValidator;
    @MockBean
    protected AuthService authService;
    @MockBean
    protected UserService userService;
}
