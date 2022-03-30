package kh.edu.updown.model.service;

import kh.edu.updown.model.dao.MainDAO;
import kh.edu.updown.model.vo.Member;

public class MainService {

	private MainDAO dao = new MainDAO();
	
	/** 아이디 중복 검사 Service
	 * @param memberId
	 * @return result
	 */
	public int duplicateCheck(String memberId) {
		int result = dao.duplicateCheck(memberId);
		return result;
	}

	/** 회원 가입 Service
	 * @param mem
	 * @return result
	 */
	public int signUp(Member mem) {
		int result = dao.signUp(mem);
		return result;
	}

	/** 로그인 Service
	 * @param mem
	 * @return loginMember
	 */
	public Member login(Member mem) {
		Member loginMember = dao.login(mem);
		return loginMember;
	}

}
