package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.controllers.Ejercicio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	Ejercicio ejercicio;

	@Test
	void contextLoads() {
	}

	@Test
	void greetTest(){
		String resultado=ejercicio.greet();
		assertEquals("Vienvenido al servidor backend", resultado);
	}



}
