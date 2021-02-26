package com.investing.forecastbackend.service;

import java.util.List;

import com.investing.forecastbackend.model.InvestmentDetail;

public class InvestingForecastServiceTest {
	
	public static void main(String[]args) {
	InvestingForecastService service = new InvestingForecastService();
	List<InvestmentDetail> details = service.getInvestmentOptions();
	System.out.println(details.get(0).getCategory());
	}
}
