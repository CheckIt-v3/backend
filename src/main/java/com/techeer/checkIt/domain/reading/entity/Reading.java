package com.techeer.checkIt.domain.reading.entity;

import com.techeer.checkIt.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Reading extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "Reading_id")
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "last_page")
    private int lastPage;
    private ReadingStatus status; // 책의 상태 [UNREAD,READING,READ]

    @Builder

    public Reading(Date date, int lastPage) {
        this.date = date;
        this.lastPage = lastPage;
    }
}
