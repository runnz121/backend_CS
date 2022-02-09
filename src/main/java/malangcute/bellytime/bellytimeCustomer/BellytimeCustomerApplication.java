package malangcute.bellytime.bellytimeCustomer;

import malangcute.bellytime.bellytimeCustomer.food.repository.FoodRepository;
import malangcute.bellytime.bellytimeCustomer.food.repository.elastic.FoodSearchRepository;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.ShopSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter( //jpa가 2중으로 확인함으로 엘라스틱 리포지토리는 제외시킴
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {ShopSearchRepository.class, FoodSearchRepository.class}
))
public class BellytimeCustomerApplication  {



	public static void main(String[] args) {
		SpringApplication.run(BellytimeCustomerApplication.class, args);

	}


}
