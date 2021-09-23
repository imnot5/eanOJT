package com.ean.drill.notice.model.service;

import java.util.ArrayList;

import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.notice.model.vo.Notice;

public interface NoticeService {

	//공지사항 리스트 조회
	ArrayList<Notice> selectNoticeList(PageInfo pi);
	int selectCount();
	
	//공지사항 작성
	int insertNotice(Notice n, ArrayList<Attachment> att);
	int addNoticeAtt(ArrayList<Attachment> list);
	
	//공지사항 상세 조회
	int increaseCount(int noticeNo);
	Notice selectNotice(int noticeNo);
	ArrayList<Attachment> selectNoticeAtt(int noticeNo);
	
	//공지사항 수정
	int updateNotice(Notice n, ArrayList<Attachment> list);
	
	//공지사항 삭제
	int deleteNotice(int noticeNo, ArrayList<Attachment> atList);
	//공지사항 첨부파일개별삭제
	int deleteNoticeAtt(int noticeNo);
}
