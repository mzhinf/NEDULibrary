package nedu.edu.library.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nedu.edu.library.dao.BaseDao;
import nedu.edu.library.dao.BookInfoDao;
import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.qrcode.QRCodeUtil;
import nedu.edu.library.util.ToolUtil;
import net.sf.json.JSONObject;

public class BookInfoImpl extends BaseDao implements BookInfoDao {

	public BookInfoImpl() {
		
	}

	@Override
	public ArrayList<BookInfo> getBookList(String str, String way) {
		ArrayList<BookInfo> booklist = new ArrayList<BookInfo>();
		String sql = "select * from bookinfo where "+way+" like ?";
		Object[] params={"%"+str+"%"};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while(rs.next()){
				BookInfo bookinfo = new BookInfo();
				bookinfo.setId(rs.getInt("id"));
				bookinfo.setTitle(rs.getString("title"));
				bookinfo.setAuthor(rs.getString("author"));
				bookinfo.setPublishing(rs.getString("publishing"));
				bookinfo.setISBN(rs.getString("ISBN"));
				bookinfo.setSubject(rs.getString("subject"));
				bookinfo.setSearchNumber(rs.getString("searchNumber"));
				bookinfo.setAmount(rs.getInt("amount"));
				bookinfo.setBorrowedNumber(rs.getInt("borrowedNumber"));
				bookinfo.setReservationNumber(rs.getInt("reservationNumber"));
				bookinfo.setSummary(rs.getString("summary"));
				booklist.add(bookinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booklist;
	}
	
	//
	@Override
	public ArrayList<BookInfo> getBookList(BookInfo bookInfo) {
		//select * from bookinfo where  title like '%嵌入%'and author like '%王%'and ISBN like '%9%'and publishing like '%清华%'and subject like '%1%'and searchNumber like '%1%'
		ArrayList<BookInfo> bookList = new ArrayList<BookInfo>();
		String sql = "select * from bookinfo where ";
		boolean flag = false;
		if (bookInfo.getTitle() != null && !bookInfo.getTitle().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " title like '%" + bookInfo.getTitle() + "%'"; flag = true;
		}
		if (bookInfo.getAuthor() != null && !bookInfo.getAuthor().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " author like '%" + bookInfo.getAuthor() + "%'"; flag = true;
		}
		if (bookInfo.getISBN() != null && !bookInfo.getISBN().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " ISBN like '%" + bookInfo.getISBN() + "%'"; flag = true;
		}
		if (bookInfo.getPublishing() != null && !bookInfo.getPublishing().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " publishing like '%" + bookInfo.getPublishing() + "%'"; flag = true;
		}
		if (bookInfo.getSubject() != null && !bookInfo.getSubject().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " subject like '%" + bookInfo.getSubject() + "%'"; flag = true;
		}
		if (bookInfo.getSearchNumber() != null && !bookInfo.getSearchNumber().equals("")) {
			if (flag) sql = sql + "and";
			sql = sql + " searchNumber like '%" + bookInfo.getSearchNumber() + "%'"; flag = true;
		}
		System.out.println(sql);
		if (flag) {//可以开始搜索
			Object[] params={};
			ResultSet rs = this.executeSQL(sql,params);
			try {
				while(rs.next()){
					BookInfo bookinfo = new BookInfo();
					bookinfo.setId(rs.getInt("id"));
					bookinfo.setTitle(rs.getString("title"));
					bookinfo.setAuthor(rs.getString("author"));
					bookinfo.setPublishing(rs.getString("publishing"));
					bookinfo.setISBN(rs.getString("ISBN"));
					bookinfo.setSubject(rs.getString("subject"));
					bookinfo.setSearchNumber(rs.getString("searchNumber"));
					bookinfo.setAmount(rs.getInt("amount"));
					bookinfo.setBorrowedNumber(rs.getInt("borrowedNumber"));
					bookinfo.setReservationNumber(rs.getInt("reservationNumber"));
					bookinfo.setSummary(rs.getString("summary"));
					bookList.add(bookinfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("没有搜索条件!");
		}
		return bookList;
	}

	//通过id获取单条书籍信息
	@Override
	public BookInfo idToBookInfo(int b_id) {
		BookInfo bookinfo = new BookInfo();
		String sql = "select * from bookinfo where id = ?";
		Object[] params={String.valueOf(b_id)};
		ResultSet rs = this.executeSQL(sql,params);
		try {
			while(rs.next()){
				bookinfo.setId(rs.getInt("id"));
				bookinfo.setTitle(rs.getString("title"));
				bookinfo.setAuthor(rs.getString("author"));
				bookinfo.setPublishing(rs.getString("publishing"));
				bookinfo.setISBN(rs.getString("ISBN"));
				bookinfo.setSubject(rs.getString("subject"));
				bookinfo.setSearchNumber(rs.getString("searchNumber"));
				bookinfo.setAmount(rs.getInt("amount"));
				bookinfo.setBorrowedNumber(rs.getInt("borrowedNumber"));
				bookinfo.setReservationNumber(rs.getInt("reservationNumber"));
				bookinfo.setSummary(rs.getString("summary"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookinfo;
	}

	//在表BookInfo生成书目信息
	//在表BookUniqueInfo生成每一本书的唯一标识
	@Override
	public boolean addBookInfo(BookInfo bookinfo) {
		boolean flag = true;
		//在表BookInfo生成书目信息
		String sql = "insert into bookinfo(id,title,author,publishing,ISBN,subject,searchNumber,amount,borrowedNumber,reservationNumber,summary) values(SEQ_Library.nextval,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {bookinfo.getTitle(),bookinfo.getAuthor(),bookinfo.getPublishing(),bookinfo.getISBN(),bookinfo.getSubject(),bookinfo.getSearchNumber(),bookinfo.getAmount(),bookinfo.getBorrowedNumber(),bookinfo.getReservationNumber(),bookinfo.getSummary()};
		int i=this.executeUpdate(sql, params);
		if(i>0){
			System.out.println("图书信息插入成功");
			//在表BookUniqueInfo生成每一本书的唯一标识
			String tsql = "insert into bookuniqueinfo(id,b_id,b_key,isBorrowed) values(SEQ_bookuniqueinfo.nextval,(SELECT id FROM BOOKINFO WHERE ISBN = ?),?,'f')";
			for (int j = 1; j <= bookinfo.getAmount(); j++) {
				Object[] tparams = {bookinfo.getISBN(),j};
				int k = this.executeUpdate(tsql, tparams);
				if (k > 0) {
					System.out.println("创建" + j + "标识成功");
					//生成对应的QRCode
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("ISBN", bookinfo.getISBN());
					jsonObject.put("code", j);
					String str = jsonObject.toString();
					String file = bookinfo.getISBN() + "-" + j + ".jpg";
					try {
						QRCodeUtil.encode(str, "", ToolUtil.DATA_PATH + "\\bookQRcode",true,file);
						System.out.println("创建" + file + "QRCode成功");
					} catch (Exception e) {
						System.out.println("创建" + file + "QRCode失败");
						e.printStackTrace();
					}
				} else {
					System.out.println("创建" + j + "标识失败");
					flag = false;
					break;
				}
			}
		}
		else{
			System.out.println("图书信息插入失败");
			flag = false;
		}
		return flag;
	}

	//创建借阅信息时修改BookInfo way=1 为借阅 way=2为预约 value=1为借阅或预约修改 value=-1为还书或预约修改
	@Override
	public boolean updateBookInfo(int b_id, int way, int value) {
		boolean flag = false;
		//先通过b_id 获取该书籍信息 获取书籍数目 进行下一步判断
		BookInfo bookInfo = idToBookInfo(b_id);	
		int available  = bookInfo.getAmount() - bookInfo.getBorrowedNumber() - bookInfo.getReservationNumber();
		if (available > 0 || value == -1) {//存在可借副本 或者为还书或取消预约
			//创建sql语句与params
			String sql;
			Object[] params = null;
			if (way == 1){
				sql = "update bookinfo set "+ "borrowedNumber" +"=? where id = ?";
				params = new Object[]{bookInfo.getBorrowedNumber()+value,b_id};
			} else {
				sql = "update bookinfo set "+ "reservationNumber" +"=? where id = ?";
				params = new Object[]{bookInfo.getReservationNumber()+value,b_id};
			}
			int i=this.executeUpdate(sql, params);
			if (i > 0) {
				System.out.println("书籍信息更新成功");
				flag = true;
			} else {
				System.out.println("书籍信息更新失败");
			}
		} else {
			System.out.println("没有可借(预约)副本");
		}
		return flag;
	}
	
	//修改从表BookUniqueInfo value表示借阅或还书
	@Override
	public boolean updateBookUniqueInfo(int b_id, int b_key, char value) {
		boolean flag = true;
		String sql = "update bookuniqueinfo set isborrowed = ? where b_id = ? and b_key = ? and isborrowed = ?";
		System.out.println(b_id + " " + b_key + " " + String.valueOf(value == 't' ? 'f' : 't'));
		Object[] params = {String.valueOf(value),b_id,b_key,String.valueOf(value == 't' ? 'f' : 't')};
		int i = this.executeUpdate(sql, params);
		if(i > 0){
			System.out.println("BookUniqueInfo更新成功");
		} else {
			System.out.println("BookUniqueInfo更新失败(借阅过该书)");
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean delete(String str, String way) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] agrs){
		BookInfoImpl book = new BookInfoImpl();
		/*
		ArrayList<BookInfo> b = book.getBookList("1","subject");
		for(BookInfo bookinfo:b){
			System.out.println(bookinfo.getTitle());
			System.out.println(bookinfo.getPublishing());
		}
		*/
		BookInfo bookinfo = new BookInfo();
		bookinfo.setTitle("test");
		bookinfo.setAuthor("admin");
		bookinfo.setPublishing("Test出版社");
		bookinfo.setISBN("GGWP-666-GGWP");
		bookinfo.setSubject("1");
		bookinfo.setSearchNumber("GGWP-666");
		bookinfo.setAmount(6);
		bookinfo.setBorrowedNumber(0);
		bookinfo.setReservationNumber(0);
		bookinfo.setSummary("test");
		book.addBookInfo(bookinfo);
	}
}
