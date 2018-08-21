package com.kurra.myRetail.util

import groovy.util.logging.Slf4j
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered

/**
 * User: Niranjan.kurra - Date: 8/21/18 12:01 PM
 */
@Slf4j
class OrderedCassandraTestExecutionListener extends CassandraUnitDependencyInjectionTestExecutionListener {

    @Override
    int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE
    }

    @Override
    protected void cleanServer() {
        try {
            super.cleanServer()
        }
        catch (Exception ex) {
            log.warn("Failure during server cleanup", ex)
        }
    }

}
