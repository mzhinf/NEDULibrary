package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.BookInfoImpl;
import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.util.InfoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BookInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/BookInfoServlet?str=1&way=subject&page=0
		//req.setAttribute("str", new String(req.getParameter("str").getBytes("iso-8859-1"),"utf-8"));
		//req.setAttribute("way", new String(req.getParameter("way").getBytes("iso-8859-1"),"utf-8"));
		//req.setAttribute("page", new String(req.getParameter("page").getBytes("iso-8859-1"),"utf-8"));
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//编码
		req.setCharacterEncoding("utf-8");
		
		String way = req.getParameter("way");
		
		if(way.equals("input")) {
			//flag 表示是否插入成功
			boolean flag = false;
			String str = req.getParameter("bookInfo");
			System.out.println(str);
			BookInfo bookInfo = new BookInfo(JSONObject.fromObject(str));
			//数据库操作数据
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			flag = bookinfoimpl.addBookInfo(bookInfo);
			if (flag){
				req.setAttribute("flag", "图书信息插入成功");
			}
			else{
				req.setAttribute("flag", "图书信息插入失败");
			}
			JSONObject res = new JSONObject();
			res.put("result",flag);
			resp.getOutputStream().write(res.toString().getBytes());
		} else {
			String str = req.getParameter("str");
			int page = Integer.parseInt(req.getParameter("page"));
			System.out.println(str);
			System.out.println(way);
			System.out.println(page);
			
			//在数据库中查询数据
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			ArrayList<BookInfo> booklist = null;
			if (way.equals("all")) {
				booklist = bookinfoimpl.getBookList(new BookInfo(JSONObject.fromObject(str)));
			} else {
				booklist = bookinfoimpl.getBookList(str, way);
			}
			
			//将查到的数据封装成json数据
			JSONArray bookjson = InfoUtil.BookInfoToJSONArray(booklist,page);
			JSONObject res = new JSONObject();
			
			//发送数据到客户端
			res.put("BookInfo",bookjson);
			res.put("bookNum", booklist.size());
			resp.getOutputStream().write(res.toString().getBytes());
		}
	}
	
	public BookInfoServlet() {
		// TODO Auto-generated constructor stub
	}

}
