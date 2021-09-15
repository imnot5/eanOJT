package com.ean.drill.member.model.dao;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ean.drill.member.model.vo.Address;
import com.ean.drill.member.model.vo.Member;

@Repository
public class MemberDao {
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.loginMember", m);
	}
	
	public ArrayList<Address> selectAddress(SqlSessionTemplate sqlSession, String memId) {
		return (ArrayList)sqlSession.selectList("memberMapper.selectAddress", memId);
	}
	
	public Address selectEachAddress(SqlSessionTemplate sqlSession, int addressNo) {
		return sqlSession.selectOne("memberMapper.selectEachAddress", addressNo);
	}
	
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}
	public int insertAddress(SqlSessionTemplate sqlSession, Address adr) {
		return sqlSession.insert("memberMapper.insertAddress", adr);
	}
	public int insertNewAdress(SqlSessionTemplate sqlSession, Address adr) {
		return sqlSession.insert("memberMapper.insertNewAddress", adr);
	}
	
	/*
	 * public int insertAddressList(SqlSessionTemplate sqlSession,
	 * ArrayList<Address> adrList) {
	 * 
	 * int result = 0; for(Address adr : adrList) { result =
	 * sqlSession.insert("memberMapper.insertAdrList", adr); } return result; }
	 */
	
	public int idCheck(SqlSessionTemplate sqlSession, String memId) {
		return sqlSession.selectOne("memberMapper.idCheck", memId);
	}
	
	public int updatePwdMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updatePwd", m);
	}
	
	public int updateAdrMember(SqlSessionTemplate sqlSession, Address adr) {
		return sqlSession.update("memberMapper.updateAdr", adr);
	}
}
