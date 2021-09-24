package com.ean.drill.member.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class KakaoController {
	private final static String kakaoAuthUrl = "https://kauth.kakao.com";
	private final static String kakaoApiKey = "59ea53f98098c442f2b7872c6b3b016f";
	private final static String redirectURI = "http://localhost:8888/auth/kakao/callback";
	private final static String kakaoApiUrl = "https://kapi.kakao.com";

	@RequestMapping("test.me")
	public String test() {
		return "member/test";
	}
	 @RequestMapping("/login")
	 public String oauth(@RequestParam(value="code", required=false) String code, Model model, HttpServletRequest request)throws Exception 
	 { System.out.println("code:" + code); 
	 HttpSession session = request.getSession(); session.setAttribute("code", code); 
	 return "member/test"; }
	 

}
