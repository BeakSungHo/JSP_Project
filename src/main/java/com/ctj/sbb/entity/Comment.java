package com.ctj.sbb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratedColumn;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150)
    private String comment;//

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private Answer answer;//

    @ManyToOne
    private SiteUser author;//
    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;


}
