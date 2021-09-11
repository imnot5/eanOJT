package com.ean.drill.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Member {

	private String memId;
	private String memPwd;
	private String memName;
	private String memEmail;
	private String memPhone;
	private String memAddress;
	private String memZipcode;
	private Date memBday;
	private String memGender;
	private Date memCreateDate;
	private Date memModifyDate;
	private String memStatus;
}
