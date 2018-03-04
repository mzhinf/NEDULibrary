package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.BookInfoImpl;
import nedu.edu.library.dao.impl.ReservationInfoImpl;
import nedu.edu.library.entity.BookInfo;
import nedu.edu.library.entity.ReservationInfo;
import nedu.edu.library.util.InfoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReservationInfoServlet extends HttpServlet {

	public ReservationInfoServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/ReservationInfoServlet?u_id=5&way=1
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//实现显示用户预约-----way=1 --> u_id
		//实现预约-----way=2 --> u_id b_id
		
		//编码
		req.setCharacterEncoding("utf-8");
		
		//获取数据
		int way = Integer.parseInt(req.getParameter("way"));
		System.out.println("way:"+way);
		if(way == 1){
			int u_id = Integer.parseInt(req.getParameter("u_id"));
			System.out.println("u_id:"+u_id);
			
			//在数据库中查询数据
			ReservationInfoImpl reservationInfoImpl = new ReservationInfoImpl();
			BookInfoImpl bookinfoimpl = new BookInfoImpl();
			ArrayList<ReservationInfo> reservationList = reservationInfoImpl.getReservationList(u_id);
			
			//将查到的数据封装成json数据
			//ReservationInfo + BookInfo
			JSONArray reservationjson = InfoUtil.ReservationInfoToJSONArray(reservationList);
			JSONArray bookjson = new JSONArray();
			for(int i = 0; i < reservationList.size(); i++){
				int b_id = reservationList.get(i).getB_id();
				BookInfo bookinfo = bookinfoimpl.idToBookInfo(b_id);
				bookjson.add(InfoUtil.BookInfoToJSONObject(bookinfo));
			}
			JSONObject res = new JSONObject();
			
			res.put("ReservationInfo",reservationjson);
			res.put("BookInfo",bookjson);
			resp.getOutputStream().write(res.toString().getBytes());
			
		}
		else if(way ==2){
			int u_id = Integer.parseInt(req.getParameter("u_id"));
			int b_id = Integer.parseInt(req.getParameter("b_id"));
			System.out.println("u_id:"+u_id);
			System.out.println("b_id:"+b_id);
			
			boolean flag = false;
			
			ReservationInfoImpl reservationInfoImpl = new ReservationInfoImpl();
			flag = reservationInfoImpl.addReservationInfo(u_id, b_id);
			
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

}
