package com.techeer.checkIt.domain.book.entity;

import com.techeer.checkIt.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Reading extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "Reading_id")
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "last_page")
    private int lastPage;

    @Builder

    public Reading(String date, int lastPage) {
        this.date = date;
        this.lastPage = lastPage;
    }
}
