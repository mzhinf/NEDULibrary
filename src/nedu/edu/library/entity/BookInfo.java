package nedu.edu.library.entity;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class BookInfo {
	private int id;					//书目id
	private String title;			//书名
	private String author;			//作者
	private String publishing;		//出版社
	private String ISBN;			//ISBN
	private String subject;			//学科主题
	private String searchNumber;	//索书号
	private int amount;				//馆藏数目
	private int borrowedNumber;		//借阅数目
	private int reservationNumber;	//预约数目
	private String summary;			//简介

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSearchNumber() {
		return searchNumber;
	}

	public void setSearchNumber(String searchNumber) {
		this.searchNumber = searchNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBorrowedNumber() {
		return borrowedNumber;
	}

	public void setBorrowedNumber(int borrowedNumber) {
		this.borrowedNumber = borrowedNumber;
	}

	public int getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public BookInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public BookInfo(JSONObject bookjson) {
		try {
			id = bookjson.getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			title = bookjson.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			author = bookjson.getString("author");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			publishing = bookjson.getString("publishing");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			ISBN = bookjson.getString("ISBN");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			subject = bookjson.getString("subject");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			searchNumber = bookjson.getString("searchNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			amount = bookjson.getInt("amount");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			borrowedNumber = bookjson.getInt("borrowedNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			reservationNumber = bookjson.getInt("reservationNumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			summary = bookjson.getString("summary");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
