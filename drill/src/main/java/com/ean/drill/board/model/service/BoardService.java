package com.ean.drill.board.model.service;

import java.util.ArrayList;

import com.ean.drill.board.model.vo.Board;
import com.ean.drill.board.model.vo.Reply;
import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;

public interface BoardService {
	//총 몇 개의 게시물 찾기
	int selectListCount();
	
	//페이징처리한 게시물 정보
	ArrayList<Board> selectList(PageInfo pi);
	ArrayList<Attachment> selectListAll(PageInfo pi);
	
	//첨부파일
	ArrayList<Attachment> selectBoardAtt(int refPno);
	
	//게시글 작성
	int insertBoard(Board b, ArrayList<Attachment> list);
	int addBoardAtt(ArrayList<Attachment> list);
	
	//조회수
	int hitCount(int boardNo);
	
	//상세조회
	Board selectThumb(int boardNo);

	//댓글등록
	int insertReply(Reply r);
	//대댓글등록
	int insertReReply(Reply r);
	
	//댓글조회
	ArrayList<Reply> selectReplyList(int refBno);
}
