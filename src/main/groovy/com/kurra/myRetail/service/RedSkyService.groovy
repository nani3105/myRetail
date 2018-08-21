package com.kurra.myRetail.service

import com.kurra.myRetail.domain.RedSkyProductResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:08 PM
 */
@Service
class RedSkyService {

    @Autowired
    RestTemplate restTemplate

    @Value('${redskyProductUrl}')
    String redskyProductUrl

    // this url includes the price
    @Value('${redskyPriceUrl}')
    String redskyPriceUrl

    String getProductName(long id) {
        RedSkyProductResponse response
        response = restTemplate.getForObject(redskyProductUrl, RedSkyProductResponse, [id: id])
        response?.product?.item?.description?.title
    }

    RedSkyProductResponse getPrice(long id) {
        restTemplate.getForObject(redskyPriceUrl, RedSkyProductResponse, [id: id])
    }
}
