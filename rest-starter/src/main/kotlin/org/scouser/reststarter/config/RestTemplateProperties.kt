package org.scouser.reststarter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "rest-template.properties")
class RestTemplateProperties {
    var poolSize: Int = 100
    val timeout: RestTemplateTimeout = RestTemplateTimeout()
}