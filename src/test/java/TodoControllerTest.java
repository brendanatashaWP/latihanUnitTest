import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.Introduction;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.service.TodoService;

import java.util.Collections;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Introduction.class)
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @LocalServerPort
    private int serverPort;

    private static final String name = "todo1";
    private static final TodoPriority priority = TodoPriority.HIGH;

    private static final String todo = "{\"code\":200,\"message\":null,\"value\":[{\"name\":\"Todo1\",\"priority\":\"HIGH\"}]}";

    @After
    public void tearDown(){
        verifyNoMoreInteractions(todoService);
    }

    @Test
    public void testing() throws Exception{
        when(todoService.getAll()).thenReturn(Collections.singletonList(new Todo(name, priority)));
        RestAssured.given()
                .contentType("application/json")
                .when()
                .port(serverPort)
                .get("/todos")
                .then()
                .body(Matchers.containsString("value"))
                .body(Matchers.containsString(name))
                .body(Matchers.equalTo(todo))
                .statusCode(200);

        verify(todoService).getAll();
    }

    @Test
    public void saveTodoTest(){
        when(todoService.saveTodo(name, priority)).thenReturn(true);

        RestAssured.given()
                .contentType("application/json")
                .body("{\"name\":\"Todo1\",\"priority\":\"HIGH\"}")
                .when()
                .port(serverPort)
                .post("/todos")
                .then()
                .statusCode(200);

        verify(todoService).saveTodo(name, priority);
    }

}
