package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import batch.BatchJobExecutor;
import batch.CoolTimeBatchConfig;

@Configuration
public class JobUtils {

	private final static String THIRTY_SEC = "0/30 * * * * ?"; //30초

	private final static String ONE_DAY = "0 0 0 * * ?"; //매일 자정

	private final static String JOB_NAME = "coolTimeUpdateJob";

	@Bean
	public JobDetail jobDetail() {
		JobDetail jobDetail = JobBuilder.newJob(BatchJobExecutor.class)
			.withIdentity(JOB_NAME)
			.build();
		jobDetail.getJobDataMap().put("jobName", JOB_NAME);

		return jobDetail;
	}

	@Bean
	public Trigger jobTrigger() {
		return TriggerBuilder.newTrigger()
			.withIdentity(new JobKey(JOB_NAME).getName())
			.withSchedule(CronScheduleBuilder.cronSchedule(ONE_DAY))
			.build();
	}
}
