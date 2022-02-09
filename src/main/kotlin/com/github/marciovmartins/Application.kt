package com.github.marciovmartins

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.validation.Validator

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class MyRepositoryRestConfiguration : RepositoryRestConfigurer {
    @Autowired
    private lateinit var validator: Validator

    override fun configureValidatingRepositoryEventListener(validatingListener: ValidatingRepositoryEventListener) {
        validatingListener.addValidator("beforeCreate", validator)
        validatingListener.addValidator("beforeSave", validator)
    }
}
