package br.univille.apidacs2022;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonpCharacterEscapes;

import br.univille.apidacs2022.api.PlanoDeSaudeControllerAPI;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTestsPlanoDeSaude {
    
    @Autowired
	private PlanoDeSaudeControllerAPI planoControllerAPI;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(planoControllerAPI).isNotNull();
	}

    @Test
	void planoControllerAPIPOSTGETTest() throws Exception {
		String jwtToken = getToken();

		MvcResult result = mockMvc.perform(post("/api/v1/planodesaude")
				.content("{\"nome\":\"Premium Norte Catarinense\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/planodesaude/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Premium Norte Catarinense")));
	}


	@Test
    void planoControllerAPIPOSTDELTest() throws Exception {
      String jwtToken = getToken();

      MvcResult result = mockMvc.perform(post("/api/v1/planodesaude")
				.content("{\"nome\":\"Premium Norte Catarinense\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

      String resultStr = result.getResponse().getContentAsString();
      JSONObject objJson = new JSONObject(resultStr);

      mockMvc.perform(delete("/api/v1/planodesaude/" + objJson.getString("id"))
      .content("{\"nome\":\"Premium Norte Catarinense\"}")
      .header("Authorization", "Bearer " + jwtToken)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

    }

	@Test
    void planoControllerAPIPOSTPUTTest() throws Exception {
      String jwtToken = getToken();

      MvcResult result = mockMvc.perform(post("/api/v1/planodesaude")
				.content("{\"nome\":\"Premium Norte Catarinense\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

      String resultStr = result.getResponse().getContentAsString();
      JSONObject objJson = new JSONObject(resultStr);

      mockMvc.perform(put("/api/v1/planodesaude/" + objJson.getString("id"))
      .content("{\"nome\":\"Premium Sul\"}")
      .header("Authorization", "Bearer " + jwtToken)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
    }
	

    private String getToken() throws Exception {

		MvcResult resultAuth = mockMvc.perform(post("/api/v1/auth/signin")
				.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String jwtToken = resultAuth.getResponse().getContentAsString();

		return jwtToken;
	}

}
