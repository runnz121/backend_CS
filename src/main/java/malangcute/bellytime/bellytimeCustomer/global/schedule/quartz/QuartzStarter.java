package malangcute.bellytime.bellytimeCustomer.global.schedule.quartz;

import malangcute.bellytime.bellytimeCustomer.global.schedule.quartz.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

public class QuartzStarter {

    @Autowired
    private QuartzService quartzService;

    public void init() throws SchedulerException {
        quartzService.clear();
        quartzService.register();
        quartzService.start();
    }

}
