package sec01.exam01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
	// 멤버 변수
	private Connection connection = null;

	// 생성자 : 디폴트
	// 멤버 함수
	public void connect() {
		// 1. 외부에서 데이터베이스를 접속 할 수 있도록 설정
				Properties properties = new Properties();
				FileInputStream fis = null;
				// 2. db.properties 파일 로드
				try {
					fis = new FileInputStream("C:/202302javaWorkspace/SignUp/src/sec01/exam01/db.properties");
					properties.load(fis);
				} catch (FileNotFoundException e) {
					System.out.println("FileInputStream error : " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Properties.load error : " + e.getMessage());
				}
				// 2. 내부적으로 JDBC DriverManager 통해서 DB와 연결을 가져온다.
				try {
					// 1. jdbc 클래스 로드
					Class.forName(properties.getProperty("driverName"));
					// 2. mysql DB 연결
					connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
							properties.getProperty("password"));
				} catch (ClassNotFoundException e) {
					System.out.println("데이터베이스 로드오류" + e.getStackTrace());
				} catch (SQLException e) {
					System.out.println("데이터베이스 연결오류" + e.getStackTrace());
				}
			}

	public int insert(SignUp su) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "insert into signupTBL values(?, ?, ?, ?, ?);";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, su.getName());
			ps.setString(2, su.getId());
			ps.setString(3, su.getPw());
			ps.setString(4, su.getSsn());
			ps.setString(5, su.getAddress());
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("insert error" + e.getMessage());
		}
		if (returnValue == 1) {
			String query2 = "create table if not exists " + su.getId() + su.getPw()
					+ "TBL(	num int unsigned auto_increment,\r\n" + "    sendtime char(20) not null,\r\n"
					+ "	senderid char(15) not null,\r\n" + "	recipientid char(15) not null,\r\n"
					+ "    contents char(30) not null,\r\n" + "    primary key(num)\r\n" + ");";
			try {
				ps = connection.prepareStatement(query2);
				ps.executeUpdate();
				if (ps != null) {
					returnValue = 1;
				}

			} catch (SQLException e) {
				System.out.println("make tables error : " + e.getMessage());
			}
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue;
	}

	public int signIn(String signInId, String signInPw) {
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int returnValue = -1;
		String query = "select * from signupTBL;";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				if (id.equals(signInId) && pw.equals(signInPw)) {
					returnValue = 1;
				}
			}
		} catch (Exception e) {
			System.out.println("signin error : " + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue;
	}

	public int sendMail(String signInId, String sendAddress, String sendContent, String formatedNow) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		ResultSet rs = null;
		String pw = null;
		String query1 = "select pw from signupTBL where id= '" + sendAddress + "';";
		try {

			ps = connection.prepareStatement(query1);
			rs = ps.executeQuery();
			if (rs.next()) {
				pw = rs.getString("pw");
			}
			String query2 = "insert into " + sendAddress + pw + "TBL values(null, ?, ?, ?, ?);";
			ps = connection.prepareStatement(query2);
			ps.setString(1, formatedNow);
			ps.setString(2, signInId);
			ps.setString(3, sendAddress);
			ps.setString(4, sendContent);
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("insert error" + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue;
	}

	public ArrayList<SignUp> readMail(String signInId) {
		ArrayList<SignUp> list = new ArrayList<>();
		this.connect();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		String pw = null;
		String query1 = "select pw from signupTBL where id= ?;";

		try {
			ps = connection.prepareStatement(query1);
			ps.setString(1, signInId);
			rs = ps.executeQuery();
			if (rs.next()) {
				pw = rs.getString("pw");
			}
			String query2 = "select * from " + signInId + pw + "TBL;";
			ps2 = connection.prepareStatement(query2);
			rs = ps2.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int num = rs.getInt("num");
				String sendTime = rs.getString("sendtime");
				String sender = rs.getString("senderid");
				String recipient = rs.getString("recipientid");
				String content = rs.getString("contents");
				list.add(new SignUp(num, sendTime, sender, recipient, content));

			}
		} catch (Exception e) {
			System.out.println("insert 오류 발생 : " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류 : " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				System.out.println(" 오류 : " + e.getMessage());
			}
		}
		return list;
	}

	public int deleteIdPw(String signInId, String signInPw) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query1 = "drop table " + signInId + signInPw + "TBL;";
		String query2 = "delete from signupTBL where id = '" + signInId + "';";
		try {
			ps = connection.prepareStatement(query1);
			returnValue = ps.executeUpdate();
			ps = connection.prepareStatement(query2);
			returnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("insert error" + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue;
	}

	public int updatePw(String type, String signInId, String signInPw, String newPw) {
		this.connect();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		int returnValue = -1;
		int returnValue2 = -1;
		String query = "UPDATE signupTBL SET " + type + " = ? WHERE id = ?;";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, newPw);
			ps.setString(2, signInId);
			returnValue = ps.executeUpdate();
			String query2 = "ALTER TABLE " + signInId + signInPw + "tbl RENAME " + signInId + newPw + "tbl;";
			ps2 = connection.prepareStatement(query2);
			if (returnValue != -1) {
				returnValue2 = ps2.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("update error : " + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
			if (ps2 != null) {
				ps2.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue2;
	}

	public String searchInfo(String type, String searchId) {
		this.connect();
		PreparedStatement ps = null;
		String returnInfo = null;
		String searchType = null;
		if (type.equals("id")) {
			searchType = "name";
		} else if (type.equals("pw")) {
			searchType = "id";
		}
		String query = "select " + type + " from signupTBL where " + searchType + "= '" + searchId + "';";
		try {
			ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				returnInfo = rs.getString(type);
			}
		} catch (Exception e) {
			System.out.println("signin error : " + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnInfo;
	}

	public int updateInfo(String type, String signInId, String newInfo) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "UPDATE signupTBL SET " + type + " = ? WHERE id = ?;";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, newInfo);
			ps.setString(2, signInId);
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("update error : " + e.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			System.err.println("ps close error" + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("connection close error" + e.getMessage());
		}
		return returnValue;
	}

}
