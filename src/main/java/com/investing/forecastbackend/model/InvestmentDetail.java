package com.investing.forecastbackend.model;

import lombok.Data;

@Data
// TODO Model the data read from ../resources/data/investment-details.json
public class InvestmentDetail {
	private String category;
	private int minimum;
	private double[] data;
}
