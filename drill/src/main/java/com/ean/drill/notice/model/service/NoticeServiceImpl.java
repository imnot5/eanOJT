package com.ean.drill.notice.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int insertNotice(Notice n) {
		return nDao.insertNotice(sqlSession, n);
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
	public int updateNotice(Notice n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteNotice(int noticeNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
