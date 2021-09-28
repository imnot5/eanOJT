package com.ean.drill.board.model.vo;

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
public class Board {
	private String grpCd;
	private String detailCd;
	private int boardNo;
	private String boardWriter;
	private String boardTitle;
	private String boardContent;
	private int boardCount;
	private String boardStatus;
	private Date createDate;
	private Date modifyDate;
}
