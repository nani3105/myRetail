package com.kurra.myRetail.util

import com.kurra.myRetail.domain.RedskyItem
import com.kurra.myRetail.service.RedSkyService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import spock.mock.DetachedMockFactory

/**
 * User: Niranjan.kurra - Date: 8/21/18 11:32 AM
 */
@TestConfiguration
class IntegrationTestMockingConfig {
    private DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    RedSkyService redSkyService() {
        factory.Mock(RedSkyService)
    }
}