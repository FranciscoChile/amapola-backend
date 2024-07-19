package com.pancho.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.pancho.demo.model.CalcRequest;
import com.pancho.demo.model.RecordOperation;
import com.pancho.demo.service.UserRecordService;
import com.pancho.demo.web.CalculatorController;
import com.pancho.demo.web.Mediator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pancho.demo.model.APIResponse;

@WebMvcTest(value = CalculatorController.class)
class DemoApplicationTests {

	@Autowired
    private MockMvc mvc;

    @MockBean
    private UserRecordService userRecordService;

    @MockBean
    private Mediator mediator;

    @Test
    public void listAll_whenGetMethod()
            throws Exception {

        RecordOperation ro = new RecordOperation();
        List<RecordOperation> list = List.of(ro);
        when(userRecordService.findAll() ).thenReturn(list);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData("calcResponse");
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully executed");
        ResponseEntity<APIResponse> response = new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
        when(mediator.handler(any())).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/calculator")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())                
				.andReturn();

        assertNotNull(result.getResponse().getContentAsString());

				
        }


		@Test
    	public void create_whenPostMethod()
            throws Exception {

        CalcRequest calcRequest = CalcRequest.builder().values(Arrays.asList(1d,1d)).userId(1L).build();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(calcRequest);

            APIResponse apiResponse = new APIResponse();
            apiResponse.setData("calcResponse");
            apiResponse.setResponseCode(HttpStatus.OK);
            apiResponse.setMessage("Successfully executed");
            ResponseEntity<APIResponse> response = new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
            when(mediator.handler(any())).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/calculator/add")
				.content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())        
				.andExpect(jsonPath("$.message", Matchers.is("Successfully executed")))
				.andReturn();
					
        assertNotNull(result.getResponse().getContentAsString());
				
        }


        @Test
    	public void deleteById_whenDeleteMethod()
            throws Exception {

        RecordOperation ro = RecordOperation.builder().id(1L).build();
        List<RecordOperation> list = List.of(ro);
        when(userRecordService.findAll() ).thenReturn(list);

            APIResponse apiResponse = new APIResponse();
            apiResponse.setData("calcResponse");
            apiResponse.setResponseCode(HttpStatus.OK);
            apiResponse.setMessage("Successfully executed");
            ResponseEntity<APIResponse> response = new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
            when(mediator.handler(any())).thenReturn(response);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/calculator/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().is2xxSuccessful())     
				.andExpect(jsonPath("$.message", Matchers.is("Successfully executed")))
				.andReturn();
					
        assertNotNull(result.getResponse().getContentAsString());
				
        }


}
