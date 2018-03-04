package nedu.edu.library.util;

import java.util.ArrayList;

import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.entity.BookReviewInfo;
import nedu.edu.library.entity.BorrowedInfo;
import nedu.edu.library.entity.ReservationInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InfoUtil {

	public InfoUtil() {
		// TODO Auto-generated constructor stub
	}
	
	//书目信息包装成JSONArray 分页包装
	public static JSONArray BookInfoToJSONArray(ArrayList<BookInfo> booklist,int page){
		JSONArray jsonArray = new JSONArray();
		for(int i = page*10; i < booklist.size() && i < page*10+10; i++){
			jsonArray.add(InfoUtil.BookInfoToJSONObject(booklist.get(i)));
		}
		return jsonArray;
	}
	
	//书目信息包装成JSONObject
	public static JSONObject BookInfoToJSONObject(BookInfo bookinfo){
		JSONObject newsJson = new JSONObject();
		newsJson.put("id", bookinfo.getId());
		newsJson.put("title", bookinfo.getTitle());
		newsJson.put("author", bookinfo.getAuthor());
		newsJson.put("publishing", bookinfo.getPublishing());
		newsJson.put("ISBN", bookinfo.getISBN());
		newsJson.put("subject", bookinfo.getSubject());
		newsJson.put("searchNumber", bookinfo.getSearchNumber());
		newsJson.put("amount", bookinfo.getAmount());
		newsJson.put("borrowedNumber", bookinfo.getBorrowedNumber());
		newsJson.put("reservationNumber", bookinfo.getReservationNumber());
		newsJson.put("summary", bookinfo.getSummary());
		return newsJson;
	}
	
	//借阅信息包装成JSONArray
	public static JSONArray BorrowedInfoToJSONArray(ArrayList<BorrowedInfo> borrowedList){
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < borrowedList.size(); i++){
			BorrowedInfo borrowedInfo = borrowedList.get(i);
			JSONObject newsJson = new JSONObject();
			newsJson.put("id", borrowedInfo.getId());
			newsJson.put("b_id", borrowedInfo.getB_id());
			newsJson.put("b_key", borrowedInfo.getB_key());
			newsJson.put("u_id", borrowedInfo.getU_id());
			newsJson.put("borrowedTime", borrowedInfo.getBorrowedTime().toString());
			newsJson.put("isRenew", borrowedInfo.isRenew());
			newsJson.put("isOverdue", borrowedInfo.isOverdue());
			jsonArray.add(newsJson);
		}
		return jsonArray;
	}
	
	//预约信息包装成JSONArray
	public static JSONArray ReservationInfoToJSONArray(ArrayList<ReservationInfo> reservationList){
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < reservationList.size(); i++){
			ReservationInfo reservationInfo = reservationList.get(i);
			JSONObject newsJson = new JSONObject();
			newsJson.put("id", reservationInfo.getId());
			newsJson.put("b_id", reservationInfo.getB_id());
			newsJson.put("u_id", reservationInfo.getU_id());
			newsJson.put("reservationTime", reservationInfo.getReservationTime().toString());
			jsonArray.add(newsJson);
		}
		return jsonArray;
	}

	//书评信息包装成JSONArray
	public static JSONArray BookReviewInfoToJSONArray(ArrayList<BookReviewInfo> bookReviewList, int page){
		JSONArray jsonArray = new JSONArray();
		for(int i = page*10; i < bookReviewList.size() && i < page*10+10; i++){
			jsonArray.add(InfoUtil.BookReviewInfoToJSONObject(bookReviewList.get(i)));
		}
		return jsonArray;
	}
	
	public static JSONObject BookReviewInfoToJSONObject(BookReviewInfo bookReviewInfo){
		JSONObject newsJson = new JSONObject();
		newsJson.put("id", bookReviewInfo.getId());
		newsJson.put("b_id", bookReviewInfo.getB_id());
		newsJson.put("u_id", bookReviewInfo.getU_id());
		newsJson.put("reviewTime", bookReviewInfo.getReviewTime().toString());
		newsJson.put("grade", bookReviewInfo.getGrade());
		newsJson.put("bookReviewContent", bookReviewInfo.getBookReviewContent());
		return newsJson;
	}
	
}
