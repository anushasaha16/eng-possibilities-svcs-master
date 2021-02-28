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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    	List<InvestmentDetail> details = (ArrayList) objectMapper.readValue(new File("src/main/resources/data/investment-details.json"), Map.class).get("Investments");
    	String str = objectMapper.writeValueAsString(details);
    	return objectMapper.readValue(str, new TypeReference<List<InvestmentDetail>>() {});
    }
	
    public ForecastResponse getInvestmentOptions(final ForecastRequest request) {
        // TODO write algorithm to calculate investment forecast from request configuration
    	List<InvestmentDetail> details = getInvestmentOptions();
    	List<Double> response = getForeCast(request.getRequest(), details);
    	ForecastResponse forecastResponse = new ForecastResponse();
    	forecastResponse.setResponse(response);
        return forecastResponse;
    }
    
    public List<Double> getForeCast(Map<String, Double> userRequest, List<InvestmentDetail> details) {
    	Map<Integer, Double> totalYearAmount = new HashMap<>();
        for (InvestmentDetail i : details) {
            //user input for category i
            double userInvestmentPercentage = userRequest.get(i.getCategory());
            double userInvestmentDollars = (userInvestmentPercentage / 100) * 10000;
            for (int x = 0; x < 10; x++) {

                //historical interest data for category i in year x
                double historicalInterest = Double.valueOf(i.getData().get(x));
                double currentInterest = (historicalInterest / 100) * userInvestmentDollars;

                userInvestmentDollars = userInvestmentDollars + currentInterest;

                Double currentYearTotal = totalYearAmount.getOrDefault(x, 0.0);
                //add total amount for category i in year x in Map<Integer, Double> totalYearAmount
                //continuously sum total for each investment i in year x
                totalYearAmount.put(x, currentYearTotal + userInvestmentDollars);
            }
        }
        return new ArrayList<>(totalYearAmount.values());
    }
    

}
