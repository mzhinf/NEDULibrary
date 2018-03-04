package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.BookReviewInfoImpl;
import nedu.edu.library.dao.impl.UserInfoImpl;
import nedu.edu.library.entity.BookReviewInfo;
import nedu.edu.library.util.InfoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BookReviewServlet extends HttpServlet {
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/BookReviewServlet?way=1&b_id=1
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//该Servlet需要实现 1.客户端的申请书评信息 2.客户端的书评提交
		//way=1客户端的申请书评信息 way=2客户端的书评提交
		
		//编码
		req.setCharacterEncoding("utf-8");
		
		int way = Integer.parseInt(req.getParameter("way"));
		System.out.println("way:"+way);
		
		if (way == 1) {//客户端的申请书评信息
			int b_id = Integer.parseInt(req.getParameter("b_id"));
			int page = Integer.parseInt(req.getParameter("page"));
			System.out.println("b_id:"+b_id);
			System.out.println("page:"+page);
			
			//在数据库中查询数据
			BookReviewInfoImpl bookReviewInfoImpl = new BookReviewInfoImpl();
			UserInfoImpl userInfoImpl = new UserInfoImpl();
			ArrayList<BookReviewInfo> bookReviewList = bookReviewInfoImpl.getBookReviewList(b_id);
			
			//将查到的数据封装成json数据
			JSONArray bookreviewjson = InfoUtil.BookReviewInfoToJSONArray(bookReviewList,page);
			JSONArray userjson = new JSONArray();
			for(int i = 0; i < bookReviewList.size(); i++){
				int u_id = bookReviewList.get(i).getU_id();
				userjson.add(userInfoImpl.idGetUserId(u_id));
			}
			JSONObject res = new JSONObject();
			
			//发送数据到客户端
			res.put("BookReviewInfo",bookreviewjson);
			res.put("UserId",userjson);
			res.put("BookReviewNum", bookReviewList.size());
			resp.getOutputStream().write(res.toString().getBytes());
		}
		else if (way == 2) {//客户端的书评提交
			boolean flag = false;
			String bookReview = req.getParameter("bookReviewInfo");
			System.out.println(bookReview);
			JSONObject bookReviewJson = JSONObject.fromObject(bookReview);
			BookReviewInfo bookReviewInfo = new BookReviewInfo(bookReviewJson);
			BookReviewInfoImpl bookReviewInfoImpl = new BookReviewInfoImpl();
			flag = bookReviewInfoImpl.addBookReview(bookReviewInfo);
			JSONObject res = new JSONObject();
			if (flag) {
				res.put("res", flag);
			}
			else {
				res.put("res", flag);
			}
			resp.getOutputStream().write(res.toString().getBytes());
		}
	}

	public BookReviewServlet() {
		// TODO Auto-generated constructor stub
	}

}
