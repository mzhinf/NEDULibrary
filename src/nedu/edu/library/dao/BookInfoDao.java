package nedu.edu.library.dao;

import java.util.ArrayList;

import nedu.edu.library.entity.BookInfo;

public interface BookInfoDao {
	
	public ArrayList<BookInfo> getBookList(String str,String way);
	
	public ArrayList<BookInfo> getBookList(BookInfo bookInfo);
	
	//通过id获取单条书籍信息
	public BookInfo idToBookInfo(int b_id);

	public boolean addBookInfo(BookInfo bookinfo);

	//创建借阅信息时修改BookInfo way=1 为借阅 way=2为预约 value=1为借阅或预约修改 value=-1为还书或预约修改
	public boolean updateBookInfo(int b_id, int way, int value);
	
	//修改从表BookUniqueInfo value表示借阅或还书
	public boolean updateBookUniqueInfo(int b_id, int b_key, char value);
	
	public boolean delete(String str,String way);
}
