package com.ean.drill.notice.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.notice.model.vo.Notice;

@Repository
public class NoticeDao {

	public ArrayList<Notice> selectNoticeList(SqlSession sqlSession, PageInfo pi){
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("noticeMapper.selectList", null, rowBounds);
	}
	
	public int selectCount(SqlSession sqlSession) {
		return sqlSession.selectOne("noticeMapper.selectCount");
	}
	public int insertNotice(SqlSession sqlSession, Notice n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}
	
	public int increaseCount(SqlSession sqlSession, int noticeNo) {
		return sqlSession.update("noticeMapper.increaseCount", noticeNo);
	}
	public Notice selectNotice(SqlSession sqlSession, int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectNotice", noticeNo);
	}
}
