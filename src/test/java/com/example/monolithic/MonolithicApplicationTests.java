
package com.example.monolithic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class MonolithicApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
	}

	@Test
	public void greetingsTestWithoutServer() throws Exception {
		mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello World")));
	}

	@Test
	public void bonjour() throws Exception {
		mockMvc.perform(get("/bonjour")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void notPresent() throws Exception {
		mockMvc.perform(get("/dssdsdkah")).andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void confirmUriResponse() throws Exception {
		mockMvc.perform(get("/CheesyFries"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Hello"));
	}

	@Test
	public void getPostTest() throws Exception {
		mockMvc.perform(get("/getPosts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("HelloWorld")));
	}

	@Test
	public void testPathVariables() throws Exception {
		mockMvc.perform(get("/uriParams")
				.param("job", "Programmer")
				.param("name", "Harrison"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name").value("Harrison"))
				.andExpect(jsonPath("$.job").value("Programmer"));
	}

	@Test
	public void testListPosition() throws Exception {
		mockMvc.perform(get("/getPosts"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[1].postInfo").value("HelloWorld"))
				.andExpect(jsonPath("$[2].postInfo").value("HelloWorld"));;
	}
}
