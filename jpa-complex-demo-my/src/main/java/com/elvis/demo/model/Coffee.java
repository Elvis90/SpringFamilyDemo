package com.elvis.demo.model;


import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_menu")
@Data
@ToString(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coffee extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name",columnDefinition="varchar(50) COMMENT '咖啡名称'")
	private String name;
	@Column(name="price",columnDefinition="decimal(19) COMMENT '咖啡价格'")
	@Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",parameters= {@org.hibernate.annotations.Parameter(name="currencyCode",value="CNY")})
	private Money price;
}
