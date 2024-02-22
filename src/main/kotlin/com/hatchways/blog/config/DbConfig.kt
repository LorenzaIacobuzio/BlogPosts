package com.hatchways.blog.config

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@EnableJpaAuditing
class DbConfig {

    @Value("\${blog.db.url}")
    private lateinit var dbUrl: String

    @Bean
    fun dataSource(): DataSource {
      return DriverManagerDataSource().apply {
        setDriverClassName("org.sqlite.JDBC")
        url = dbUrl
      }
    }

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}
