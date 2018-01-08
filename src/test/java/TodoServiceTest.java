import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceTest {

    //instantiate TodoService
    private TodoService todoService; //kelas yg mau ditest jangan dikasih @mock

    @Mock //untuk menjadikan kelas TodoRepository kelas boongan.
    private  TodoRepository todoRepository;


    @Before
    public void setUp(){
//        this.todoRepository = new TodoRepository();
        this.todoService = new TodoService(this.todoRepository);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown(){
        Mockito.verifyNoMoreInteractions(todoRepository);
    }

    @Test
    public  void getAllTest() throws Exception {

        //given
        // todo repo must return non empty list when getAll is called
        ArrayList<Todo> todos = new ArrayList<Todo>();
        todos.add(new Todo("todo1", TodoPriority.MEDIUM));
        BDDMockito.given(todoRepository.getAll()).willReturn(todos);


        //when
        //call getAll
        List<Todo> todoList = todoService.getAll();

        //then
        //assert that todo list is not null
        Assert.assertThat(todoList, Matchers.notNullValue());
        //assert that todo list is null
        Assert.assertThat(todoList.isEmpty(), Matchers.equalTo(false));

        //verify
        BDDMockito.then(todoRepository).should().getAll();

        //Anything missing?
        Mockito.verifyNoMoreInteractions(todoRepository);
    }

    @Test
    public void saveToDoTest(){
        Todo todo = new Todo("halo", TodoPriority.HIGH);
        BDDMockito.given(todoRepository.store(todo)).willReturn(true);

        boolean result = this.todoService.saveTodo(todo.getName(), todo.getPriority());

        Assert.assertThat(result, Matchers.equalTo(true));
        BDDMockito.then(todoRepository).should().store(todo);

    }

}
