package com.kurra.myRetail.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:03 PM
 */
class CurrentPrice {
    BigDecimal value
    @JsonProperty('current_code')
    String currenyCode
}
