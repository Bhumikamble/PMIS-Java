package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Test
	void homePageReturnsGreeting() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, Bhumiiiii!"));
	}

	@Test
	void beansActuatorEndpointIsExposed() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		mockMvc.perform(get("/actuator/beans"))
				.andExpect(status().isOk());
	}

}
