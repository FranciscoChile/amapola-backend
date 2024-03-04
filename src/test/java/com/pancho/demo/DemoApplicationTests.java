package com.pancho.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pancho.demo.controller.FavoriteController;
import com.pancho.demo.model.APIResponse;
import com.pancho.demo.model.FavDTO;
import com.pancho.demo.service.FavoriteService;

@WebMvcTest(value = FavoriteController.class)
class DemoApplicationTests {


	@Autowired
    private MockMvc mvc;

    @MockBean
    private FavoriteService favoriteService;


    @Test
    public void listAll_whenGetMethod()
            throws Exception {

        FavDTO favDTO = new FavDTO();
        favDTO.setDescription("description");

        List<FavDTO> list = List.of(favDTO);

        when(favoriteService.findAll()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/favs")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())                
				.andReturn();
					
		String data = result.getResponse().getContentAsString();		
		ObjectMapper objectMapper = new ObjectMapper();
		APIResponse resp = objectMapper.readValue(data, APIResponse.class);
		
		List listData = (List) resp.getData();
		Map.Entry<String, String> entryTest = (Entry<String, String>) ((LinkedHashMap)listData.get(0)).entrySet().toArray()[2];

        assertNotNull(result.getResponse().getContentAsString());
		assertEquals(entryTest.getValue(), "description");
				
        }


		@Test
    	public void create_whenPostMethod()
            throws Exception {

        FavDTO favDTO = new FavDTO();
		favDTO.setId(1L);
        favDTO.setDescription("description");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(favDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/favs")                                
				.content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())        
				.andExpect(jsonPath("$.message", Matchers.is("Successfully created")))
				.andReturn();
					
        assertNotNull(result.getResponse().getContentAsString());
				
        }


        @Test
    	public void deleteById_whenDeleteMethod()
            throws Exception {

        FavDTO favDTO = new FavDTO();
        favDTO.setId(1L);
        favDTO.setDescription("description");
        
        List<FavDTO> list = List.of(favDTO);

        when(favoriteService.findAll()).thenReturn(list);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/favs/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())     
				.andExpect(jsonPath("$.message", Matchers.is("Successfully deleted")))           
				.andReturn();
					
        assertNotNull(result.getResponse().getContentAsString());
				
        }


}
