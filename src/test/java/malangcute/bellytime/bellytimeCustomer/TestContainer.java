// package malangcute.bellytime.bellytimeCustomer;
//
// import lombok.extern.slf4j.Slf4j;
// import org.junit.jupiter.api.Test;
// import org.springframework.stereotype.Component;
// import org.testcontainers.containers.GenericContainer;
// import org.testcontainers.containers.MySQLContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.utility.DockerImageName;
//
// import javax.annotation.PreDestroy;
//
// import static org.junit.Assert.*;
//
// @Testcontainers
// @Slf4j
// public class TestContainer {
//
//
//     //테스트 필드 모두 하나의 리포로 돌릴꺼면 static으로 선언
//     @Container
//     public GenericContainer mysql = new GenericContainer(DockerImageName.parse("mysql")).withExposedPorts(6379); // 각 테스트에 새 컨테이너로 테스트
//     // public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379); // 모든 테스트에 한 컨테이너로 테스트
//
//
//     @Test
//     public void check() {
//         log.info(mysql.getContainerName());
//         log.info(mysql.getHost());
//     }
//
//     @Test
//     public void testRedisContainer() {
//         assertEquals("localhost", mysql.getHost());
//         assertTrue(mysql.getFirstMappedPort() > 0);
//     }
//
// }
//
//
