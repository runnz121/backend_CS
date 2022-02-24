package malangcute.bellytime.bellytimeCustomer.global.config;

import lombok.RequiredArgsConstructor;
import malangcute.bellytime.bellytimeCustomer.global.domain.CacheElements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRedisRepositories
//@AllArgsConstructor -> value값 인식 못함 -> 생성시 비어있는 원시 변수값으로 초기화 하려다 보니 에러남
@RequiredArgsConstructor // 이것을 사용하게되면 있는 값들 바탕으로 생성자 생성 후
@EnableCaching // 캐시 어노테이션 사용
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}") // 빈 등록시 적용됨
    private int port;




    //redis 연결 및, lettuce 사용
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    //redis의 다섯가지 유형의 데이터 제공
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    // 캐쉬 적용
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(CacheElements.EXPIRE_DURATION_DAY))
                .computePrefixWith(CacheKeyPrefix.simple())  //name::key 처럼 key앞에 '::'를 삽입(redis-cli에서 get "name::key" 로 조회.)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        //캐시별 디폴트 유효기간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put(CacheElements.RECENT_SEARCH, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(CacheElements.EXPIRE_DURATION_HOUR)));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();
    }
}
