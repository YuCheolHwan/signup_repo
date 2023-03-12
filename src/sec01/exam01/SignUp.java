package sec01.exam01;

import java.util.Objects;


public class SignUp {
	private String name;
	private String id;
	private String pw;
	private String signInId;
	private String signInPw;
	private String ssn;
	private String address;
	private int num;
	private String sender;
	private String recipient;
	private String content;
	private String sendTime;

	public SignUp() {
	}

	public SignUp(String name) {
		this(name, null, null, null, null);
	}

	public SignUp(String id, String pw) {
		this(null, id, pw, null, null);
	}

	public SignUp(String name, String id, String pw) {
		this(name, id, pw, null, null);
	}

	public SignUp(String name, String id, String pw, String ssn, String address) {
		super();
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.ssn = ssn;
		this.address = address;
	}

	public SignUp(int num, String sendTime, String sender, String recipient, String content) {
		super();
		this.num = num;
		this.sendTime = sendTime;
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(int sendTime) {
		this.num = sendTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getSignInId() {
		return signInPw;
	}

	public String getSignInPw() {
		return signInPw;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(signInId, signInPw);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignUp other = (SignUp) obj;
		return Objects.equals(signInId, other.signInId) && Objects.equals(signInPw, other.signInPw);
	}

	@Override
	public String toString() {
		return "SignUp [name=" + name + ", id=" + id + ", pw=" + pw + ", ssn=" + ssn + ", address=" + address + "]";
	}

	public String toString2() {
		return num + "\t | \t" + sendTime + "\t | \t" + sender + "\t | \t" + recipient + "\t | \t" + content;
	}

}
