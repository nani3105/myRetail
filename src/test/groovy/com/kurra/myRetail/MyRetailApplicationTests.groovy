package com.kurra.myRetail

import com.kurra.myRetail.util.OrderedCassandraTestExecutionListener
import org.cassandraunit.spring.CassandraDataSet
import org.cassandraunit.spring.CassandraUnit
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

@RunWith(SpringRunner)
@SpringBootTest(["spring.data.cassandra.port=9142", "spring.data.cassandra.keyspace-name=myretail"])
@ContextConfiguration
@TestExecutionListeners([ OrderedCassandraTestExecutionListener,
		DependencyInjectionTestExecutionListener ])
@CassandraDataSet(value = [ "product.cql" ], keyspace = "myretail")
@CassandraUnit
class MyRetailApplicationTests {

	@Test
	void contextLoads() {
	}

}
