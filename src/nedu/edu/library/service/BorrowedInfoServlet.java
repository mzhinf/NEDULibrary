package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.BookInfoImpl;
import nedu.edu.library.dao.impl.BorrowedInfoImpl;
import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.entity.BorrowedInfo;
import nedu.edu.library.util.InfoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BorrowedInfoServlet extends HttpServlet {

	public BorrowedInfoServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/BorrowedInfoServlet?u_id=5&way=1
		//http://localhost:8080/NEDULibrary/BorrowedInfoServlet?u_id=21&id=1&way=2
		//http://localhost:8080/NEDULibrary/BorrowedInfoServlet?way=3&b_id=144&u_id=21&b_key=1
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		
		int way = Integer.parseInt(req.getParameter("way"));
		System.out.println("way:"+way);
		
		if (way == 1) {//way=1 返回user借阅信息
			int u_id = Integer.parseInt(req.getParameter("u_id"));
			System.out.println("u_id:"+u_id);
			
			//在数据库中查询数据
			BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			ArrayList<BorrowedInfo> borrowedList = borrowedInfoImpl.getBorrowedList(u_id);
			
			//将查到的数据封装成json数据
			//BorrowedInfo + BookInfo
			JSONArray borrowedjson = InfoUtil.BorrowedInfoToJSONArray(borrowedList);
			JSONArray bookjson = new JSONArray();
			for(int i = 0; i < borrowedList.size(); i++){
				int b_id = borrowedList.get(i).getB_id();
				BookInfo bookinfo = bookinfoimpl.idToBookInfo(b_id);
				bookjson.add(InfoUtil.BookInfoToJSONObject(bookinfo));
			}
			JSONObject res = new JSONObject();
			
			//发送数据到客户端
			res.put("BorrowedInfo",borrowedjson);
			res.put("BookInfo",bookjson);
			System.out.println(res.toString());
			resp.getOutputStream().write(res.toString().getBytes());
		} else if(way == 2) {//实现续借功能 更新借书信息
			boolean flag = false;
			int id = Integer.parseInt(req.getParameter("id"));
			int u_id = Integer.parseInt(req.getParameter("u_id"));
			System.out.println("id:"+id);
			BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
			flag = borrowedInfoImpl.update(id,u_id);
			JSONObject res = new JSONObject();
			if(flag){
				res.put("res", flag);
				res.put("id", id);//对于续借成功的书籍 返回借阅的id 使得客户端更新数据
			}
			else{
				res.put("res", flag);
			}
			resp.getOutputStream().write(res.toString().getBytes());
		} else if (way == 3 || way == 4) {//实现借阅还书功能
			int u_id = Integer.parseInt(req.getParameter("u_id"));
			int b_id = Integer.parseInt(req.getParameter("b_id"));
			int b_key = Integer.parseInt(req.getParameter("b_key"));
			int error = -1;
			BorrowedInfoImpl borrowedInfoImpl = new BorrowedInfoImpl();
			if (way == 3){//借阅
				error = borrowedInfoImpl.addBorrowedInfo(u_id, b_id, b_key);
			} else if (way == 4){//还书
				error = borrowedInfoImpl.deleteBorrowedInfo(u_id, b_id, b_key);
			}
			JSONObject res = new JSONObject();
			res.put("error", error);
			resp.getOutputStream().write(res.toString().getBytes());
		}
	}
	
}
