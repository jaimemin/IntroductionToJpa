package com.tistory.jaimemin.jpa;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * create table Member (
 *        MEMBER_ID bigint not null,
 *         createdAt timestamp,
 *         createdBy varchar(255),
 *         lastModifiedAt timestamp,
 *         lastModifiedBy varchar(255),
 *         ...
 */
@MappedSuperclass
public abstract class BaseEntity {

    // 모든 DB에 공통적으로 있어야하는 칼럼들 (DBA 요청사항)
    private String createdBy;

    private LocalDateTime createdAt;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
