package com.ean.drill.notice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ean.drill.common.model.vo.PageInfo;
import com.ean.drill.common.template.Pagination;
import com.ean.drill.notice.model.service.NoticeService;
import com.ean.drill.notice.model.vo.Notice;
import com.google.gson.Gson;

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
	
	@ResponseBody
	@RequestMapping(value="insert.no", method= RequestMethod.POST)
	public String insertNotice(Notice n, Model model) {
		int result = nService.insertNotice(n);

		if(result>0) {
			return "success";
		} else {
			model.addAttribute("errorMsg", "failed");
			return "common/errorPage";
		}
	}
	
	/*
	@RequestMapping("detail.no")
	public ModelAndView selectNotice(int nno, ModelAndView mv) {
		int noticeNo = nno;
		//조회수
		int increaseCount = nService.increaseCount(noticeNo);
		Notice n = nService.selectNotice(noticeNo);
		if(increaseCount > 0) {
			mv.addObject("code","0000");
			mv.addObject("n", n).setViewName("notice/noticeDetailView");
		} else {
			mv.addObject("code", "error");
		}
		return mv;
	}
	*/
	
	
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
}
