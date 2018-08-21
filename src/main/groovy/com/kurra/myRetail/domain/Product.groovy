package com.kurra.myRetail.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:02 PM
 */
@Table('products')
class Product {
    @PrimaryKey
    long id
    String name
    BigDecimal price
    @Column('currency_code')
    String currencyCode
}
