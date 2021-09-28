package com.ean.drill.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@ToString
public class Reply {

	private int replyNo;
	private int refBno;
	private int detailCd;
	private int replyDepth;
	private int replyParent;
	private int replyOrder;
	private String replyWriter;
	private String replyContent;
	private String createDate;
	private Date modifyDate;
	private String status;
}
