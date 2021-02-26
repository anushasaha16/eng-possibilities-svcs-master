package com.investing.forecastbackend.service;

import com.investing.forecastbackend.model.ForecastRequest;
import com.investing.forecastbackend.model.ForecastResponse;
import com.investing.forecastbackend.model.InvestmentDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvestingForecastService {
	
	@SneakyThrows
    public List<InvestmentDetail> getInvestmentOptions(){
        // TODO read investment options from investment-details.json
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    	List<InvestmentDetail> details = objectMapper.readValue(new File("src/main/resources/data/investment-details.json"), new TypeReference<List<InvestmentDetail>>(){});
    	return details;
    }
	
    public ForecastResponse getInvestmentOptions(final ForecastRequest request) {
        // TODO write algorithm to calculate investment forecast from request configuration
    	List<Double> response = new ArrayList<Double>();
    	for(int i = 0; i < 10; i++)
    	{
    		
    	}
    	ForecastResponse forecastResponse = new ForecastResponse();
    	forecastResponse.setResponse(response);
        return forecastResponse;
    }

}
