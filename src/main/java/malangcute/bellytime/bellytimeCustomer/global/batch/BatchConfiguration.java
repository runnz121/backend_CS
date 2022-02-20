package malangcute.bellytime.bellytimeCustomer.global.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;


    //job은 여러개의 step을 갖고 있다.
    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("check")
                .start(check1(null))
                .build();
    }


    @Bean
    @JobScope
    public Step check1(@Value("#{jobParameters[request]}") String request) {
        return stepBuilderFactory.get("check") //  이 이름으로 job 생성
                .tasklet((contribution, chunkContext) -> { // step 안에서 수행할 기능 명시
                    System.out.println(">>>>>check1");
                    log.info("info check!");
                    log.info("info check! = {}", request);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
