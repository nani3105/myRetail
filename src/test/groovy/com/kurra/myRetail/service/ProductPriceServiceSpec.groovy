package com.kurra.myRetail.service

import com.kurra.myRetail.domain.Product
import com.kurra.myRetail.domain.RedSkyListPrice
import com.kurra.myRetail.domain.RedSkyPrice
import com.kurra.myRetail.domain.RedSkyProduct
import com.kurra.myRetail.domain.RedSkyProductResponse
import com.kurra.myRetail.repository.ProductRepository
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import spock.lang.Specification

/**
 * User: Niranjan.kurra - Date: 8/22/18 10:01 AM
 */
class ProductPriceServiceSpec extends Specification {

    RedSkyService redSkyService = Mock(RedSkyService)
    ProductRepository productRepository = Mock(ProductRepository)

    ProductPriceService service = new ProductPriceService(
            redSkyService: redSkyService,
            productRepository: productRepository
    )
    Long productId = 100L

    def 'should get the price if available in database'() {
        when:
        BigDecimal price = service.getPrice(productId)
        then:
        1 * productRepository.findById(productId) >> Optional.of(new Product(id: productId, price: 20.40))
        0 * _

        then:
        price == 20.40
    }

    def 'should get the price from redsky service if not available in database'() {
        given:
        RedSkyProductResponse response = new RedSkyProductResponse(product: new RedSkyProduct(price: new RedSkyPrice(listPrice: new RedSkyListPrice(price: 30.56))))
        when:
        BigDecimal price = service.getPrice(productId)
        then:
        1 * productRepository.findById(productId) >> Optional.empty()
        1 * redSkyService.getPrice(productId) >> response
        1 * productRepository.save(_)
        0 * _

        then:
        price == 30.56
    }

    def 'should update the price in database'() {
        given:
        RedSkyProductResponse response = new RedSkyProductResponse(product: new RedSkyProduct(price: new RedSkyPrice(listPrice: new RedSkyListPrice(price: 30.56))))
        when:
        service.updatePrice(productId, 30.44)
        then:
        1 * productRepository.findById(productId) >> Optional.of(new Product(id: productId, price: 20.40))
        1 * productRepository.save(_)
        0 * _
    }

    def 'should throw when updating the price in database if not found'() {
        given:
        RedSkyProductResponse response = new RedSkyProductResponse(product: new RedSkyProduct(price: new RedSkyPrice(listPrice: new RedSkyListPrice(price: 30.56))))
        when:
        service.updatePrice(productId, 30.44)
        then:
        1 * productRepository.findById(productId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }
}
