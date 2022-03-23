package schedule.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

//https://homoefficio.github.io/2019/09/29/Quartz-%EC%8A%A4%EC%BC%80%EC%A4%84%EB%9F%AC-%EC%A0%81%EC%9A%A9-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EA%B0%9C%EC%84%A0-2/
//객체를 직접 얻기 위해 applicationcontextarware를 통해서 객체를 얻을 수 있다.
// 스프링부트가 제공하는 SpringBeanJobFactory를 통해 애플리케이션 구동 완료 후에 동적으로 추가하는 bean에도 의존 관계를 쉽게 주입할 수 있다.
public class QuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    //transient : serialize과정에서 제외하고 싶은 경우 -> 메모리 상태에서만 사용되어야할 경우
    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //빈 컨테이너에서 의존성주입이 가능한 객체를 얻기 위해
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    //springbeanjobfacoty를 오버라이드함 -> 쿼츠의 잡 인스턴스를 생성함
    //triggerfiredbundle -> josbstore에서 시간 데이터를 추출하여 쿼츠 스케쥴러 스레드로 전달
    //autowireBean을 통해 생성된 잡 인스턴스 객체를 빈의 의존성 주입으로 넣음
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}
