package com.ean.drill.notice.model.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Notice {
	private String grpCd;
	private String detailCd;
	private String detailNm;
	private int noticeNo;
	private String noticeWriter;
	private String noticeTitle;
	private String noticeContent;
	private String noticeCount;
	private String noticeStatus;
	private String createDate;
	private Date modifyDate;
	private MultipartFile upfile;
}
