package com.ean.drill.member.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ean.drill.member.model.dao.MemberDao;
import com.ean.drill.member.model.vo.Address;
import com.ean.drill.member.model.vo.KakaoProfile;
import com.ean.drill.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDao mDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Member loginMember(Member m) {
		return mDao.loginMember(sqlSession, m);
	}

	@Override
	public Member loginMember(KakaoProfile profile) {
		return mDao.loginMember(sqlSession, profile);
	}
	@Override
	public ArrayList<Address> selectAddress(String memId) {
		return mDao.selectAddress(sqlSession, memId);
	}
	
	@Override
	public Address selectEachAddress(int addressNo) {
		return mDao.selectEachAddress(sqlSession, addressNo);
	}
	
	@Override
	public int insertNewAddress(Address adr) {
		return mDao.insertNewAdress(sqlSession, adr);
	}
	

	/*
	 * @Override public int insertAddressList(ArrayList<Address> adrList) { return
	 * mDao.insertAddressList(sqlSession, adrList); }
	 */
	
	@Override
	public int insertMember(Member m, Address adr) {
		int result1 = mDao.insertMember(sqlSession, m);
		int result2 = mDao.insertAddress(sqlSession, adr);
		return result1 * result2;
	}

	@Override
	public int saveMember(KakaoProfile profile) {
		return mDao.saveMember(sqlSession, profile);
	}
	
	@Override
	public int updateMember(Member m) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePwdMember(Member m) {
		return mDao.updatePwdMember(sqlSession, m);
	}
	
	@Override
	public int updateAdrMember(Address adr) {
		return mDao.updateAdrMember(sqlSession, adr);
	}
	@Override
	public int deleteMember(String memId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int idCheck(String memId) {
		return mDao.idCheck(sqlSession, memId);
	}

}
