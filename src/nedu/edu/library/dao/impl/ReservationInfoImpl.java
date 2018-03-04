package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.dao.ReservationInfoDao;
import nedu.edu.library.entity.ReservationInfo;

public class ReservationInfoImpl extends BaseDao implements ReservationInfoDao {

	public ReservationInfoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<ReservationInfo> getReservationList(int u_id) {
		ArrayList<ReservationInfo> reservationlist = new ArrayList<ReservationInfo>();
		String sql = "select * from reservationinfo where u_id = ?";
		Object[] params={u_id};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while(rs.next()){
				ReservationInfo reservationInfo = new ReservationInfo();
				reservationInfo.setId(rs.getInt("id"));
				reservationInfo.setB_id(rs.getInt("b_id"));
				reservationInfo.setU_id(rs.getInt("u_id"));
				reservationInfo.setReservationTime(rs.getTimestamp("reservationTime"));
				reservationlist.add(reservationInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationlist;
	}

	//客户端添加预约信息 先修改BookInfo 再创建ReservationInfo
	@Override
	public boolean addReservationInfo(int u_id, int b_id) {
		//预约功能实现该时间点u_id+b_id
		boolean flag = true;
		//预约限制:预约总数不能超过5本 且不能同时预约多本同一书籍
		ArrayList<ReservationInfo> reservationList = getReservationList(u_id);
		int num = reservationList.size();
		for (ReservationInfo reservationInfo:reservationList) {
			if (reservationInfo.getB_id() == b_id) {
				flag = false;
				break;
			}
		}
		if (flag) {
			if (num < 5) {
				//修改BookInfo数据
				BookInfoImpl bookInfoImpl = new BookInfoImpl();
				flag = bookInfoImpl.updateBookInfo(b_id,2,1);
				if (flag) {
					//实现预约功能 创建预约信息
					String sql = "insert into reservationinfo(id,u_id,b_id,reservationTime) values(SEQ_reservationinfo.nextval,?,?,sysdate)";
					Object[] params = {u_id,b_id};
					int i = this.executeUpdate(sql, params);
					if(i>0){
						System.out.println("预约成功");
						flag = true;
					}
					else{
						System.out.println("预约失败->创建预约信息");
						//重新修改BookInfo数据
						bookInfoImpl.updateBookInfo(b_id,2,-1);
					}
				} else {
					System.out.println("预约失败->修改BookInfo失败");
				}
			} else {
				System.out.println("预约失败->预约数量已满");
			}
		} else {
			System.out.println("预约失败->已经预约过该书");
		}
		return flag;
	}
	
	//借阅时删除预约信息 先查询ReservationInfo 再修改BookInfo 再删除ReservationInfo
	@Override
	public boolean deleteReservationInfo(int u_id, int b_id) {
		boolean flag = true;
		//获得该用户借阅信息
		ArrayList<ReservationInfo> reservationList = getReservationList(u_id);
		for (ReservationInfo reservationInfo:reservationList) {
			if (reservationInfo.getB_id() == b_id) {
				flag = false;
				break;
			}
		}
		//flag = flase则存在该书的预约信息
		if (!flag) {
			//修改BookInfo数据
			BookInfoImpl bookInfoImpl = new BookInfoImpl();
			flag = bookInfoImpl.updateBookInfo(b_id,2,-1);
			if (flag) {
				//删除ReservationInfo
				String sql = "delete from ReservationInfo where u_id = ? and b_id = ?";
				Object[] params = {u_id,b_id};
				int i = this.executeUpdate(sql, params);
				if(i>0){
					System.out.println("删除预约信息成功");
				}
				else{
					System.out.println("删除预约信息失败");
					flag = false;
					//重新修改BookInfo数据
					bookInfoImpl.updateBookInfo(b_id,2,1);
				}
			} else {
				System.out.println("预约失败->修改BookInfo失败");
				flag = false;
			}
		} else {
			//此时返回的也是true
			System.out.println("不存在该书的预约信息");
		}
		return flag;
	}

	//先查询ReservationInfo 再修改BookInfo 再删除ReservationInfo
	@Override
	public boolean dailyTaskDeleteReservationInfo() {
		//先查询ReservationInfo
		ArrayList<ReservationInfo> reservationlist = new ArrayList<ReservationInfo>();
		String sql = "select * from ReservationInfo where reservationTime < sysdate-1";
		Object[] params={};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while(rs.next()){
				ReservationInfo reservationInfo = new ReservationInfo();
				reservationInfo.setId(rs.getInt("id"));
				reservationInfo.setB_id(rs.getInt("b_id"));
				reservationInfo.setU_id(rs.getInt("u_id"));
				reservationInfo.setReservationTime(rs.getTimestamp("reservationTime"));
				reservationlist.add(reservationInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//根据ReservationInfo 先修改BookInfo 再删除ReservationInfo
		boolean flag = true;
		String deleteSql = "delete from ReservationInfo where id = ?";
		for(int i = 0; i < reservationlist.size(); i++){
			Object[] par={reservationlist.get(i).getId()};
			int f = this.executeUpdate(deleteSql, par);
			if(f>0){
				//修改BookInfo数据
				BookInfoImpl bookInfoImpl = new BookInfoImpl();
				flag = bookInfoImpl.updateBookInfo(reservationlist.get(i).getB_id(),2,-1);
				if (flag) {
					System.out.println("DailyTask->删除预约成功");
				} else {
					System.out.println("DailyTask->删除预约失败->修改BookInfo失败");
					flag = false;
				}
			} else {
				System.out.println("DailyTask->删除预约失败->删除ReservationInfo失败");
				flag = false;
			}
		}
		if (reservationlist.size() == 0) {
			System.out.println("DailyTask->没有预约信息");
			flag = false;
		}
		return flag;
	}

}
