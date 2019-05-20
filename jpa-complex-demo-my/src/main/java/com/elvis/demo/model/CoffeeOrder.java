package com.elvis.demo.model;

import java.util.List;


@Table(name="coffee_order")
@Entity
@Data
@Builder
@ToString(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeOrder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1331185523437559989L;
	@Column(name="customer",columnDefinition="varchar(255) COMMENT '顾客姓名'")
	private String customer;
	@ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
	@OrderBy("UpdateTime")
	private List<Coffee> coffeelist;
	@Column(name="status",nullable=false,columnDefinition="int(1) COMMENT '订单状态'")
	private OrderStatus status;
}
