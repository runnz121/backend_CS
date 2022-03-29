// package malangcute.bellytime.bellytimeCustomer.global.schedule.quartz;
//
// import lombok.AllArgsConstructor;
// import org.quartz.*;
// import org.springframework.scheduling.quartz.SchedulerFactoryBean;
// import org.springframework.stereotype.Component;
//
// import javax.annotation.PostConstruct;
//
// @Component
// @AllArgsConstructor
// public class QuartzService {
//
//     private final SchedulerFactoryBean schedulerFactoryBean;
//
//     private final static String THIRTY_SEC = "0/30 * * * * ?"; //30초
//
//     private final static String ONE_DAY = "0 0 0 * * ?"; //매일 자정
//
//     private final static String JOB_NAME = "coolTimeUpdateJob";
//
//
//     Scheduler scheduler = null;
//
//     //스케쥴러 시작
//     @PostConstruct
//     public void init() {
//         scheduler = schedulerFactoryBean.getScheduler();
//     }
//
//     //잡 이름을 받아 잡 디테일 생성
//     private JobDetail createJobDetail(String jobname) {
//         JobDetail jobDetail = JobBuilder.newJob(QuartzJobExecutor.class)
//                 .withIdentity(jobname)
//                 .build();
//         jobDetail.getJobDataMap().put("jobName", jobname);
//         return jobDetail;
//     }
//
//     //잡 이름과 크론 조건을 받아 크론 트리거 생성
//     private CronTrigger createCronTrigger(String jobName, String cron) {
//         return TriggerBuilder.newTrigger()
//                 .withIdentity(new JobKey(jobName).getName())
//                 .withSchedule(CronScheduleBuilder.cronSchedule(cron))
//                 .build();
//     }
//
//     //스케쥴러에 스케쥴 등록
//     public void register() throws SchedulerException {
//         JobDetail jobDetail = this.createJobDetail(JOB_NAME);
//         CronTrigger cronTrigger = this.createCronTrigger(JOB_NAME, ONE_DAY);
//         scheduler.scheduleJob(jobDetail,cronTrigger);
//     }
//
//     //스케쥴 시작
//    public void start() throws SchedulerException {
//         if(scheduler != null && !scheduler.isStarted()) {
//             scheduler.start();
//         }
//     }
//
//     /** 스케쥴러 종료 */
//     public void shutdown() throws SchedulerException, InterruptedException {
//         if(scheduler != null && !scheduler.isShutdown()) {
//             scheduler.shutdown();
//         }
//     }
//
//     /** 스케쥴러 클리어 */
//     public void clear() throws SchedulerException {
//         scheduler.clear();
//     }
// }
