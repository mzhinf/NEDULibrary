package nedu.edu.library.entity;

import java.sql.Timestamp;

public class ReservationInfo {
	private int id;						//预约id
	private int b_id;					//BookInfo id
	private int u_id;					//UserInfo id
	private Timestamp reservationTime;	//预约时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public Timestamp getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Timestamp reservationTime) {
		this.reservationTime = reservationTime;
	}

	public ReservationInfo() {
		// TODO Auto-generated constructor stub
	}

}
