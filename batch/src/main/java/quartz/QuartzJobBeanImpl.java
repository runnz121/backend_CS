package quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class QuartzJobBeanImpl extends QuartzJobBean {

	private final ApplicationContext applicationContext;

	private final JobLauncher jobLauncher;

	private static final String JOB_NAME = "coolTimeUpdateJob";

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobParameters jobParameters = new JobParameters();
		try {
			jobLauncher.run((Job)applicationContext.getBean(JOB_NAME), jobParameters);
		} catch (Exception e) {
			log.trace("quartz error");
		}
	}
}
