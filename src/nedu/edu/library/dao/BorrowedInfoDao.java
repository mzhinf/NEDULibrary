package nedu.edu.library.dao;

import java.util.ArrayList;

import nedu.edu.library.entity.BorrowedInfo;

public interface BorrowedInfoDao {
	
	public ArrayList<BorrowedInfo> getBorrowedList(int u_id);
	
	//实现借阅功能 尝试删除预约信息 先修改BookInfo 再修改BookUniqueInfo 再创建BorrowedInfo
	public int addBorrowedInfo(int u_id, int b_id, int b_key);

	//实现续借功能
	public boolean update(int id, int u_id);
	
	//实现还书功能 先修改BookInfo 再修改BookUniqueInfo 再删除BorrowedInfo
	public int deleteBorrowedInfo(int u_id, int b_id, int b_key);
	
	//DailyTask 设置超期
	//先查询BorrowedInfo 再删除BorrowedInfo
	public boolean dailyTaskSetOverdue();
	
}
