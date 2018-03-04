package nedu.edu.library.test;

import nedu.edu.library.dao.impl.BookReviewInfoImpl;
import nedu.edu.library.dao.impl.BorrowedInfoImpl;
import nedu.edu.library.dao.impl.ReservationInfoImpl;
import nedu.edu.library.dao.impl.UserInfoImpl;
import nedu.edu.library.entity.BookReviewInfo;
import nedu.edu.library.entity.UserInfo;
import nedu.edu.library.qrcode.QRCodeUtil;
import net.sf.json.JSONObject;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public void testReservation(){
		int u_id = 5;//马泽华
		int b_id = 1;//追寻生命的意义
		ReservationInfoImpl impl = new ReservationInfoImpl();
		/*
		ArrayList<ReservationInfo> arrayList = impl.getReservationList(5);
		
		for(ReservationInfo reservationInfo:arrayList){
			System.out.println("id:"+reservationInfo.getId());
			System.out.println("u_id:"+reservationInfo.getU_id());
			System.out.println("b_id:"+reservationInfo.getB_id());
			System.out.println("time:"+reservationInfo.getReservationTime());
		}
		*/
		impl.addReservationInfo(u_id,b_id);
		impl.dailyTaskDeleteReservationInfo();
	}
	
	public void testUser(){
		UserInfoImpl userInfoImpl = new UserInfoImpl();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(21);
		userInfo.setPassword("sixsixsix");
		userInfo.setEmail("15948682400@163.com");
		//userInfo.setSex('m');
		
		userInfoImpl.update(userInfo);
		//userInfoImpl.add("six", "sixsixsix");
	}
	
	public void testBorrowed(){
		
		int u_id = 21;//马泽华
		int b_id = 144;//追寻生命的意义
		int b_key = 1;
		BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
		//测试借阅功能
		//borrowedInfoImpl.addBorrowedInfo(u_id, b_id, b_key);
		borrowedInfoImpl.deleteBorrowedInfo(u_id, b_id, b_key);
		
		//测试还书功能
		//borrowedInfoImpl.deleteBorrowedInfo(u_id, b_id);
		//borrowedInfoImpl.dailyTaskSetOverdue();
	}
	
	public void testBookReview() {
		int b_id = 1;//追寻生命的意义
		BookReviewInfoImpl bookReviewInfoImpl = new BookReviewInfoImpl();
		/*
		//测试用户获取书评
		ArrayList<BookReviewInfo> bookReviewList = bookReviewInfoImpl.getBookReviewList(b_id);
		for (BookReviewInfo bookReviewInfo:bookReviewList) {
			System.out.println(bookReviewInfo.getId());
			System.out.println(bookReviewInfo.getU_id());
			System.out.println(bookReviewInfo.getB_id());
			System.out.println(bookReviewInfo.getReviewTime());
			System.out.println(bookReviewInfo.getGrade());
			System.out.println(bookReviewInfo.getBookReviewContent());
		}
		*/
		//测试插入书评
		BookReviewInfo bookReviewInfo = new BookReviewInfo();
		bookReviewInfo.setU_id(27);
		bookReviewInfo.setB_id(1);
		bookReviewInfo.setGrade(2);
		bookReviewInfo.setBookReviewContent("test");
		bookReviewInfoImpl.addBookReview(bookReviewInfo);
		
	}
	
	public void testCreateQRCode() {
		String ISBN = "9787501162734";
		int code = 1;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ISBN", ISBN);
		jsonObject.put("code", code);
		String str = jsonObject.toString();
		String file = ISBN + "-" + code + ".jpg";
		try {
			QRCodeUtil.encode(str,"","D:\\eclipse_workspace\\NEDULibrary\\WebRoot\\bookQRcode",true,file);
			System.out.println("创建" + file + "成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] agrs){
		Test test = new Test();
		//test.testUser();
		//测试借阅功能
		//test.testBorrowed();
		//测试预约功能
		//test.testReservation();
		//测试书评功能
		//test.testBookReview();
		//DailyTask.dailyTask();
		
		//BookInfo bookInfo = new BookInfo();
		//System.out.println(bookInfo.getTitle());
		
		test.testCreateQRCode();
	}

}