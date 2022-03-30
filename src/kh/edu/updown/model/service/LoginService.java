package kh.edu.updown.model.service;

import java.util.List;

import kh.edu.updown.model.dao.LoginDAO;
import kh.edu.updown.model.vo.Member;

public class LoginService {
	
	private LoginDAO dao = new LoginDAO();

	/** 점수 업데이트 Service
	 * @param loginMember
	 * @param count
	 * @return result
	 */
	public int updateScore(Member loginMember, int count) {
		int result = dao.updateScore(loginMember, count);
		
		// DB가 수정된 경우 현재 로그인한 회원의 최고 점수도 같은 값으로 갱신함.
		if(result > 0) {
			loginMember.setHighScore( count );
		}
		return result;
	}

	
	/** 전체 회원 조회(최고 점수 내림 차순) Service
	 * @return members
	 */
	public List<Member> selectAll() {
		List<Member> members = dao.selectAll();
		return members;
	}


	/** 비밀 번호 변경 Service
	 * @param loginMember
	 * @param currentPw
	 * @param newPw
	 * @return result
	 */
	public int updatePw(String memberId, String currentPw, String newPw) {
		int result = dao.updatePw(memberId, currentPw, newPw);
		return result;
	}

}
