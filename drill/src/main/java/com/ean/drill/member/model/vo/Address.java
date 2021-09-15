package com.ean.drill.member.model.vo;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Address {
	private int addressNo;
	private String memId;
	private String aka;
	private String address;
	private String zipCode;
	private String status;
	private Date createDate;
	private Date modifyDate;
	private ArrayList<Address> adrList;
}
