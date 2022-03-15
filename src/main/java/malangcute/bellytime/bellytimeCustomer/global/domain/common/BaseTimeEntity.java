package malangcute.bellytime.bellytimeCustomer.global.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("JST", ZoneId.SHORT_IDS));
        this.modifiedAt = LocalDateTime.now(ZoneId.of("JST", ZoneId.SHORT_IDS));
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now(ZoneId.of("JST", ZoneId.SHORT_IDS));
    }


    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
    }
}
