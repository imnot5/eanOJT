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
		/* System.out.println("noticeList"+list); */
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
	/*
	@RequestMapping("insert.no")
	public String insertNotice(Notice n, Attachment att, MultipartFile[] upfile, Model model, HttpSession session) {

		ArrayList<Attachment> list = new ArrayList<>();
		for(MultipartFile mf : upfile) {
			System.out.println("upfile[0]" + upfile[0]);
			System.out.println("upfile[1]" + upfile[1]);
			if(!mf.isEmpty()) {
				String changeName = saveFile(mf, session);
				att.setOriginName(mf.getOriginalFilename());
				att.setChangeName("resources/uploadFiles/"+changeName);
			}
			list.add(att);
		}
		System.out.println("????????????"+list);
		int result = nService.insertNotice(n, list);
		
		if(result>0) {
			session.setAttribute("alertMsg", "????????????????????????");
			return "redirect:/listView.no";
		} else {
			model.addAttribute("errorMsg", "????????????????????????");
			return "common/errorPage";
		}
		
	}
*/
	@RequestMapping("insert.no")
	public String insertNotice(Notice n, Attachment att, MultipartHttpServletRequest multi, Model model, HttpSession session) {

		List<MultipartFile> fileList = multi.getFiles("upfile");
		
		ArrayList<Attachment> list = new ArrayList<>();
		for(MultipartFile mf : fileList) {
			att = new Attachment();
			String changeName = saveFile(mf, session);
			
			att.setOriginName(mf.getOriginalFilename());
			att.setChangeName("resources/uploadFiles/"+changeName);
			list.add(att);
		}
		int result = nService.insertNotice(n, list);
		
		if(result>0) {
			session.setAttribute("alertMsg", "????????????????????????");
			return "redirect:/listView.no";
		} else {
			model.addAttribute("errorMsg", "????????????????????????");
			return "common/errorPage";
		}
	}
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String savePath = session.getServletContext().getRealPath("resources/uploadFiles/");
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		int ranNum =(int)(Math.random() * 90000 + 10000);
		String ext = originName.substring(originName.lastIndexOf("."));//?????????
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
		//?????????
		int increaseCount = nService.increaseCount(noticeNo);
		//????????????
		ArrayList<Attachment> list = nService.selectNoticeAtt(noticeNo);
		System.out.println("attList: " + list);
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
	 * ???????????? ???????????? ajax ?????? ?????????!
	@ResponseBody
	@RequestMapping(value="detail.no", produces="application/json; charset=utf-8")
	public ModelAndView selectNotice(int nno, ModelAndView mv) {
		int noticeNo = nno;
		//?????????
		int increaseCount = nService.increaseCount(noticeNo);
		//Notice n = nService.selectNotice(noticeNo);
		System.out.println("?????????"+increaseCount);
		
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
	public String updateNotice(MultipartHttpServletRequest multi, Attachment att, Notice n, Model model, HttpSession session, HttpServletRequest request, MultipartFile[] reUpfile) {
		
		//String[] attNo = request.getParameterValues("attachmentNo");
		String[] delFiles = request.getParameterValues("delFiles");
		//??????????????????
		int result2 = 1;
		
		if(delFiles != null) {
			for(String str : delFiles) {
				String[] arr;
				arr = str.split(",");
				System.out.println("arr[0] " + arr[0]);
				String no = arr[0];
				String cname = arr[1];
				//????????????????????????
				new File(session.getServletContext().getRealPath(cname)).delete();
				//db????????????
				int noticeNo = Integer.parseInt(no);
				result2 = nService.deleteNoticeAtt(noticeNo);
			}
		}
		
		ArrayList<Attachment> list =new ArrayList<>();
		//?????? ?????? ????????? ????????????
		int result3 = 1;
		if(!multi.getFile("upfile").getOriginalFilename().equals("")) {
			List<MultipartFile> fileList = multi.getFiles("upfile");
			for(MultipartFile mf: fileList) {
				att = new Attachment();
				String changeName = saveFile(mf, session);
				att.setOriginName(mf.getOriginalFilename());
				att.setChangeName("resources/uploadFiles/" + changeName);
				att.setRefPno(n.getNoticeNo());
				list.add(att);
			}
			result3 = nService.addNoticeAtt(list);
		}

		int result1 = 1;
		
		if(reUpfile!=null) {
			for(int i=0; i<reUpfile.length; i++) {
				System.out.println("reUpfile"+reUpfile[i].getOriginalFilename());
				list = new ArrayList<>();
				//?????? ????????? ??????!
				if(!reUpfile[i].getOriginalFilename().equals("")) {
					ArrayList<Attachment> atList = att.getAtList();
					System.out.println("atList??????:" + atList);
					//????????????????????? ???
					if(atList!=null) {
						//??????????????????
						System.out.println(atList.get(i).getAttachmentNo());
						new File(session.getServletContext().getRealPath(atList.get(i).getChangeName())).delete();
					}
					//???????????????
					String changeName = saveFile(reUpfile[i], session);
					att.setOriginName(reUpfile[i].getOriginalFilename());
					att.setChangeName("resources/uploadFiles/"+changeName);
					att.setAttachmentNo(atList.get(i).getAttachmentNo());
					list.add(att);
				}
			}
			String memId =((Member)session.getAttribute("loginUser")).getMemId();
			n.setNoticeWriter(memId);
			System.out.println("????????????list" + list);
			result1 = nService.updateNotice(n, list);
		}

		System.out.println("?????? ??? ???list "+list);
		System.out.println("result1 " + result1);//??????+??????
		System.out.println("result2 " + result2);//?????? ??????
		System.out.println("result3 " + result3);//?????? ??????
		if(result1 * result2 * result3> 0) {
			model.addAttribute("n", n);
			return "redirect:/detail.no?nno=" + n.getNoticeNo();
		} else {
			model.addAttribute("errorMsg", "???????????? ?????? ??????");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("delete.no")
	public String deleteNotice(Attachment att, int noticeNo, Model model, HttpSession session) {
		ArrayList<Attachment> atList = att.getAtList();
		//??????????????? ?????? ??????
		int result = nService.deleteNotice(noticeNo, atList);
		if(result > 0) {
			session.setAttribute("alertMsg", "????????????????????????");
			return "redirect:/listView.no";
		} else {
			model.addAttribute("errorMsg", "????????? ?????? ??????");
			return "common/errorPage";
		}
	}
}
