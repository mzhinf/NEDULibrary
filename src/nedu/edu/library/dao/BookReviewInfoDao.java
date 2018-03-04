package nedu.edu.library.dao;

import java.util.ArrayList;

import nedu.edu.library.entity.BookReviewInfo;

public interface BookReviewInfoDao {

	//给用户获取书评
	public ArrayList<BookReviewInfo> getBookReviewList(int b_id);
	
	//用户添加书评
	public boolean addBookReview(BookReviewInfo bookReviewInfo);
	
}
