package malangcute.bellytime.bellytimeCustomer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.ApplicationContext;


@SpringBootTest
@AllArgsConstructor
public class BeanTest {

    private final ApplicationContext ac;


    @Test
    @DisplayName("print all beans")
    void findAllBean() {
        // search all bean which registrated in spring container
        String[] allBeanNames = ac.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
}
