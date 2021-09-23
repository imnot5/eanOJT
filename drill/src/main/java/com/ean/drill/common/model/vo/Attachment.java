package com.ean.drill.common.model.vo;

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
public class Attachment {
	private String grpCd;
	private String detailCd;
	private int attachmentNo;
	private int refPno;
	private String originName;
	private String changeName;
	private String status;
	private Date createDate;
	private Date modifyDate;
	private int imgLevel;
	private ArrayList<Attachment> atList;
}
