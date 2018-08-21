package com.kurra.myRetail.controller

import com.kurra.myRetail.domain.CurrentPrice
import com.kurra.myRetail.domain.ProductDTO
import com.kurra.myRetail.service.ProductPriceService
import com.kurra.myRetail.service.RedSkyService
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import spock.lang.Specification

/**
 * User: Niranjan.kurra - Date: 8/21/18 8:05 AM
 */
class ProductsControllerSpec extends Specification {

    RedSkyService redSkyService = Mock(RedSkyService)
    ProductPriceService productPriceService = Mock(ProductPriceService)
    ProductsController controller =  new ProductsController(redSkyService: redSkyService, productPriceService: productPriceService)

    Long productId = 1L

    def 'should return product response when passed an id'() {
        when:
        def dto = controller.getProduct(productId)
        then:
        1 * redSkyService.getProductName(productId) >> 'productName'
        1 * productPriceService.getPrice(productId) >> 2.0
        0 * _
        dto
        dto.name == 'productName'
        dto.currentPrice
        dto.currentPrice.value == 2.0
    }

    def 'should throw an error when the service throws exception'() {
        when:
        controller.getProduct(productId)
        then:
        1 * redSkyService.getProductName(productId) >> { throw new HttpClientErrorException(HttpStatus.NOT_FOUND) }
        thrown(HttpClientErrorException)
        0 * _
    }

    def 'should throw an expection when updating price with null value'() {
        when:
        controller.updateProductPrice(productId, new ProductDTO())
        then:
        thrown(IllegalArgumentException)
        0 * _
    }

    def 'should update price when put endpoint is called'() {
        when:
        controller.updateProductPrice(productId, new ProductDTO(currentPrice: new CurrentPrice(value: 30.00, currenyCode: 'USD')))
        then:
        1 * productPriceService.updatePrice(productId, 30.00)
        0 * _
    }
}
