package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.BookInfoImpl;
import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.util.DailyTask;

public class ManageBookInfoServlet extends HttpServlet {

	public ManageBookInfoServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//flag 表示是否插入成功
		boolean flag = false;
		String category = null;
		category = req.getParameter("getCategory");
		
		if (category == null) {
			//编码
			req.setCharacterEncoding("utf-8");
			
			//数据库操作数据
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			
			BookInfo bookinfo = new BookInfo();
			bookinfo.setTitle(req.getParameter("title"));
			bookinfo.setAuthor(req.getParameter("author"));
			bookinfo.setPublishing(req.getParameter("publishing"));
			bookinfo.setISBN(req.getParameter("ISBN"));
			bookinfo.setSubject(req.getParameter("subject"));
			bookinfo.setSearchNumber(req.getParameter("searchNumber"));
			String amount = req.getParameter("amount");
			if (amount != null) {
				bookinfo.setAmount(Integer.parseInt(amount));
			}
			bookinfo.setBorrowedNumber(0);//入库书籍没有借阅
			bookinfo.setReservationNumber(0);//入库书籍没有预约
			bookinfo.setSummary(req.getParameter("summary"));
			
			flag = bookinfoimpl.addBookInfo(bookinfo);
			
			if (flag){
				req.setAttribute("flag", "图书信息插入成功");
			}
			else{
				req.setAttribute("flag", "图书信息插入失败");
			}
			req.getRequestDispatcher("jsp/admin/bookinfoRes.jsp").forward(req, resp);
		} else {
			String str = req.getParameter("str");
			String page = req.getParameter("pageNow");
			int pageNow = 1;
			if (page != null) pageNow = Integer.parseInt(page);
			
			System.out.println("pageNow:"+pageNow);
			System.out.println("str:"+str);
			System.out.println("getCategory:"+category);
			
			//数据库操作数据
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			ArrayList<BookInfo> getlist = null;
			ArrayList<BookInfo> bookList = new ArrayList<BookInfo>();
			getlist = bookinfoimpl.getBookList(str, category);
			int itemNum = getlist.size();
			int pageNum = (itemNum + 4) / 5;
			for (int i = (pageNow - 1) * 5; i < pageNow * 5 && i < itemNum; i++) {
				bookList.add(getlist.get(i));
			}
			
			if (itemNum == 0) {
				int resCode = 201;
				req.setAttribute("resCode", resCode);
				req.getRequestDispatcher("jsp/admin/resPage.jsp").forward(req, resp);
			} else {
				req.setAttribute("bookList", bookList);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("pageNow", pageNow);
				req.setAttribute("getCategory", category);
				req.setAttribute("str", str);
				req.getRequestDispatcher("jsp/admin/bookinfoList.jsp").forward(req, resp);
			}
		}
		
	}
	
}
