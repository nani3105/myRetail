package com.kurra.myRetail.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:28 PM
 */
class RedskyItem {
    @JsonProperty('product_description')
    ProductDescription description
}
