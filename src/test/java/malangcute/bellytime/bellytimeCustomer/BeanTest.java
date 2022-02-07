package malangcute.bellytime.bellytimeCustomer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.module.Configuration;


@SpringBootTest
public class BeanTest {

    private static ApplicationContext ac;

    @Test
    @DisplayName("print all beans")
    void findAllBean() {
        // search all bean which registrated in spring container
//
        String[] allBeanNames = ac.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
}
