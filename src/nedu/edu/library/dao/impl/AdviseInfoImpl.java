package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import nedu.edu.library.dao.AdviseInfoDao;
import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.entity.AdviseInfo;

public class AdviseInfoImpl extends BaseDao implements AdviseInfoDao {

	public AdviseInfoImpl() {
		// TODO Auto-generated constructor stub
	}

	//way=1 全表搜索 way=2 搜索未查看 way=3 搜索已查看
	@Override
	public ArrayList<AdviseInfo> getAdviseInfoList(int way) {
		ArrayList<AdviseInfo> adviseList = new ArrayList<AdviseInfo>();
		String sql = "select * from adviseinfo ";
		if (way == 1) {
			sql = sql + "ORDER BY adviseTime desc";
		} else if (way == 2) {
			sql = sql + "where isCheck = 'f' ORDER BY adviseTime desc";
		} else if (way == 3) {
			sql = sql + "where isCheck = 't' ORDER BY adviseTime desc";
		}
		Object[] params={};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while (rs.next()) {
				AdviseInfo adviseInfo = new AdviseInfo();
				adviseInfo.setId(rs.getInt("id"));
				if(rs.getString("isCheck").charAt(0) == 't') adviseInfo.setCheck(true);
				else adviseInfo.setCheck(false);
				adviseInfo.setAdviseTime(rs.getTimestamp("adviseTime"));
				adviseInfo.setU_id(rs.getInt("u_id"));
				adviseInfo.setAdvise(rs.getString("advise"));
				adviseInfo.setEmail(rs.getString("email"));
				adviseList.add(adviseInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adviseList;
	}

	@Override
	public boolean addAdviseInfo(AdviseInfo adviseInfo) {
		boolean flag = true;
		String sql = "insert into adviseinfo(id,u_id,isCheck,adviseTime,advise,email) values(SEQ_adviseinfo.nextval,?,'f',sysdate,?,?)";
		Object[] params = {adviseInfo.getU_id(),adviseInfo.getAdvise(),adviseInfo.getEmail()};
		int i = this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("添加建议成功");
		} else {
			System.out.println("添加建议失败");
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean updateAdviseInfo(int id) {
		boolean flag = true;
		String sql = "update adviseinfo set isCheck = 't' where id = ? and isCheck = 'f'";
		Object[] params = {id};
		int i = this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("修改查看建议成功");
		} else {
			System.out.println("修改查看建议失败->不存在该id或者已经修改过");
			flag = false;
		}
		return flag;
	}

}
