package com.ean.drill.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ean.drill.board.model.dao.BoardDao;
import com.ean.drill.board.model.vo.Board;
import com.ean.drill.board.model.vo.Reply;
import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao bDao;
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Override
	public int selectListCount() {
		return bDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return bDao.selectList(sqlSession, pi);
	}
	@Override
	public ArrayList<Attachment> selectListAll(PageInfo pi){
		return bDao.selectListAll(sqlSession, pi);
	}
 	@Override
	public ArrayList<Attachment> selectBoardAtt(int refPno){
		return bDao.selectBoardAtt(sqlSession, refPno);
	}

	@Override
	public int insertBoard(Board b, ArrayList<Attachment> list) {
		int result1 = bDao.insertBoard(sqlSession, b);
		int result2 = 1;
		System.out.println("service "+list);
		if(!list.isEmpty()) {
			result2 = bDao.insertBoardAtt(sqlSession, list);
		}
		return result1 * result2;
		
	}

	@Override
	public int addBoardAtt(ArrayList<Attachment> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hitCount(int boardNo) {
		return bDao.hitCount(sqlSession, boardNo);
	}

	@Override
	public Board selectThumb(int boardNo) {
		return bDao.selectThumb(sqlSession, boardNo);
	}

	@Override
	public int insertReply(Reply r) {
		return bDao.insertReply(sqlSession, r);
	}

	@Override
	public ArrayList<Reply> selectReplyList(int refBno) {
		return bDao.selectReplyList(sqlSession, refBno);
	}
	
	


}
