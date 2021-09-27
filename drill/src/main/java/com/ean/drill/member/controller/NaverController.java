package com.ean.drill.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jpath.expression.Parser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;

@Controller
public class NaverController {

	private NaverLoginBO naverLoginBO;
	
	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}
	
	@RequestMapping("getNaverAuthUrl")
	public @ResponseBody String getNaverAuthUrl(HttpSession session) throws Exception{
		String reqUrl = naverLoginBO.getAuthorizationUrl(session);
		System.out.println("naver" + reqUrl);
		return reqUrl;
	}
	
	@RequestMapping(value="/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(HttpServletRequest request, HttpSession session, Model model) throws Exception{
		JSONParser parser = new JSONParser();
		Gson gson = new Gson();
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String error = request.getParameter("error");
		/*
		if(error != null) {
			if(error.equals("access_denied")) {
				return "redirect:/";
			};
		}
		*/
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		//string 형식의 json데이터
		String loginInfo = naverLoginBO.getUserProfile(oauthToken);
		//사용자 정보를 json형태로 바꾸기
		Object obj = parser.parse(loginInfo);
		JSONObject jsonObj = (JSONObject)obj;
		//받아온 정보 파싱
		JSONObject callbackResponse = (JSONObject)jsonObj.get("response");
		String naverUniqueNo = callbackResponse.get("id").toString();
		String naverName = callbackResponse.get("name").toString();
		HashMap<String, String> loginUser = new HashMap<>();
		loginUser.put("id", naverUniqueNo);
		loginUser.put("name", naverName);
		session.setAttribute("loginUserSns", loginUser);
		System.out.println(loginUser);
		return "redirect:/";
	}
	

}
