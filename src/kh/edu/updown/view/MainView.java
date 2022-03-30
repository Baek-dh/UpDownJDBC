package kh.edu.updown.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import kh.edu.updown.model.service.LoginService;
import kh.edu.updown.model.service.MainService;
import kh.edu.updown.model.vo.Member;

public class MainView {
	private Scanner sc = new Scanner(System.in);
	
	// 현재 로그인한 회원의 정보를 참조할 변수 선언.
	private Member loginMember = null; // 로그인 X == null,  로그인 O != null
	
	// 메인 페이지 기능이 포함된 MainService 객체 생성
	private MainService mainService = new MainService();
	
	
	// 로그인 전용 화면이 포함된 LoginView 객체 생성
	private LoginView loginView = new LoginView();
		
	
	/** [메뉴 출력 메소드]
	 * 로그인, 비로그인 화면 구현
	 */
	public void displayMenu() {

		int sel = 0; // 메뉴 선택용 변수
		
		do {
			try {
				
				System.out.println();
				System.out.println("=== UP/DOWN 게임 ===");
				
				if(loginMember == null) { // 로그인이 되어있지 않은 경우
				
					System.out.println("[메인 메뉴]");
					System.out.println("1. 회원가입");
					System.out.println("2. 로그인");
					System.out.println("0. 종료");
					System.out.print("메뉴 선택 >> ");
					
					sel = sc.nextInt();
					sc.nextLine();
					System.out.println();
					
					switch(sel) {
					case 1 : signUp(); break;
					case 2 : login(); break;
					case 0 : System.out.println("[프로그램 종료]"); break;
					default : System.out.println("잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.");
					}
				
				}else { // 로그인이 되어있는 경우
					
					System.out.println("[로그인 메뉴]");
					System.out.println("1. 업/다운 게임 start");
					System.out.println("2. 내 정보 조회");
					System.out.println("3. 전체 회원 조회(최고 점수 내림 차순)");
					System.out.println("4. 비밀번호 변경");
					System.out.println("9. 로그아웃");
					System.out.print("메뉴 선택 >> ");
					
					sel = sc.nextInt();
					sc.nextLine();
					System.out.println();
					
					switch (sel) {
					case 1: loginView.startGame(loginMember); break;
					case 2: loginView.selectMyInfo(loginMember); break;
					case 3: loginView.selectAll(); break;
					case 4: loginView.updatePassword(loginMember); break;
					
					case 9 : System.out.println("로그아웃 되었습니다."); 
							 loginMember = null; // loginMember 필드에 아무것도 참조하고 있지 않음을 의미하는 null을 대입
							 break;
					
					default: System.out.println("잘못 입력하셨습니다. 메뉴를 다시 선택해주세요.");
					}
					
				}
				
			}catch (InputMismatchException e) {
				System.out.println("올바르지 않은 입력입니다. 다시 시도해주세요.");
				sc.nextLine(); // 버퍼에 남아있는 문자열 제거
			}
		}while(sel != 0);
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	/** [회원 가입]
	 * 아이디, 비밀번호, 이름을 입력 받아 회원 가입 진행.
	 * 이 떄, 아이디 중복 검사, 비밀번호 확인 작업 필수!
	 */
	public void signUp() { 
		System.out.println("[회원 가입]");
		
		// 입력 받은 값을 저장할 변수를 미리 선언
		String memberId = null;
		String memberPw = null;
		String memberPw2 = null;
		String memberName = null;
		
		// 중복 되지 않은 아이디가 입력 될 때 까지 무한 반복
		while(true) { 
			
			System.out.print("아이디 : ");
			memberId = sc.next();
			
			int result = mainService.duplicateCheck(memberId);
			
			if(result == 1) {
				System.out.println("이미 사용 중인 아이디 입니다. 다시 입력해주세요. \n");
			}else {
				System.out.println("사용 가능한 아이디 입니다. \n");
				break;
			}
		}

		// 비밀번호가 일치할 때 까지 무한 반복
		while(true) {
			System.out.print("비밀번호 : ");
			memberPw = sc.next();
			
			System.out.print("비밀번호 확인 : ");
			memberPw2 = sc.next();
			
			if(!memberPw.equals(memberPw2)) {
				System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n");
				continue;
			}
			
			break;
		}
		
		System.out.print("\n이름 : ");
		memberName = sc.next();
		
		// 입력 받은 정보를 하나의 Member객체에 저장
		Member mem = new Member(memberId, memberPw, memberName);
		
		// DB에 입력 받은 값을 INSERT하는 회원 가입 서비스 호출
		int result = mainService.signUp(mem);
		
		
		if(result > 0) 	System.out.println("회원 가입 성공");
		else			System.out.println("회원 가입 실패");
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	/** [로그인]
	 * 아이디, 비밀번호를 입력 받아 
	 * DB에 일치하는 회원 정보가 존재하면 조회하여 
	 * loginMember 필드에 저장
	 * 
	 * 로그인 결과에 따라 아래 내용 출력
	 * - 로그인 성공 시 : 홍길동님 환영합니다.
	 * - 로그인 실패 시 : 아이디 또는 비밀번호가 일치하지 않습니다.
	 */
	public void login() {
		System.out.println("[로그인]");
		System.out.print("아이디 : ");
		String inputId = sc.next();
		
		System.out.print("비밀번호 : ");
		String inputPw  = sc.next();
		
		Member mem = new Member();
		mem.setMemberId(inputId);
		mem.setMemberPw(inputPw);
		
		// loginMember 필드에 DB 조회 결과를 저장
		loginMember = mainService.login(mem);
		
		// 로그인 결과에 따라 내용 출력하기
		
	}
	
	
	
	
}
