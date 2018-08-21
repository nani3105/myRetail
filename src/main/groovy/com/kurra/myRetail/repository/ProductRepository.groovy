package com.kurra.myRetail.repository

import com.kurra.myRetail.domain.Product
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * User: Niranjan.kurra - Date: 8/20/18 6:42 PM
 */
interface ProductRepository extends CrudRepository<Product, Long> {
}
