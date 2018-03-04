package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.dao.UserInfoDao;
import nedu.edu.library.entity.UserInfo;
import net.sf.json.JSONObject;

public class UserInfoImpl extends BaseDao implements UserInfoDao{

	public UserInfoImpl() {
	}
	
	@Override
	public JSONObject getUserInfo(String userid) {
		JSONObject userjson = null;
		try {
			String sql = "select * from userinfo where userid = ?";
			Object[] params={userid};
			ResultSet rs = this.executeSQL(sql,params);
			if(rs.next()){
				userjson = new JSONObject();
				userjson.put("id", rs.getInt("id"));
				userjson.put("userid", rs.getString("userid"));
				userjson.put("password", rs.getString("password"));
				userjson.put("name", rs.getString("name"));
				userjson.put("sex", rs.getString("sex"));
				userjson.put("phone", rs.getString("phone"));
				userjson.put("email", rs.getString("email"));
				userjson.put("department", rs.getString("department"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userjson;
	}

	@Override
	public boolean add(String userid, String password) {
		boolean flag = false;
		String sql = "insert into userinfo(id,userid,password) values(SEQ_userinfo.nextval,?,?)";
		Object[] params = {userid,password};
		int i=this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("用户信息插入成功");
			flag = true;
		}
		else{
			System.out.println("用户信息插入失败");
		}
		return flag;
	}
	
	@Override
	public boolean update(UserInfo userInfo) {//字符需要特殊处理
		boolean flag = false;
		String sql = "update userinfo set password = ?, name = ?, sex = ?, phone = ?, email = ?, department = ? where id = ?";
		Object[] params = {userInfo.getPassword(),userInfo.getName(),userInfo.getSex() == 0 ? null : String.valueOf(userInfo.getSex()),userInfo.getPhone(),userInfo.getEmail(),userInfo.getDepartment(),userInfo.getId()};
		int i=this.executeUpdate(sql, params);
		
		if(userInfo.getSex()==0){
			System.out.println(userInfo.getId());
			System.out.println(String.valueOf('m'));
		}
		
		if(i>0){
			System.out.println("用户更新成功");
			flag = true;
		}
		else{
			System.out.println("用户更新失败");
		}
		return flag;
	}

	@Override
	public boolean delete(String userid) {
		return false;
	}
	
	public String idGetUserId(int id) {
		String userid = null;
		try {
			String sql = "select userid from userinfo where id = ?";
			Object[] params={id};
			ResultSet rs = this.executeSQL(sql,params);
			if(rs.next()){
				userid = rs.getString("userid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userid;
	}
}
