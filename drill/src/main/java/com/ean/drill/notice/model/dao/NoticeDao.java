package com.ean.drill.notice.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.notice.model.vo.Notice;

@Repository
public class NoticeDao {

	public ArrayList<Notice> selectNoticeList(SqlSessionTemplate sqlSession, PageInfo pi){
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("noticeMapper.selectList", null, rowBounds);
	}
	
	public int selectCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("noticeMapper.selectCount");
	}
	
	public int insertNotice(SqlSessionTemplate sqlSession, Notice n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}
	
	public int insertNoticeAtt(SqlSessionTemplate sqlSession, ArrayList<Attachment> list) {
		int result = 0;
		for(Attachment att : list) {
			result = sqlSession.insert("noticeMapper.insertNoticeAtt", att);
		}
		System.out.println("dao" + result);
		return result;
	}
	public int addNoticeAtt(SqlSessionTemplate sqlSession, ArrayList<Attachment> list) {
		int result = 0;
		for(Attachment att : list) {
			result = sqlSession.insert("noticeMapper.addNoticeAtt", att);
		}
		return result;
	}
	public int increaseCount(SqlSessionTemplate sqlSession, int noticeNo) {
		return sqlSession.update("noticeMapper.increaseCount", noticeNo);
	}
	
	public Notice selectNotice(SqlSessionTemplate sqlSession, int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectNotice", noticeNo);
	}
	
	public ArrayList<Attachment> selectNoticeAtt(SqlSessionTemplate sqlSession, int noticeNo) {
		return (ArrayList)sqlSession.selectList("noticeMapper.selectNoticeAtt", noticeNo);
	}
	
	public int updateNotice(SqlSessionTemplate sqlSession, Notice n) {
		return sqlSession.update("noticeMapper.updateNotice", n);
	}
	
	public int updateNoticeAtt(SqlSessionTemplate sqlSession, ArrayList<Attachment> list) {
		int result = 0;
		for(Attachment att : list) {
			result = sqlSession.update("noticeMapper.updateNoticeAtt", att);
		}
		return result;
	}
	public int deleteNotice(SqlSessionTemplate sqlSession, int noticeNo) {
		int result = sqlSession.delete("noticeMapper.deleteNotice", noticeNo);
		return result;
	}
	public int deleteNoticeAtt(SqlSessionTemplate sqlSession, int noticeNo) {
		return sqlSession.delete("noticeMapper.deleteNoticeAtt", noticeNo);
	}
}
