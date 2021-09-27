package com.ean.drill.member.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ean.drill.member.model.service.MemberService;
import com.ean.drill.member.model.vo.KakaoProfile;
import com.ean.drill.member.model.vo.Member;
import com.ean.drill.member.model.vo.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
public class KakaoController {
	//private final static String kakaoAuthUrl = "https://kauth.kakao.com";
	//private final static String kakaoApiKey = "59ea53f98098c442f2b7872c6b3b016f";
	//private final static String redirectURI = "http://localhost:8888/auth/kakao/callback";
	//private final static String kakaoApiUrl = "https://kapi.kakao.com";
	@Autowired
	private MemberService mService;
	
	@RequestMapping("test.me")
	public String test() {
		return "member/test";
	}
	 @RequestMapping("/login")
	 public String oauth(@RequestParam(value="code", required=false) String code, Model model, HttpServletRequest request)throws Exception 
	 { System.out.println("code:" + code); 
	 HttpSession session = request.getSession(); 
	 session.setAttribute("code", code);
	 
	 RestTemplate rt = new RestTemplate();
	 
	 HttpHeaders headers = new HttpHeaders();
	 headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "59ea53f98098c442f2b7872c6b3b016f");
		params.add("redirect_uri", "http://localhost:8888/login");
		params.add("code", code);

		//http요청--> 토큰받기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
	
		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
			);
	
		System.out.println(response);

		//토큰으로 사용자정보가져오기
		ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String token = oauthToken.getAccess_token();
        session.setAttribute("token", token);
        System.out.println("tt" + token);
        HttpHeaders headersForRequestProfile = new HttpHeaders();
        headersForRequestProfile.add("Authorization", "Bearer " + Objects.requireNonNull(oauthToken).getAccess_token());
        headersForRequestProfile.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        
		
        HttpEntity<MultiValueMap<String, String>> kakaoResourceProfileRequest = new HttpEntity<>(headersForRequestProfile);

        
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
        ResponseEntity<String> resourceProfileResponse = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoResourceProfileRequest,
            String.class
        );

        KakaoProfile profile = null;
        try {
            profile = objectMapper.readValue(resourceProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("profile" + profile);
        System.out.println(profile.getProperties().getNickname());
        String kakaoName = profile.getProperties().getNickname();
        HashMap<String, String> loginUser = new HashMap<>();
        loginUser.put("name", kakaoName);
        session.setAttribute("loginUserSns", loginUser);
        /*
        Member loginUser = mService.loginMember(profile);
        
        if(loginUser!=null) {//로그인 정보가 있다면
        	session.setAttribute("loginUser", loginUser);
        	return "redirect:/";
        } else {
        	int result = mService.saveMember(profile);
            if(result > 0) {
         	   session.setAttribute("alertMsg", "카카오로 로그인");
         	   return "redirect:/"; 
            } else {
         	   model.addAttribute("errorMsg", "failed");
         	   return "common/errorPage";
            }
        }
        
*/
        return "redirect:/";
       //profile로 정보를 가져가서 가입되어 있는지를 판별--> 그 뒤에 가입 되어 잇으면 loginMember로 보내기
       //없다면 insert돌리고 loginMember(select)돌리기?
	
	 }
	 
	 
	/* 
	 @RequestMapping("klogout")
	 public String kakaoLogout(HttpServletRequest request,HttpSession session) {
		 String accessToken = ((String)session.getAttribute("token"));
		 System.out.println("ac: " + accessToken);
		 
		 
	     HttpHeaders at = new HttpHeaders();
	     at.add("Authorization", "Bearer " + accessToken);
	        
		 RestTemplate rt = new RestTemplate();
		 HttpEntity<MultiValueMap<String, String>> kakaoLogout = new HttpEntity<>(at);
		 
	     ResponseEntity<String> requestLogout = rt.exchange(
	                "https://kapi.kakao.com/v1/user/logout",
	                HttpMethod.POST,
	                kakaoLogout,
	                String.class
	            );
	     ObjectMapper objectMapper = new ObjectMapper();
	     KakaoProfile profile = null;
	        try {
	            profile = objectMapper.readValue(requestLogout.getBody(), KakaoProfile.class);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	     System.out.println(profile.getId());
	     int kId = profile.getId();
	     session.setAttribute("kId", kId);
	     
	     return "redirect:/";

	 }
	 */

	 @RequestMapping("klogout")
	 public @ResponseBody String logout(HttpServletRequest request, HttpSession session) throws Exception {
		 String logoutUrl = "https://kauth.kakao.com/oauth/logout"
				 			+ "?client_id=" + "59ea53f98098c442f2b7872c6b3b016f"
				 			+ "&logout_redirect_uri=" + "http://localhost:8888/";
		 session.invalidate();
		 return logoutUrl;
	 }
}
