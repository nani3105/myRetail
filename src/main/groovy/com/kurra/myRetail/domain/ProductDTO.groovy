package com.kurra.myRetail.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.kurra.myRetail.domain.CurrentPrice

/**
 * User: Niranjan.kurra - Date: 8/20/18 11:00 PM
 */
class ProductDTO {
    Long id
    String name
    @JsonProperty('current_price')
    CurrentPrice currentPrice
}
