package malangcute.bellytime.bellytimeCustomer.global.config;

import malangcute.bellytime.bellytimeCustomer.food.repository.elastic.FoodSearchRepository;
import malangcute.bellytime.bellytimeCustomer.shop.repository.elastic.ShopSearchRepository;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {ShopSearchRepository.class, FoodSearchRepository.class}) //빈 중복 문제로 엘라스틱 서치 리포만 스캔하게끔 설정
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {


    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;


    /**
     * 1. 로컬환경에서 도커로 elastic search 깐다 ->9200 포트 : http, 클라이언트, 9300 : 노드간 통신
     * 2. 확인후 ec2에 설치
     */


    // 엘라스틱 서치 엔드포인트 연결
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }


}


//    @Bean
//    public RestHighLevelClient client() {
//        RestClient restClient = RestClient.builder(
//                new HttpHost("search-bellytime-wcyh7pmvbvavdmjhfi5tklwqay.ap-northeast-2.es.amazonaws.com", 80, "http")).build();
//        return RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClient);
//        //return new RestHighLevelClient(RestClient.builder(HttpHost.create("https://search-bellytime-wcyh7pmvbvavdmjhfi5tklwqay.ap-northeast-2.es.amazonaws.com")));
//    }

