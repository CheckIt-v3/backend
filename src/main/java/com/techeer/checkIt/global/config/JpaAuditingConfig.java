package com.techeer.checkIt.global.config;

import com.techeer.checkIt.domain.book.repository.BookJpaRepository;
import com.techeer.checkIt.domain.book.repository.BookSearchRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.techeer.checkIt.domain",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BookSearchRepository.class))
public class JpaAuditingConfig {}
