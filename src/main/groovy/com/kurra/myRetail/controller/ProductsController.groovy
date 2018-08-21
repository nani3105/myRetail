package com.kurra.myRetail.controller

import com.kurra.myRetail.domain.CurrentPrice
import com.kurra.myRetail.domain.ProductDTO
import com.kurra.myRetail.service.ProductPriceService
import com.kurra.myRetail.service.RedSkyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * User: Niranjan.kurra - Date: 8/20/18 5:59 PM
 */
@RestController
@RequestMapping('/products')
@Slf4j
class ProductsController {

    @Autowired
    RedSkyService redSkyService

    @Autowired
    ProductPriceService productPriceService

    @GetMapping('{id}')
    ProductDTO getProduct(@PathVariable('id') Long id) {
        String productName = redSkyService.getProductName(id)
        BigDecimal price = productPriceService.getPrice(id)
        new ProductDTO(id: id, name: productName, currentPrice: new CurrentPrice(value: price, currenyCode: 'USD'))
    }

    @PutMapping('{id}')
    void updateProductPrice(@PathVariable('id') Long id, @RequestBody ProductDTO request) {
        if (!request?.currentPrice?.value) {
            throw new IllegalArgumentException('Null price cannot be passed')
        }
        productPriceService.updatePrice(id, request.currentPrice.value)
    }
}
