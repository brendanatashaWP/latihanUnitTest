import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;

import java.util.List;

public class TodoRepositoryTest {
    @Mock
    TodoRepository todoRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testGetAll() throws Exception{
        List<Todo> result = todoRepository.getAll();
        Assert.assertThat(result, org.hamcrest.Matchers.notNullValue());
    }

    @Test
    public void saveToDoTest(){
        boolean result = todoRepository.store(new Todo("todo1", TodoPriority.HIGH));
        Assert.assertThat(result, org.hamcrest.Matchers.equalTo(false));
    }
}
