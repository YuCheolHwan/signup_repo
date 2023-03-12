package sec01.exam01;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpMain {
	public static Scanner sc = new Scanner(System.in);
	public static final int SIGNUP = 1, SIGNIN = 2, SEARCHINFO = 3, EXIT = 4;

	public static void main(String[] args) {

		boolean run = true;
		DBConnection dbCon = new DBConnection();
		int selectNo = 0;
		int selectNo2 = 0;
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
		String formatedNow = now.format(formatter);
		while (run) {
			System.out.println("=====================================================");
			System.out.println("1. 회원가입 | 2. 로그인 | 3. 회원 정보(id, pw) 찾기 | 4. 종료");
			System.out.println("=====================================================");
			System.out.print("원하시는 서비스 번호를 입력해주세요 >>");
			selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case SIGNUP:
				// char(10)으로 id 범위를 정해놨는데 글자 수를 넘겨도 완료는 되고 데이터베이스에 저장은 안됨
				System.out.println("회원가입 시스템을 실행합니다.");
				SignUp su = new SignUp();
				su = inputData();
				int returnValue = dbCon.insert(su);
				if (returnValue == 1) {
					System.out.println("회원가입 완료");
				} else {
					System.out.println("회원가입 오류 발생. 재시도 해주십시오.");
				}
				break;
			case SIGNIN:
				boolean run2 = true;
				System.out.println("================로그인 시스템을 실행합니다.================");
				System.out.print("아이디 입력 : ");
				String signInId = sc.nextLine();
				System.out.print("패스워드 입력 : ");
				String signInPw = sc.nextLine();
				int returnValue2 = dbCon.signIn(signInId, signInPw);
				if (returnValue2 == 1) {
					System.out.println("로그인 성공");
					while (run2) {
						System.out.println("=================================================================");
						System.out.println("1. 메일 보내기 | 2. 메일 확인 | 3.회원 정보 수정 | 4. 회원 탈퇴 | 5. 로그아웃");
						System.out.println("=================================================================");
						System.out.print("원하시는 서비스 번호를 입력해주세요 >>");
						selectNo2 = Integer.parseInt(sc.nextLine());
						switch (selectNo2) {
						case 1:
							System.out.println("====================메일 보내기 시스템을 실행합니다.====================");
							System.out.print("메일 받으실 분의 아이디를 입력해주십시오 >>");
							String sendAddress = sc.nextLine();
							System.out.println("보내실 내용을 입력해주세요.(30글자 이내)");
							System.out.print(">>");
							String sendContent = sc.nextLine();
							int ruturnValue3 = dbCon.sendMail(signInId, sendAddress, sendContent, formatedNow);
							if (ruturnValue3 == 1) {
								System.out.println("메일 발송 완료");
							} else {
								System.out.println("메일 발송 실패");
							}
							break;
						case 2:
							System.out.println("받은 메일을 확인합니다.");
							ArrayList<SignUp> list = new ArrayList<>();
							list = dbCon.readMail(signInId);
							for (SignUp data : list) {
								System.out.println(data.toString2());
							}
							break;
						case 3:
							boolean run3 = true;
							String newPw = null;
							String newPwRe = null;
							while (run3) {
								System.out.println("변경하실 정보를 목록에서 선택하세요.");
								System.out.println("1. 비밀번호 | 2. 이름 | 3. 출생 월일 | 4. 거주 지역 | 5. 종료");
								System.out.print(">>");
								int no = Integer.parseInt(sc.nextLine());
								switch (no) {
								case 1:
									System.out.println("비밀번호 변경을 선택하셨습니다.");
									System.out.print("현재 비밀번호를 입력하세요 >>");
									String pw = sc.nextLine();
									if (pw.equals(signInPw)) {
										System.out.print("변경하실 ");
										newPw = matchingPwPattern();
										System.out.print("일치하는지 확인합니다. 다시 ");
										newPwRe = matchingPwPattern();
										if (newPw.equals(newPwRe)) {
											System.out.println("일치합니다.");
											int returnValue3 = dbCon.updatePw("pw", signInId, signInPw, newPw);
											if (returnValue3 != -1) {
												System.out.println("변경 되었습니다.");
												run3 = false;
												run2 = false;
											} else {
												System.out.println("변경 실패하였습니다.");
											}
										}
									} else {
										System.out.println("현재 비밀번호와 일치하지 않습니다.");
									}

									break;
								case 2:
									System.out.println("이름 변경을 선택하셨습니다.");
									System.out.print("변경하실 ");
									String newName = matchingNamePattern();
									int returnValue4 = dbCon.updateInfo("name", signInId, newName);
									if (returnValue4 != -1) {
										System.out.println("변경 되었습니다.");
									} else {
										System.out.println("변경 실패하였습니다.");
									}
									break;
								case 3:
									System.out.println("출생 월일 변경을 선택하셨습니다.");
									int birthMonth = matchingBirth("월");
									int birthDate = matchingBirth("일");
									String newSsn = birthMonth + "월 " + birthDate + "일";
									int returnValue5 = dbCon.updateInfo("ssn", signInId, newSsn);
									if (returnValue5 != -1) {
										System.out.println("변경 되었습니다.");
									} else {
										System.out.println("변경 실패하였습니다.");
									}
									break;
								case 4:
									System.out.println("거주 지역 변경을 선택하셨습니다.");
									String newAddress = matchingCityPattern();
									int returnValue6 = dbCon.updateInfo("address", signInId, newAddress);
									if (returnValue6 != -1) {
										System.out.println("변경 되었습니다.");
									} else {
										System.out.println("변경 실패하였습니다.");
									}
									break;
								case 5:
									run3 = false;
									break;

								}
							}
							run3 = true;
							System.out.println("정보 변경 시스템 종료합니다.");
							break;
						case 4:
							int returnValue5 = dbCon.deleteIdPw(signInId, signInPw);
							if (returnValue5 == 1) {
								System.out.println("회원 탈퇴 완료.");
								System.out.println("감사합니다.");
								run2 = false;
							}
							break;
						case 5:
							System.out.println("로그아웃 완료");
							run2 = false;
							break;
						}
					}
				} else {
					System.out.println("로그인 실패. 재시도 해주십시오.");
				}
				run2 = true;
				break;
			case SEARCHINFO:
				System.out.println("1. 아이디 찾기 | 2. 비밀번호 찾기");
				int selectIdPwNo = Integer.parseInt(sc.nextLine());
				switch (selectIdPwNo) {
				case 1:
					System.out.println("찾으실 아이디의 이름을 입력해주세요 >>");
					String searchName = sc.nextLine();
					String searchCompleteId = dbCon.searchInfo("id", searchName);
					if (searchCompleteId == null) {
						System.out.println("찾으시는 회원 정보는 없습니다.");
					}
					System.out.println(searchCompleteId);
					break;
				case 2:
					System.out.print("찾으실 비밀번호의 id를 입력해주세요 >>");
					String searchId = sc.nextLine();
					String searchCompletePw = dbCon.searchInfo("pw", searchId);
					System.out.println(searchCompletePw);
					break;
				}
				break;
			case EXIT:
				run = false;
				break;

			}// end of switch case
		} // end of while
		System.out.println("시스템 종료합니다. 감사합니다.");
	}// end of main

	private static SignUp inputData() {
		String name = matchingNamePattern();
		String id = matchingIdPattern();
		String pw = matchingPwPattern();
		int birthMonth = matchingBirth("월");
		int birthDate = matchingBirth("일");
		String ssn = birthMonth + "월 " + birthDate + "일";
		String address = matchingCityPattern();
		SignUp signUp = new SignUp(name, id, pw, ssn, address);
		return signUp;
	}

	private static String matchingCityPattern() {
		String city = null;
		while (true) {

			try {
				System.out.print("거주 지역(시)을 입력하세요 : ");
				city = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{2,3}$");
				Matcher matcher = pattern.matcher(city);
				if (!matcher.find()) {
					System.out.println("거주 지역 입력에 오류가 발생했습니다. 다시 재입력 요청합니다.");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력에서 오류가 발생했습니다.");
				break;
			}
		}
		return city;
	}

	private static int matchingBirth(String str) {
		boolean run = true;
		boolean run2 = true;
		int data = 0;
		int month = 0;
		int date = 0;
		while (run) {
			switch (str) {
			case "월":
				while (run2) {
					try {
						// 다른 오류들은 잡아봤지만 0000 << 이런식으로 12보다 작은 0이 반복되는건 어떻게 잡는지 아직 모르겠음.
						System.out.print("출생 월을 적어주십시오 >>");
						month = Integer.parseInt(sc.nextLine());
						Pattern pattern = Pattern.compile("^[0-9]{1,2}$");
						Matcher matcher = pattern.matcher(String.valueOf(month));
						if (!matcher.find() || month > 12) {
							System.out.println("출생 월 입력 오류입니다.");
						} else {
							return month;
						}
						run = false;

					} catch (Exception e) {
						System.out.println("입력 오류 : " + e.getMessage());
					}
				}
				break;
			case "일":
				while (run2) {
					try {
						System.out.print("출생 일을 적어주십시오 >>");
						date = Integer.parseInt(sc.nextLine());
						Pattern pattern2 = Pattern.compile("^[0-9]{1,2}$");
						Matcher matcher2 = pattern2.matcher(String.valueOf(date));
						if (!matcher2.find() || date > 31) {
							System.out.println("출생 일 입력 오류입니다.");
						} else {
							return date;
						}
						run = false;

					} catch (Exception e) {
						System.out.println("입력 오류 : " + e.getMessage());
					}
				}

				break;
			}
		}
		if (run == false) {
			data = 1;
		} else {
			data = 0;
		}

		return data;

	}

	private static String matchingPwPattern() {
		String pw = null;
		while (true) {

			try {
				System.out.print("pw를 입력하세요 : ");
				pw = sc.nextLine();
				Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*{1,12}$");
				Matcher matcher = pattern.matcher(pw);
				if (!matcher.find()) {
					System.out.println("pw 입력에 오류가 발생했습니다. 다시 재입력 요청합니다.");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력에서 오류가 발생했습니다.");
				break;
			}
		}
		return pw;
	}

	private static String matchingIdPattern() {
		String id = null;
		while (true) {

			try {
				System.out.print("id를 입력하세요 : ");
				id = sc.nextLine();
				Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*{1,10}$");
				Matcher matcher = pattern.matcher(id);
				if (!matcher.find()) {
					System.out.println("id 입력에 오류가 발생했습니다. 다시 재입력 요청합니다.");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력에서 오류가 발생했습니다.");
				break;
			}
		}
		return id;
	}

	private static String matchingNamePattern() {
		String name = null;
		while (true) {

			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.print("이름을 입력하세요 : ");
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{2,4}$");
				Matcher matcher = pattern.matcher(name);
				if (!matcher.find()) {
					System.out.println("이름 입력에 오류가 발생했습니다. 다시 재입력 요청합니다.");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력에서 오류가 발생했습니다.");
				break;
			}
		}
		return name;
	}
}// end of class
