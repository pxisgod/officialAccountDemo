

import com.px.oad.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
public class ApplicationTest {



    public void doBefore() throws Exception {

    }

    @Test
    public void tesGetUserList(){

    }

    @Test
    public void tesGetUserInfo(){

    }


}
