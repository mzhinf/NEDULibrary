package nedu.edu.library.entity;

import java.sql.Timestamp;

public class BorrowedInfo {
	private int id;						//借阅id
	private int b_id;					//BookInfo id
	private int b_key;					//BookInfo key
	private int u_id;					//UserInfo id
	private Timestamp borrowedTime;		//借阅时间
	private boolean isRenew;			//是否续借 true可以续借 false无法续借
	private boolean isOverdue;			//是否超期 true超期 false未超期
	
	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getB_key() {
		return b_key;
	}

	public void setB_key(int b_key) {
		this.b_key = b_key;
	}

	public Timestamp getBorrowedTime() {
		return borrowedTime;
	}

	public void setBorrowedTime(Timestamp borrowedTime) {
		this.borrowedTime = borrowedTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOverdue() {
		return isOverdue;
	}

	public void setOverdue(boolean overdue) {
		isOverdue = overdue;
	}

	public boolean isRenew() {
		return isRenew;
	}

	public void setRenew(boolean renew) {
		isRenew = renew;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public BorrowedInfo() {
		// TODO Auto-generated constructor stub
	}

}
