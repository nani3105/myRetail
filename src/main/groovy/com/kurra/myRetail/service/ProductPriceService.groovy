package com.kurra.myRetail.service

import com.kurra.myRetail.domain.Product
import com.kurra.myRetail.domain.RedSkyProductResponse
import com.kurra.myRetail.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:47 PM
 */
@Service
class ProductPriceService {

    @Autowired
    RedSkyService redSkyService

    @Autowired
    ProductRepository productRepository

    /**
     * Look in the database for the product.
     * Since this is sample app, we dont have data, so instead of pre populating some test data,
     * we can call the redsky endpoint which has the price info and save it in cassandra.
     * @param id
     * @return
     */
    BigDecimal getPrice(Long id) {
        Optional<Product> optional = productRepository.findById(id)
        if (optional.isPresent()) {
            return optional.get().price
        } else {
            RedSkyProductResponse response = redSkyService.getPrice(id)
            BigDecimal price = response?.product?.price?.listPrice?.price
            productRepository.save(new Product(id: id, name: response?.product?.item?.description?.title,
                                                price: price, currencyCode: 'USD'))
            price
        }
    }

    @Async
    CompletableFuture<BigDecimal> getPriceAsync(Long id) {
        CompletableFuture.completedFuture(getPrice(id))
    }

    /**
     * Get the product from the db and update the price.
     * @param id
     * @param price
     */
    void updatePrice(Long id, BigDecimal price) {
        Optional<Product> optional = productRepository.findById(id)
        if (optional.isPresent()) {
            def product = optional.get()
            product.price = price
            productRepository.save(product)
        } else {
            throw new ResourceNotFoundException("Product with ${id} not found")
        }
    }

}
