package kh.edu.updown.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import kh.edu.updown.model.service.LoginService;
import kh.edu.updown.model.vo.Member;

public class LoginView {

	private Scanner sc = new Scanner(System.in);

	private LoginService loginService = new LoginService();
	
	/** [업다운 게임 시작]
	 * 1 ~ 100 사이 숫자 중 랜덤하게 한 숫자를 지정하고 업/다운 게임을 진행
	 * 입력 시도 횟수가 현재 로그인한 회원의 최초 또는 최고 기록(낮을수록 높은 점수)인 경우 회원의 HIGH_SCORE 컬럼 값 변경
	 * 
	 * @param loginMember
	 * @throws InputMismatchException
	 */
	public void startGame(Member loginMember) throws InputMismatchException{
		
		System.out.println("[Game Start]");
		
		// 1~ 100 사이 난수 발생
		int random = (int)(Math.random() * 100 + 1);
		System.out.println("1~100사이 정수 중 하나를 입력해주세요.");
		
		int count = 0; // 입력 시도 횟수 카운트
		
		while(true) {
			
			count++; // while문이 반복이 될 때 마다 count를 1씩 증가시킴
			
			System.out.print( count + "번째 입력 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			if(random == input) { // 입력된 값이 발생한 난수와 같다면
				System.out.println("정답!!");
				System.out.println("입력 시도 횟수 : " + count);
				
				// 전달 받은 로그인 회원 정보와 입력 시도 횟수를 이용하여 DB 업데이트 
				int result = loginService.updateScore(loginMember, count);
				
				// 최초 또는 최고 기록으로 DB 데이터가 수정된 경우
				if(result > 0) {
					System.out.println("*** 최고 기록 달성 ***");
				}
				
				break; // while문 종료
				
			}else { // 입력된 값이 발생한 난수와 같지 않다면
				
				if(random < input) {
					System.out.println("-- DOWN --");
				}else {
					System.out.println("-- UP --");
				}
			}
			
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	/** [내 정보 조회]
	 * 로그인한 멤버의 정보 중 비밀번호를 제외한 아이디, 이름, 최고 점수만 화면에 출력
	 * 
	 * - 매개변수로 전달 받은 loginMember 활용
	 * 
	 * @param loginMember
	 */
	public void selectMyInfo(Member loginMember) {
		
		System.out.println("[내 정보 조회]");
		System.out.println("아이디 : " + loginMember.getMemberId());
		System.out.println("이름 : " + loginMember.getMemberName());
		System.out.println("최고 점수 : " + loginMember.getHighScore() + "회");
		
	}

	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * [전체 회원 조회]
	 * 전체 회원의 아이디, 최고 점수를 출럭(최고 점수 내림 차순) 
	 * 
	 * - DB에서 전체 회원 정보를 조회하여 List<Member>로 반환 받아와 출력
	 */
	public void selectAll() {
		
		System.out.println("[전체 회원 조회(최고 점수 내림 차순)]");
		
		
	}

	////////////////////////////////////////////////////////////////////////////
	
	/**[비밀번호 변경]
	 * 현재 비밀번호, 새 비밀번호, 새 비밀번호 확인을 입력 받아
	 * 
	 * 1) 새 비밀번호, 새 비밀번호 확인이 같은 경우
	 *  -> 비밀번호 수정 Service 호출
	 *    
	 *   같지 않은 경우 : "새 비밀번호가 일치하지 않습니다." 출력
	 *  
	 * 2) 비밀번호 수정 Service 호출 결과가 1인 경우
	 *    "비밀번호가 변경되었습니다." 출력
	 *    
	 *    결과가 0인 경우
	 *    ""현재 비밀번호가 일치하지 않습니다."
	 *  
	 * @param loginMember
	 */
	public void updatePassword(Member loginMember) {
		
		System.out.println("[비밀번호 변경]");
		
		
		
	}

	
}
