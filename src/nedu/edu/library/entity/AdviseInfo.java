package nedu.edu.library.entity;

import java.sql.Timestamp;

public class AdviseInfo {
	
	private int id;					//建议id
	private boolean isCheck;		//是否查看过
	private Timestamp adviseTime;	//建议日期
	private int u_id;				//用户id
	private String advise;			//建议
	private String email;			//反馈邮箱

	public AdviseInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public Timestamp getAdviseTime() {
		return adviseTime;
	}

	public void setAdviseTime(Timestamp adviseTime) {
		this.adviseTime = adviseTime;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
