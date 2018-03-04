package nedu.edu.library.dao;

import java.util.ArrayList;

import nedu.edu.library.entity.ReservationInfo;

public interface ReservationInfoDao {

	public ArrayList<ReservationInfo> getReservationList(int u_id);
	
	//客户端添加预约信息 先修改BookInfo 再创建ReservationInfo
	public boolean addReservationInfo(int u_id, int b_id);
	
	//没有update 预约信息不需要更新 一次 预约可持续一天
	
	//借阅时删除预约信息 先查询ReservationInfo 再修改BookInfo 再删除ReservationInfo
	public boolean deleteReservationInfo(int u_id, int b_id);
	
	//DailyTask 清除ReservationInfo
	//先查询ReservationInfo 再修改BookInfo 再删除ReservationInfo
	public boolean dailyTaskDeleteReservationInfo();
}
