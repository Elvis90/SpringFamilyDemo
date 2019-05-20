package com.elvis.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2452675659499269983L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@CreationTimestamp
	@Column(name="create_time",updatable=false)
	private Date createTime;
	@UpdateTimestamp
	@Column(name="update_time",insertable=false)
	private Date UpdateTime;
	@Column(name="is_delete",nullable=false,columnDefinition="INT default 0")
	private int is_delete;//1表示该条数据已删除
}
