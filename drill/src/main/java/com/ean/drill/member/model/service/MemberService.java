package com.ean.drill.member.model.service;

import java.util.ArrayList;

import com.ean.drill.member.model.vo.Address;
import com.ean.drill.member.model.vo.Member;

public interface MemberService {
	
	//login
	Member loginMember(Member m);
	
	//주소정보조회
	ArrayList<Address> selectAddress(String memId);
	Address selectEachAddress(int addressNo);
	
	//주소저장
	int insertAddressList(ArrayList<Address> adrList);
	
	//주소수정
	int updateAdrMember(Address adr);
	
	//회원가입
	int insertMember(Member m, Address adr);
	
	//회원정보수정
	int updateMember(Member m);
	
	//패스워드수정
	int updatePwdMember(Member m);
	
	//회원탈퇴
	int deleteMember(String memId);
	
	//아이디 중복 체크
	int idCheck(String memId);
}
