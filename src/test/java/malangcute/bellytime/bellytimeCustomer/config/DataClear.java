package malangcute.bellytime.bellytimeCustomer.config;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.google.common.base.CaseFormat;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataClear implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tablenames;

    @Override
    public void afterPropertiesSet() throws Exception {
        tablenames = entityManager.getMetamodel().getEntities().stream()
                .filter(entityType -> entityType.getJavaType().getAnnotation(Entity.class) != null)
                .map(entityType -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void tableClear() {
        entityManager.flush();
        entityManager.clear();

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tablenames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
