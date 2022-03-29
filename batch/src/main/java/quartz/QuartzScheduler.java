package quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzScheduler {

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext){

		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

		QuartzJobFactory jobFactory = new QuartzJobFactory();

		jobFactory.setApplicationContext(applicationContext);
		schedulerFactoryBean.setJobFactory(jobFactory);
		schedulerFactoryBean.setApplicationContext(applicationContext);
		schedulerFactoryBean.setSchedulerName("coolTime");
		//schedulerFactoryBean.setAutoStartup(true);
		return schedulerFactoryBean;
	}
}
