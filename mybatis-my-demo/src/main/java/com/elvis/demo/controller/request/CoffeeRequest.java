package com.elvis.demo.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CoffeeRequest {
	@NotEmpty
	private String Name;
	@NotNull
	private Money price;
}
