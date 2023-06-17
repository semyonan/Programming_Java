package com.lab.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationTests.class)
class ApplicationTests {
	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
