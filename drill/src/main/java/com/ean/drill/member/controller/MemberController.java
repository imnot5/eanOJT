package com.ean.drill.member.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ean.drill.member.model.service.MemberService;
import com.ean.drill.member.model.vo.Address;
import com.ean.drill.member.model.vo.Member;
import com.google.gson.Gson;

@Controller
public class MemberController {

	@Autowired
	private MemberService mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session, Model model) {
		Member loginUser = mService.loginMember(m);
		System.out.println("raw:" + m.getMemPwd());
		System.out.println("encode: " + loginUser.getMemPwd());
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getMemPwd(), loginUser.getMemPwd())) {
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "loginFailed");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		return "member/enrollForm";
	}

	@ResponseBody
	@RequestMapping("idCheck.me")
	public String ajaxIdCheck(String checkId) {
		int count = mService.idCheck(checkId);
		return count > 0 ? "NNNNN" : "NNNNY";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Address adr, HttpServletRequest request, Model model, HttpSession session) {
		String memId = request.getParameter("memId");
		m.setMemId(memId);
		adr.setMemId(memId);
		String[] addressArr = request.getParameterValues("memAddress");
		String address = "";
		if(addressArr != null) {
			address = String.join(",", addressArr);
		}
		adr.setAddress(address);
		String phone = request.getParameter("memPhone");
		String memPhone = "";
		if(phone != null) {
			memPhone = String.join("", phone.split("-"));
		}
		m.setMemPhone(memPhone);
		//암호화
		String encPwd = bcryptPasswordEncoder.encode(m.getMemPwd());
		m.setMemPwd(encPwd);
		
		int result = mService.insertMember(m, adr);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "successed");
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "failed");
			return "common/errorPage";
		}
	}
	

	@RequestMapping("myPage.me")
	public ModelAndView myPage(Address adr, HttpSession session, ModelAndView mv, HttpServletRequest request) {

		if(session.getAttribute("loginUser") == null) {
			session.setAttribute("alertMsg", "로그인이 필요한 서비스입니다"); 
			mv.setViewName("redirect:/"); 
		} else { 
			String memId = ((Member)request.getSession().getAttribute("loginUser")).getMemId();		
			ArrayList<Address> adrList = mService.selectAddress(memId);
			mv.addObject("adrList",adrList);
			mv.setViewName("member/myPage");
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("xxx.me")
	public String adrPage(@RequestBody String memId, HttpServletResponse response) {
		String mem = memId;
		System.out.println("memId"+mem);
		ArrayList<Address> list = mService.selectAddress(mem);
		System.out.println(list);
		
		String json = new Gson().toJson(list);
		
		response.setContentType("text/html;charset=UTF-8");
		
		return json;
	}
	
	@RequestMapping("addressPop.me")
	public String addressPop(int adrNo, Address adr, HttpSession session, Model model, HttpServletRequest request) {
		int addressNo = adrNo;
		adr = mService.selectEachAddress(addressNo);
		model.addAttribute("adr",adr);
		return "member/addressPop";
	}
	
	@RequestMapping("adrNewForm.me")
	public String adrNewForm() {
		return "member/addressNewForm";
	}
	
	@RequestMapping("newAddress.me")
	public String insertNewAddress(Address adr, Model model, HttpServletRequest request, HttpSession session) {
		String memId = ((Member)session.getAttribute("loginUser")).getMemId();
		adr.setMemId(memId);
		
		String[] addressArr = request.getParameterValues("address");
		String address = "";
		if(addressArr != null) {
			address = String.join(",", addressArr);
		}
		adr.setAddress(address);
		
		if(adr.getStatus() == null) {
			adr.setStatus("N");
		}
		
		int result = mService.insertNewAddress(adr);
		if(result > 0) {
			session.setAttribute("alertMsg", "주소추가성공");
			model.addAttribute("adr", adr);
			return "member/myPage";
		} else {
			model.addAttribute("errorMsg", "주소변경실패");
			return "common/errorPage";
		}
	}
	@RequestMapping("updatePwd.me")
	public String updatePwdMember(Member m, String memPwd, String newPwd, HttpSession session, Model model) {
		//기존 패스워드 암호화된것
		String encPwd = ((Member)session.getAttribute("loginUser")).getMemPwd();
		String memId = ((Member)session.getAttribute("loginUser")).getMemId();
		//새암호를암호화
		String newEncPwd = bcryptPasswordEncoder.encode(newPwd);
		m.setMemPwd(newEncPwd);
		m.setMemId(memId);
		if(bcryptPasswordEncoder.matches(memPwd, encPwd)) {
			int result = mService.updatePwdMember(m);
			
			if(result > 0) {
				session.setAttribute("alertMsg", "패스워드 변경 성공");
				return "redirect:myPage.me";
			} else {
				model.addAttribute("errorMsg", "패스워드 변경 실패");
				return "common/errorPage";
			}
		} else {
			session.setAttribute("alertMsg", "패스워드 다시 입력");
			return "redirect:myPage.me";
		}
	}
	
	@RequestMapping("adrUpdate.me")
	public String updateAdrMember(Address adr, Model model, HttpSession session) {
		
		int result = mService.updateAdrMember(adr);
		if(result > 0) {
			session.setAttribute("alertMsg", "주소변경완료");
			return "redirect:myPage.me";
		} else {
			model.addAttribute("errorMsg", "주소변경실패");
			return "common/errorPage";
		}
	}
	
}
