package com.niranjan.lcs;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LcsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testNoBody() throws Exception {
		mockMvc.perform(post("/lcs")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// Test incorrect POST body format
	@Test
	public void testIncorrectBodyFormat() throws Exception {
		String incorrectBody = "{\"wrongKey\": [\"comcast\", \"communicate\", \"commutation\"]}";
		mockMvc.perform(post("/lcs")
						.content(incorrectBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// Test empty set of strings
	@Test
	public void testEmptySetOfStrings() throws Exception {
		String emptySet = "{\"setOfStrings\": []}";
		mockMvc.perform(post("/lcs")
						.content(emptySet)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// Test non-unique set of strings
	@Test
	public void testDuplicateStringsInSetOfStrings() throws Exception {
		String duplicateStrings = "{\"setOfStrings\": [{\"value\": \"duplicate\"}, {\"value\": \"duplicate\"}]}";
		mockMvc.perform(post("/lcs")
						.content(duplicateStrings)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// Test valid case with single longest common substring
	@Test
	public void testValidInputSingleLCS() throws Exception {
		String validInput = "{\"setOfStrings\": [{\"value\": \"comcast\"}, {\"value\": \"comcastic\"}, {\"value\": \"broadcaster\"}]}";
		mockMvc.perform(post("/lcs")
						.content(validInput)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lcs[0].value").value("cast"));
	}

	// Test valid case with single longest common substring
	@Test
	public void testValidInputSingleLCS1() throws Exception {
		String validInput = "{\"setOfStrings\": [{\"value\": \"comcast\"}, {\"value\": \"communicate\"}, {\"value\": \"commutation\"}]}";
		mockMvc.perform(post("/lcs")
						.content(validInput)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lcs[0].value").value("com"));
	}

	// Test valid case with multiple longest common substrings
	@Test
	public void testValidInputMultipleLCS() throws Exception {
		String validInput = "{\"setOfStrings\": [{\"value\": \"testbase\"}, {\"value\": \"basetest\"}, {\"value\": \"testingbase\"}]}";
		mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
						.content(validInput)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lcs[*].value").value(containsInAnyOrder("test", "base")));
	}

	// Test valid case with no common substrings
	@Test
	public void testValidInputNoCommonSubstrings() throws Exception {
		String validInput = "{\"setOfStrings\": [{\"value\": \"hello\"}, {\"value\": \"world\"}, {\"value\": \"!\"}]}";
		mockMvc.perform(post("/lcs")
						.content(validInput)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lcs").isEmpty());
	}

}
