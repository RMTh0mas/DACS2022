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
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.JsonpCharacterEscapes;

import br.univille.apidacs2022.api.MedicoControllerAPI;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTestsMedico {

	@Autowired
	private MedicoControllerAPI medicoControllerAPI;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(medicoControllerAPI).isNotNull();
	}

	@Test
	void medicoControllerAPIPOSTGETTest() throws Exception {
		String jwtToken = getToken();

		MvcResult result = mockMvc.perform(post("/api/v1/medico")
				.content("{\"nome\":\"Lucas\",\"crm\":\"123\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medico/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Lucas")))
				.andExpect(jsonPath("$.crm", is("123")));
	}

	@Test
    void medicoControllerAPIPOSTDELTest() throws Exception {
      String jwtToken = getToken();

      MvcResult result = mockMvc.perform(post("/api/v1/medico")
				.content("{\"nome\":\"Paulo\",\"crm\":\"5988921\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

      String resultStr = result.getResponse().getContentAsString();
      JSONObject objJson = new JSONObject(resultStr);

      mockMvc.perform(delete("/api/v1/medico/" + objJson.getString("id"))
      .content("{\"nome\":\"Paulo\",\"crm\":\"5988921\"}")
      .header("Authorization", "Bearer " + jwtToken)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

    }

	@Test
    void medicoControllerAPIPOSTPUTTest() throws Exception {
      String jwtToken = getToken();

      MvcResult result = mockMvc.perform(post("/api/v1/medico")
				.content("{\"nome\":\"Carlinhos\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

      String resultStr = result.getResponse().getContentAsString();
      JSONObject objJson = new JSONObject(resultStr);

      mockMvc.perform(put("/api/v1/medico/" + objJson.getString("id"))
      .content("{\"nome\":\"Zezinho\"}")
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
