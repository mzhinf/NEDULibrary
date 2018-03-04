package nedu.edu.library.util;

import nedu.edu.library.dao.impl.BorrowedInfoImpl;
import nedu.edu.library.dao.impl.ReservationInfoImpl;

/**
 * @author 计机142 马泽华
 * 完成每日检测任务
 */
public class DailyTask {

	public DailyTask() {
		// TODO Auto-generated constructor stub
	}
	
	public static int dailyTask(){//返回三位数1ab a代表更新借阅 b代表更新预约
		int resCode = 1;
		BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
		if(borrowedInfoImpl.dailyTaskSetOverdue()) {
			resCode = resCode * 10 + 1;
		} else {
			resCode = resCode * 10;
		}
		ReservationInfoImpl reservationInfoImpl = new ReservationInfoImpl();
		if(reservationInfoImpl.dailyTaskDeleteReservationInfo()) {
			resCode = resCode * 10 + 1;
		} else {
			resCode = resCode * 10;
		}
		return resCode;
	}
	
}
