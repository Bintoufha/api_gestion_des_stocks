package com.bintoufha.gestionStocks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
//@Builder  // au lieu de @Builder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @UuidGenerator
    @GeneratedValue(generator = "uuid2")
    @Column(name = "uuid", columnDefinition = "UUID")//updatable = false, nullable = false
    private UUID uuid;

    @CreatedDate
    @Column(name = "CreateDate", nullable = false,updatable = false)
    @JsonIgnore
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "UpdateDate")
    @JsonIgnore
    private Date lastUpdateData;

}
