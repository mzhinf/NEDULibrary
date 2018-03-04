package nedu.edu.library.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.UserInfoImpl;
import nedu.edu.library.entity.UserInfo;
import net.sf.json.JSONObject;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/LoginServlet?userid=root&password=root&way=1
		//http://localhost:8080/NEDULibrary/LoginServlet?userid=six&password=sixsixsix&way=2
		//http://localhost:8080/NEDULibrary/LoginServlet?way=3&id=21&password=sixsixsix&sex=m
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//编码
		req.setCharacterEncoding("utf-8");
		
		int way = Integer.parseInt(req.getParameter("way"));

		if (way == 1) {	//登陆
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			System.out.println(userid);
			System.out.println(password);
			UserInfoImpl userinfoimpl = new UserInfoImpl();
			JSONObject userjson = userinfoimpl.getUserInfo(userid);
			JSONObject res = new JSONObject();
			if(userjson != null){//用户存在
				if(userjson.getString("password").equals(password)){//密码正确
					res.put("res", "login success");
					res.put("userinfo", userjson);
				}
				else{//密码错误
					res.put("res", "password error");
				}
			}else{//用户不存在
				res.put("res", "no userid");
			}
			resp.getOutputStream().write(res.toString().getBytes());
		}
		else if (way == 2){	//注册
			boolean flag = false;
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			System.out.println(userid);
			System.out.println(password);
			UserInfoImpl userinfoimpl = new UserInfoImpl();
			flag = userinfoimpl.add(userid, password);
			JSONObject res = new JSONObject();
			if (flag) {
				res.put("res", "register success");
			}
			else {
				res.put("res", "repeat of userid");
			}
			resp.getOutputStream().write(res.toString().getBytes());
		}
		else if(way == 3){	//更新用户信息
			boolean flag = false;
			UserInfoImpl userinfoimpl = new UserInfoImpl();
			//string 转化成 json
			JSONObject userjson = JSONObject.fromObject(req.getParameter("userInfo"));
			UserInfo userInfo = new UserInfo(userjson);
			flag = userinfoimpl.update(userInfo);
			JSONObject res = new JSONObject();
			if (flag) {
				res.put("res", "update success");
			}
			else {
				res.put("res", "update failed");
			}
			resp.getOutputStream().write(res.toString().getBytes());
		}
	}

	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}
}