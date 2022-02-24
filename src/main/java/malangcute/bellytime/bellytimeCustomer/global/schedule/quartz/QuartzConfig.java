package malangcute.bellytime.bellytimeCustomer.global.schedule.quartz;

import malangcute.bellytime.bellytimeCustomer.global.schedule.quartz.QuartzJobFactory;
import malangcute.bellytime.bellytimeCustomer.global.schedule.quartz.QuartzStarter;
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


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean () throws SchedulingException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        QuartzJobFactory quartzJobFactory = new QuartzJobFactory();

        quartzJobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setSchedulerName("coolTime");
        schedulerFactoryBean.setJobFactory(quartzJobFactory);
        schedulerFactoryBean.setTransactionManager(transactionManager);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }


    @Bean(initMethod = "init")
    public QuartzStarter quartzStarter() {
        return new QuartzStarter();
    }

}
