package org.scouser.reststarter.autoconfig

import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.scouser.reststarter.beans.RequestInterceptor
import org.scouser.reststarter.config.RestTemplateProperties
import org.scouser.reststarter.handlers.RestErrorHandler
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Configuration
@ConditionalOnClass(RestTemplate::class)
@EnableConfigurationProperties(RestTemplateProperties::class)
class RestTemplateAutoConfig(
    private val properties: RestTemplateProperties
) {
    @Bean
    fun buildRestTemplate(): RestTemplate {
        //создаем менеджер пулов и настраиваем его
        val poolManager = PoolingHttpClientConnectionManager()
        poolManager.maxTotal = properties.poolSize
        poolManager.defaultMaxPerRoute = properties.poolSize
        //конфиг запросов
        val requestConfig: RequestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(properties.timeout.connectionManager, TimeUnit.SECONDS)
            .setConnectTimeout(properties.timeout.connection, TimeUnit.SECONDS)
            .setResponseTimeout(properties.timeout.read, TimeUnit.SECONDS)
            .build()
        //httpClient
        val httpClient: CloseableHttpClient = HttpClientBuilder.create()
            .setConnectionManager(poolManager)
            .setDefaultRequestConfig(requestConfig)
            .build()
        //make factory with pre-configured HttpClient
        val httpRequestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        val factory: ClientHttpRequestFactory = BufferingClientHttpRequestFactory(httpRequestFactory)
        val restTemplate = RestTemplate(factory)
        var interceptors: ArrayList<ClientHttpRequestInterceptor?> = ArrayList(restTemplate.interceptors)
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = ArrayList()
        }
        //добавляем интерцептор логирования
        interceptors.add(RequestInterceptor())
        restTemplate.interceptors = interceptors
        //обработка ошибок
        restTemplate.errorHandler = RestErrorHandler()
        return restTemplate
    }
}