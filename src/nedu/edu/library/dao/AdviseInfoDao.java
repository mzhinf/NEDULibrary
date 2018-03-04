package nedu.edu.library.dao;

import java.util.ArrayList;

import nedu.edu.library.entity.AdviseInfo;

public interface AdviseInfoDao {

	//way=1 全表搜索 way=2 搜索未查看 way=3 搜索已查看
	public ArrayList<AdviseInfo> getAdviseInfoList(int way);

	public boolean addAdviseInfo(AdviseInfo adviseInfo);

	//查看过则更新信息
	public boolean updateAdviseInfo(int id);
	
}
