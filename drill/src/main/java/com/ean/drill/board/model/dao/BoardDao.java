package com.ean.drill.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ean.drill.board.model.vo.Board;
import com.ean.drill.board.model.vo.Reply;
import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;

@Repository
public class BoardDao {

	public int selectListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.selectListCount");
	}
	
	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi ){
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("boardMapper.selectList", null, rowBounds);
	}
	
	public ArrayList<Attachment> selectBoardAtt(SqlSessionTemplate sqlSession, int refPno){
		return (ArrayList)sqlSession.selectList("boardMapper.selectBoardAtt", refPno);
	}
	
	public ArrayList<Attachment> selectListAll(SqlSessionTemplate sqlSession, PageInfo pi){
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		return (ArrayList)sqlSession.selectList("boardMapper.selectAll", null, rowBounds);
	}
	//게시글 내용만
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("boardMapper.insertBoard", b);
	}
	
	//게시글 첨부파일
	public int insertBoardAtt(SqlSessionTemplate sqlSession, ArrayList<Attachment> list) {
		int result1 = 0;
		int result2 = 1;
		for(Attachment att : list) {
			if(att.getImgLevel() == 1) {
				result1 = sqlSession.insert("boardMapper.insertTopAtt", att);
			} else {
				result2 = sqlSession.insert("boardMapper.insertBelowAtt", att);
			}
		}
		return result1 * result2;
	}
	
	//조회수
	public int hitCount(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.hitCount", boardNo);
	}
	
	//상세조회
	public Board selectThumb(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectThumb", boardNo);
	}
	
	//댓글등록
	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("boardMapper.insertReply", r);
	}
	
	//댓글조회
	public ArrayList<Reply> selectReplyList(SqlSessionTemplate sqlSession, int refBno){
		return (ArrayList)sqlSession.selectList("boardMapper.selectReply", refBno);
	}
}
