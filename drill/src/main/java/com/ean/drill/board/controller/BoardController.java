package com.ean.drill.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ean.drill.board.model.service.BoardService;
import com.ean.drill.board.model.vo.Board;
import com.ean.drill.board.model.vo.Reply;
import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.common.template.Pagination;
import com.google.gson.Gson;

@Controller
public class BoardController {

	@Autowired
	public BoardService bService;
	
	
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model) {
		int listCount = bService.selectListCount();
		//한페이지 30개씩보이게끔
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 30, 5);
		ArrayList<Board> list = bService.selectList(pi);
		ArrayList<Attachment> glist = bService.selectListAll(pi);
		//ArrayList<Attachment> alist = bService.selectBoardAtt();
		
		model.addAttribute("list", list); //게시글정보+페이징
		//model.addAttribute("alist",alist); //첨부파일정보만
		model.addAttribute("glist", glist); //게시글+페이징+첨부파일
		model.addAttribute("pi", pi); //페이징
		
		return "board/thumbListView";
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/thumbEnrollForm";
	}
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, Attachment att, MultipartHttpServletRequest multi, Model model, HttpSession session) {
		List<MultipartFile> fileList = multi.getFiles("upfile");
		ArrayList<Attachment> list = new ArrayList<>();
		//왜 두번씩 돌지??
		for(int i=0; i<fileList.size(); i++) {
			att = new Attachment();
			if(i==0) {//top-image: image-level : 1
				String changeName = saveFile(fileList.get(i), session);
				att.setOriginName(fileList.get(i).getOriginalFilename());
				att.setChangeName("resources/uploadFiles/" + changeName);
				att.setImgLevel(1);
			} else {
				//belowIamge image-level:2
				String changeName = saveFile(fileList.get(i),session);
				att.setOriginName(fileList.get(i).getOriginalFilename());
				att.setChangeName("resources/uploadFiles/" + changeName);
				att.setImgLevel(2);
			}
			
			list.add(att);
		}
		System.out.println("ctrl: " + list);
		int result = bService.insertBoard(b, list);
		
		if(result>0) {
			session.setAttribute("alertMsg", "섬네일등록");
			return "redirect:/listView.bo";
		} else {
			model.addAttribute("errorMsg", "failed");
			return "common/errorPage";
		}
	}
	
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String savePath = session.getServletContext().getRealPath("resources/uploadFiles/");
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		int ranNum =(int)(Math.random() * 90000 + 10000);
		String ext = originName.substring(originName.lastIndexOf("."));//확장자
		String changeName = currentTime + "_" + ranNum + ext;
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return changeName;
	}
	
	@RequestMapping("detail.bo")
	public ModelAndView selectDetail(int bno, ModelAndView mv) {
		int boardNo = bno;
		int refPno = bno;
		int hitCount = bService.hitCount(boardNo);
		if(hitCount>0) {
			//게시글 정보
			Board b = bService.selectThumb(boardNo);
			mv.addObject("b", b).setViewName("board/thumbDetail");
			//게시글에 딸린 첨부파일
			ArrayList<Attachment> list = bService.selectBoardAtt(refPno);
			mv.addObject("list", list).setViewName("board/thumbDetail");
		} else {
			mv.addObject("errorMsg", "failed").setViewName("common/errorPage");
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="insertRply.bo")
	public String insertReply(Reply r) {

		int result = bService.insertReply(r);
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value="selectRply.bo", produces="application/json; charset=utf-8")
	public String selectReply(int bno, Model model) {
		int refBno = bno;
		ArrayList<Reply> list = bService.selectReplyList(refBno);
		model.addAttribute("list", list);//아니지 그 댓글의 번호가 필요한건데??
		return new Gson().toJson(list);
	}
	
	
	@ResponseBody
	@RequestMapping(value="insertReRply.bo")
	public String insertReReply(Reply r) {
		//대댓글 입력
		r.setReplyParent(r.getReplyNo());
		r.setReplyDepth(1);
		
 		int result2 = bService.insertReReply(r);
		return result2 > 0 ? "success" : "fail";
	}
}
