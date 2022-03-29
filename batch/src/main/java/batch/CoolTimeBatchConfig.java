package batch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.cooltime.domain.CoolTime;
import malangcute.bellytime.bellytimeCustomer.cooltime.repository.CoolTimeRepository;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
public class CoolTimeBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final CoolTimeRepository coolTimeRepository;

    private static final String JOB_NAME = "coolTimeUpdateJob";

    private static final String STEP_NAME = "coolTimeUpdateStep";




    //쿨타임 업데이트
    @Bean
   // public Job coolTimeUpdateJob(JobBuilderFactory jobBuilderFactory, Step coolTimeStep) {
    public Job coolTimeUpdateJob() {
        log.info("************** CoolTimeUpdateJob ***************");
        return jobBuilderFactory.get(JOB_NAME) //해당 job의 이름을 지정
              //  .preventRestart()
               // .start(coolTimeStep)
                .start(coolTimeStep())
                .build();
    }

    @Bean
    @JobScope
    //public Step coolTimeStep(StepBuilderFactory stepBuilderFactory) {
    public Step coolTimeStep() {
        log.info("************** CoolTimeUpdateJob ***************");
        return stepBuilderFactory.get(STEP_NAME)//해당 step의 이름을 지정
                .<CoolTime, CoolTime> chunk(10)
                .reader(coolTimeListItemReader())
                .processor(this.coolTimeItemProcessor())
                .writer(this.coolTimeItemWriter())
                .build();
    }

    // eat이 false인 데이터 모두 갖고오기
    @Bean
    @StepScope
    public ListItemReader<CoolTime> coolTimeListItemReader() {
        log.info("************** reader ***************");
        List<CoolTime> updateCoolTime = coolTimeRepository.findByEat(false);
        List<CoolTime> completeCoolTime = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        for (CoolTime coolTime : updateCoolTime) {

            if (coolTime.getEndDate().isBefore(today)) {

                completeCoolTime.add(coolTime);
            }
        }
        return new ListItemReader<>(completeCoolTime);
    }

    // eat == false, enddate 가 오늘보다 이전일 경우 1일 추가
    @Bean
    @StepScope
    public ItemProcessor<CoolTime, CoolTime> coolTimeItemProcessor() {
        return new ItemProcessor<CoolTime, CoolTime>() {
            @Override
            public CoolTime process(CoolTime item) throws Exception {
                log.info("************** processor ***************");

                return item.endDateUpdate(item.getEndDate());
            }
        };
    }

    //db 업데이트
    @Bean
    @StepScope
    public ItemWriter<CoolTime> coolTimeItemWriter() {
        log.info("************** writer ***************");
        return((List<? extends CoolTime> coolTimeList) ->
                coolTimeRepository.saveAll(coolTimeList));
    }
}
