package nedu.edu.library.dao;

import nedu.edu.library.entity.UserInfo;
import net.sf.json.JSONObject;

public interface UserInfoDao {

	public JSONObject getUserInfo(String userid);

	public boolean add(String userid, String password);

	public boolean update(UserInfo userInfo);
	
	public boolean delete(String userid);
	
}
