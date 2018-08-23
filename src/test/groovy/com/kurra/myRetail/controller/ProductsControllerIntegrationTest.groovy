package com.kurra.myRetail.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kurra.myRetail.domain.*
import com.kurra.myRetail.repository.ProductRepository
import com.kurra.myRetail.service.RedSkyService
import com.kurra.myRetail.util.OrderedCassandraTestExecutionListener
import org.cassandraunit.spring.CassandraDataSet
import org.cassandraunit.spring.CassandraUnit
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * User: Niranjan.kurra - Date: 8/21/18 8:23 AM
 */
@SpringBootTest(["spring.data.cassandra.port=9142", "spring.data.cassandra.keyspace-name=myretail"])
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ComponentScan
@ContextConfiguration
@TestExecutionListeners([ OrderedCassandraTestExecutionListener,
    DependencyInjectionTestExecutionListener ])
@CassandraDataSet(value = [ "product.cql" ], keyspace = "myretail")
@CassandraUnit
//@Import([IntegrationTestMockingConfig])
class ProductsControllerIntegrationTest extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    RedSkyService redSkyService = Mock(RedSkyService)

    @Autowired
    ProductRepository productRepository

    @Autowired
    ObjectMapper objectMapper

    String productId = 1000L + (long) (Math.random() * (90000000L))

    @Ignore
    def 'should call the redsky service for product name, price and save it to database'() {
        given:
        RedSkyProductResponse response = new RedSkyProductResponse(product: new RedSkyProduct(
                price: new RedSkyPrice(listPrice: new RedSkyListPrice(price: 20.00))
        ))

        when:
        def results = mvc.perform(get("/products/${productId}"))
        then:
        results.andExpect(status().is2xxSuccessful())
        1 * redSkyService.getProductName(_) >> 'productName'
        1 * redSkyService.getPrice(_) >> response

        and:
        results.andExpect(jsonPath('$.name').value('productName'))
        results.andExpect(jsonPath('$.current_price.value').value(20))
    }


    def 'should update the price in cassandra'() {
        given:
        def request = new ProductDTO(id: 1L, currentPrice: new CurrentPrice(value: 200.00, currenyCode: 'USD'))

        productRepository.save(new Product(id: 1L, name: 'someProduct', price: 100.00, currencyCode: 'USD'))

        when:
        def results = mvc.perform(put("/products/1").contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
        then:
        results.andExpect(status().is2xxSuccessful())
        productRepository.findById(1L).get().price == 200
    }
}
