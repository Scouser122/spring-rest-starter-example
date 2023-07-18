package org.scouser.reststarter.config

/**
 * Настройки таймаута для отправки запроса к другому сервису
 */
class RestTemplateTimeout {
    /**
     * Таймаут на установку соединения
     */
    var connection: Long = 15
    /**
     * Таймаут на получение данных
     */
    var read: Long = 15
    /**
     * Таймаут на ожидание в пуле соединений перед отправкой запроса
     */
    var connectionManager: Long = 10
}