package malangcute.bellytime.bellytimeCustomer.global.schedule.quartz;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

//@DisallowConcurrentExecution // 작업이 겹치지 않으면서 순서 보장
@Slf4j
public class QuartzJobExecutor extends QuartzJobBean implements InterruptableJob {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ApplicationContext applicationContext;

    private final static String JOB_NAME = "coolTimeUpdateJob";

    //멀티스레드에서 사용시 메인메모리 기준으로 설정할 것이기 때문
    private volatile boolean isJobInterrupted = false;
    private volatile Thread currThread;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addLong("currentTime", System.currentTimeMillis());
            jobLauncher.run((Job) applicationContext.getBean(JOB_NAME), jobParametersBuilder.toJobParameters()); //batch job 실행
        } catch (Exception e) {
            log.trace(e + "쿼츠 에러!!!");
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isJobInterrupted = true;
        if (currThread != null) {
            currThread.interrupt();
        }
    }
}
