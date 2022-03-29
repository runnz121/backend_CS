package schedule.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class QuartzConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PlatformTransactionManager transactionManager;

    //쿼츠의 스케쥴러를 관리함 -> 리스너와 쿼츠 관리 설정 여기서함

    //JobFactory를 의존 관계 주입 통로로 사용할 수 있다.
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean () throws SchedulingException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        QuartzJobFactory quartzJobFactory = new QuartzJobFactory();

        quartzJobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setSchedulerName("coolTime");
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        schedulerFactoryBean.setTransactionManager(transactionManager);
       // schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }


    // @Bean(initMethod = "init")
    // public QuartzStarter quartzStarter() {
    //     return new QuartzStarter();
    // }

}
