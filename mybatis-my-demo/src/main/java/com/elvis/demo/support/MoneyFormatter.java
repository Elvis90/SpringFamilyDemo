package com.elvis.demo.support;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MoneyFormatter implements Formatter<Money>{

	@Override
	public String print(Money money, Locale locale) {
		 if (money == null) {
	            return null;
	        }
	        return money.getCurrencyUnit().getCode() + " " + money.getAmount();
	}

	@Override
	public Money parse(String money, Locale locale) throws ParseException {
		 if (NumberUtils.isParsable(money)) {
	            return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(money));
	        } else if (!StringUtils.isEmpty(money)) {
	            String[] split = StringUtils.split(money, " ");
	            if (split != null && split.length == 2 && NumberUtils.isParsable(split[1])) {
	                return Money.of(CurrencyUnit.of(split[0]),
	                        NumberUtils.createBigDecimal(split[1]));
	            } else {
	                throw new ParseException(money, 0);
	            }
	        }
	        throw new ParseException(money, 0);
	}

}
