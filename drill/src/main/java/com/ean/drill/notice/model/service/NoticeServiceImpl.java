package com.ean.drill.notice.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.notice.model.dao.NoticeDao;
import com.ean.drill.notice.model.vo.Notice;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao nDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public ArrayList<Notice> selectNoticeList(PageInfo pi) {
		return nDao.selectNoticeList(sqlSession, pi); 
	}

	@Override
	public int selectCount() {
		return nDao.selectCount(sqlSession);
	}
	
	@Override
	public int insertNotice(Notice n,  ArrayList<Attachment> list) {
		int result1 = nDao.insertNotice(sqlSession, n);
		int result2 = 1;
		if(!list.isEmpty()) {
			result2 =nDao.insertNoticeAtt(sqlSession, list);
		}
		System.out.println("result2: " + result2);
		return result1 * result2;
	}

	@Override
	public int increaseCount(int noticeNo) {
		return nDao.increaseCount(sqlSession, noticeNo);
	}

	@Override
	public Notice selectNotice(int noticeNo) {
		return nDao.selectNotice(sqlSession, noticeNo);
	}

	@Override
	public ArrayList<Attachment> selectNoticeAtt(int noticeNo){
		return nDao.selectNoticeAtt(sqlSession, noticeNo);
	}
	
	@Override
	public int updateNotice(Notice n, ArrayList<Attachment> list) {
		int result1 = nDao.updateNotice(sqlSession, n);
		int result2 = 1;
		if(!list.isEmpty()) {
			result2 = nDao.updateNoticeAtt(sqlSession, list);
		}
		return result1 * result2;
	}

	@Override
	public int deleteNotice(int noticeNo, ArrayList<Attachment> atList) {
		int result1 = nDao.deleteNotice(sqlSession, noticeNo);
		int result2 = 1;
		if(!atList.isEmpty()) {
			result2 = nDao.deleteNoticeAtt(sqlSession, noticeNo);
		}
		
		return result1 * result2;
		
	}

}
