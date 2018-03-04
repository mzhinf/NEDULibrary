package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.dao.BookReviewInfoDao;
import nedu.edu.library.entity.BookReviewInfo;

public class BookReviewInfoImpl extends BaseDao implements BookReviewInfoDao {

	public BookReviewInfoImpl() {
		// TODO Auto-generated constructor stub
	}

	//给用户获取书评
	@Override
	public ArrayList<BookReviewInfo> getBookReviewList(int b_id) {
		ArrayList<BookReviewInfo> bookReviewList = new ArrayList<BookReviewInfo>();
		String sql = "select * from bookreviewinfo where b_id = ?";
		Object[] params={b_id};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while (rs.next()){
				BookReviewInfo bookReviewInfo = new BookReviewInfo();
				bookReviewInfo.setId(rs.getInt("id"));
				bookReviewInfo.setU_id(rs.getInt("u_id"));
				bookReviewInfo.setB_id(rs.getInt("b_id"));
				bookReviewInfo.setReviewTime(rs.getTimestamp("reviewTime"));
				bookReviewInfo.setGrade(rs.getInt("grade"));
				bookReviewInfo.setBookReviewContent(rs.getString("bookReviewContent"));
				bookReviewList.add(bookReviewInfo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookReviewList;
	}

	//用户添加书评
	@Override
	public boolean addBookReview(BookReviewInfo bookReviewInfo) {
		boolean flag = false;
		String sql = "insert into bookreviewinfo(id,u_id,b_id,reviewTime,grade,bookReviewContent) values(SEQ_bookreviewinfo.nextval,?,?,sysdate,?,?)";
		Object[] params = {bookReviewInfo.getU_id(), bookReviewInfo.getB_id(), bookReviewInfo.getGrade(), bookReviewInfo.getBookReviewContent()};
		int i=this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("书评插入成功");
			flag = true;
		}
		else{
			System.out.println("书评插入失败");
		}
		return flag;
	}

}
