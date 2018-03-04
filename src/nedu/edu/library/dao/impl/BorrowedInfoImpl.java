package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.dao.BorrowedInfoDao;
import nedu.edu.library.entity.BorrowedInfo;

public class BorrowedInfoImpl extends BaseDao implements BorrowedInfoDao {

	public BorrowedInfoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<BorrowedInfo> getBorrowedList(int u_id) {
		ArrayList<BorrowedInfo> borrowedlist = new ArrayList<BorrowedInfo>();
		String sql = "select * from borrowedinfo where u_id = ?";
		Object[] params={u_id};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while(rs.next()){
				BorrowedInfo borrowedinfo = new BorrowedInfo();
				borrowedinfo.setId(rs.getInt("id"));
				borrowedinfo.setB_id(rs.getInt("b_id"));
				borrowedinfo.setB_key(rs.getInt("b_key"));
				borrowedinfo.setU_id(rs.getInt("u_id"));
				borrowedinfo.setBorrowedTime(rs.getTimestamp("borrowedTime"));
				if(rs.getString("isRenew").charAt(0) == 't') borrowedinfo.setRenew(true);
				else borrowedinfo.setRenew(false);
				if(rs.getString("isOverdue").charAt(0) == 't') borrowedinfo.setOverdue(true);
				else borrowedinfo.setOverdue(false);
				borrowedlist.add(borrowedinfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowedlist;
	}
	
	//实现借阅功能 尝试删除预约信息 先修改BookInfo 再修改BookUniqueInfo 再创建BorrowedInfo
	@Override
	public int addBorrowedInfo(int u_id, int b_id, int b_key) {
		boolean flag = true;
		int error = 300;
		//删除预约信息
		ReservationInfoImpl reservationInfoImpl = new ReservationInfoImpl();
		reservationInfoImpl.deleteReservationInfo(u_id, b_id);
		//借阅限制:借阅总数不能超过5本
		ArrayList<BorrowedInfo> borrowedList = getBorrowedList(u_id);
		int num = borrowedList.size();
		//检查该借阅者是否有超期图书
		for (BorrowedInfo borrowedInfo:borrowedList){
			if (borrowedInfo.isOverdue()) {
				error = 301;
				break;
			}
		}
		if (error == 300) {
			if (num < 5) {//借阅数量未满
				//修改BookInfo数据
				BookInfoImpl bookInfoImpl = new BookInfoImpl();
				flag = bookInfoImpl.updateBookInfo(b_id,1,1);
				if (flag) {
					//再修改BookUniqueInfo
					flag = bookInfoImpl.updateBookUniqueInfo(b_id, b_key, 't');
					if (flag) {
						//实现借阅功能 创建借阅信息
						String sql = "insert into borrowedinfo (id,u_id,b_id,b_key,borrowedTime,isRenew,isOverdue) values(SEQ_borrowedinfo.nextval,?,?,?,sysdate,'t','f')";
						Object[] params = {u_id,b_id,b_key};
						int i = this.executeUpdate(sql, params);
						if(i>0){
							System.out.println("借阅成功");
							flag = true;
						} else {
							System.out.println("借阅失败->创建借阅信息");
							error = 305;
							//重新修改BookInfo数据
							bookInfoImpl.updateBookInfo(b_id,1,-1);
							//重新修改BookUniqueInfo
							bookInfoImpl.updateBookUniqueInfo(b_id, b_key, 'f');
						}
					} else {
						System.out.println("借阅失败->修改BookUniqueInfo失败");
						error = 304;
						//重新修改BookInfo数据
						bookInfoImpl.updateBookInfo(b_id,1,-1);
					}
				} else {
					System.out.println("借阅失败->修改BookInfo失败");
					error = 303;
				}
			} else {//借阅数量已满 无法借阅
				System.out.println("借阅失败->借阅数量已满");
				error = 302;
			}
		} else {
			System.out.println("借阅失败->存在超期图书!请及时归还!");
		}
		return error;
	}

	//实现续借功能
	@Override
	public boolean update(int id, int u_id) {
		boolean flag = true;
		ArrayList<BorrowedInfo> borrowedList = getBorrowedList(u_id);
		//检查该借阅者是否有超期图书
		for (BorrowedInfo borrowedInfo:borrowedList){
			if (borrowedInfo.isOverdue()) {
				flag = false;
				break;
			}
		}
		if (flag) {
			String sql = "update borrowedinfo set borrowedTime = sysdate , isRenew = 'f' where id = ?";
			Object[] params={id};
			int i=this.executeUpdate(sql, params);
			if (i > 0) {
				System.out.println("续借成功");
			} else {
				System.out.println("续借失败");
				flag = false;
			}
		} else {
			System.out.println("存在超期图书!请及时归还!续借失败!");
		}
		return flag;
	}

	//实现还书功能 先修改BookInfo 再修改BookUniqueInfo 再删除BorrowedInfo
	@Override
	public int deleteBorrowedInfo(int u_id, int b_id, int b_key) {
		boolean flag = false;
		int error = 310;
		if (u_id < 100) {
			//修改BookInfo数据
			BookInfoImpl bookInfoImpl = new BookInfoImpl();
			flag = bookInfoImpl.updateBookInfo(b_id,1,-1);
			if (flag) {
				//再修改BookUniqueInfo
				flag = bookInfoImpl.updateBookUniqueInfo(b_id, b_key, 'f');
				if (flag) {
					String sql = "delete from borrowedinfo where b_id = ? and b_key = ?";
					Object[] params={b_id,b_key};
					int i = this.executeUpdate(sql, params);
					if (i > 0) {
						System.out.println("还书成功");
					} else {
						System.out.println("还书失败->删除BorrowedInfo失败");
						error = 313;
						//重新修改BookInfo数据
						bookInfoImpl.updateBookInfo(b_id,1,1);
						//重新修改BookUniqueInfo
						bookInfoImpl.updateBookUniqueInfo(b_id, b_key, 't');
					}
				} else {
					System.out.println("还书失败->修改BookUniqueInfo失败");
					error = 312;
					//重新修改BookInfo数据
					bookInfoImpl.updateBookInfo(b_id,1,1);
				}
			} else {
				System.out.println("还书失败->修改BookInfo失败");
				error = 311;
			}
		} else {
			System.out.println("还书失败->还书权限不足");
			error = 314;
		}
		return error;
	}
	
	//DailyTask 设置超期
	public boolean dailyTaskSetOverdue() {
		//UPDATE BORROWEDINFO SET ISOVERDUE = 't' WHERE BORROWEDTIME+30 < SYSDATE
		boolean flag = false;
		String sql = "UPDATE BORROWEDINFO SET ISOVERDUE = 't' WHERE BORROWEDTIME+30 < SYSDATE";
		Object[] params={};
		int i=this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("DailyTask->更新超期成功");
		} else {
			System.out.println("DailyTask->更新超期失败(没有借阅超期)");
		}
		return flag;
	}

	public static void main(String[] agrs){
		BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
		ArrayList<BorrowedInfo> b = borrowedInfoImpl.getBorrowedList(5);
		for(BorrowedInfo borrowedInfo:b){
			System.out.println(borrowedInfo.getId());
			System.out.println(borrowedInfo.getU_id());
			System.out.println(borrowedInfo.getB_id());
			System.out.println(borrowedInfo.getBorrowedTime());
			System.out.println(borrowedInfo.isRenew());
			System.out.println(borrowedInfo.isOverdue());
		}
	}

}
