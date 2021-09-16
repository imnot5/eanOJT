package com.ean.drill.notice.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.ean.drill.common.model.vo.Attachment;
import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.common.template.Pagination;
import com.ean.drill.member.model.vo.Member;
import com.ean.drill.notice.model.service.NoticeService;
import com.ean.drill.notice.model.vo.Notice;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService nService;
	
	@RequestMapping("listView.no")
	public String viewNoticePage(Model model) {
		return "notice/noticeList";
	}
	
	@ResponseBody
	@RequestMapping(value="list.no", produces="application/json; charset=utf-8")
	public Map<String, Object> selectNoticeList(@RequestParam(value="currentPage", defaultValue="1") int currentPage) {
		
		int listCount = nService.selectCount();
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Notice> list = nService.selectNoticeList(pi);
		System.out.println("noticeList"+list);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("pi", pi);
		return result;
	}
	
	@RequestMapping("enrollForm.no")
	public String enrollForm() {
		return "notice/noticeEnrollForm";
	}
	
	@RequestMapping("detailView.no")
	public String detailViewNotice() {
		return "notice/noticeDetailView";
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="insert.no", method= RequestMethod.POST)
	public ModelAndView insertNotice(Notice n, ModelAndView mv) {
		int result = nService.insertNotice(n);

		if(result>0) {
			mv.addObject("code", "success");
		} else {
			mv.addObject("code", "failed");
		}
		return mv;
	}
	*/
	
	@RequestMapping("insert.no")
	public String insertNotice(Notice n, Attachment att, MultipartFile[] upfile, Model model, HttpSession session) {

		/*
		 * List<MultipartFile> upfileList = multi.getFiles("upfile");
		 * System.out.println("upfileList " + upfileList); ArrayList<Attachment> list =
		 * new ArrayList<>();
		 */
		
		ArrayList<Attachment> list = new ArrayList<>();
		for(MultipartFile mf : upfile) {
			System.out.println("upfile" + upfile);
			if(!mf.isEmpty()) {
				String changeName = saveFile(mf, session);
				att.setOriginName(mf.getOriginalFilename());
				att.setChangeName("resources/uploadFiles/"+changeName);
				//att.setDetailCd("4");//공지사항코드
			}
			list.add(att);
		}
		System.out.println("컨트롤러"+list);
		int result = nService.insertNotice(n, list);
		
		if(result>0) {
			session.setAttribute("alertMsg", "공지사항작성성공");
			return "redirect:/listView.no";
		} else {
			model.addAttribute("errorMsg", "공지사항작성실패");
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
	@RequestMapping("detail.no")
	public ModelAndView selectNotice(int nno, ModelAndView mv) {
		int noticeNo = nno;
		//조회수
		int increaseCount = nService.increaseCount(noticeNo);
		//첨부파일
		ArrayList<Attachment> list = nService.selectNoticeAtt(noticeNo);
		Notice n = nService.selectNotice(noticeNo);
		if(increaseCount > 0) {
			mv.addObject("code","0000");
			mv.addObject("n", n).setViewName("notice/noticeDetailView");
			mv.addObject("list", list).setViewName("notice/noticeDetailView");
		} else {
			mv.addObject("code", "error");
		}
		return mv;
	}
	
	/*
	 * 공지사항 상세조회 ajax 다시 해보기!
	@ResponseBody
	@RequestMapping(value="detail.no", produces="application/json; charset=utf-8")
	public ModelAndView selectNotice(int nno, ModelAndView mv) {
		int noticeNo = nno;
		//조회수
		int increaseCount = nService.increaseCount(noticeNo);
		//Notice n = nService.selectNotice(noticeNo);
		System.out.println("조회수"+increaseCount);
		
		if(increaseCount > 0) {
			mv.addObject("code","0000");
			//mv.addObject("n", n).setViewName("notice/noticeDetailView");
		} else {
			mv.addObject("code", "error");
		}
		return mv;
	}
	*/
	
	@RequestMapping("update.no")
	public String updateNotice(Attachment att, Notice n, Model model, HttpSession session, MultipartFile[] reUpfile) {
		
		//String[] attNo = request.getParameterValues("attachmentNo");
		
		ArrayList<Attachment> atList = att.getAtList();
		ArrayList<Attachment> list = new ArrayList<>();
		System.out.println("atList: "+atList);
		for(int i=0; i<reUpfile.length; i++) {
			if(!reUpfile[i].getOriginalFilename().equals("")) {
				//기존파일이있을 때
				if(!atList.isEmpty()) {
					//기존파일삭제
					new File(session.getServletContext().getRealPath(atList.get(i).getChangeName())).delete();
				}
				//새파일저장
				String changeName = saveFile(reUpfile[i], session);
				att.setOriginName(reUpfile[i].getOriginalFilename());
				att.setChangeName("resources/uploadFiles/"+changeName);
				att.setAttachmentNo(atList.get(i).getAttachmentNo());
			} 
			list.add(att);
		}

		String memId =((Member)session.getAttribute("loginUser")).getMemId();
		n.setNoticeWriter(memId);
		int result = nService.updateNotice(n, list);
		if(result > 0) {
			model.addAttribute("n", n);
			return "redirect:/detail.no?nno=" + n.getNoticeNo();
		} else {
			model.addAttribute("errorMsg", "공지사항 수정 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("delete.no")
	public String deleteNotice(Attachment att, int noticeNo, Model model, HttpSession session) {
		ArrayList<Attachment> atList = att.getAtList();
		//첨부파일이 있을 경우
		int result = nService.deleteNotice(noticeNo, atList);
		if(result > 0) {
			session.setAttribute("alertMsg", "공지사항삭제성공");
			return "redirect:/listView.no";
		} else {
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorPage";
		}
	}
}
