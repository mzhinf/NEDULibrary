package nedu.edu.library.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import nedu.edu.library.util.ToolUtil;

public class UploaderServlet extends HttpServlet {

	public UploaderServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//首先判断一下 上传的数据是表单数据还是一个带文件的数据 
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) { //如果为true 说明是一个带有文件的数据
			//拿到servlet的真实路径 
			String realpath = request.getSession().getServletContext().getRealPath("/data/userAvatar");
			//String realpath = ToolUtil.DATA_PATH + "\\userAvatar";
			//打印一下路径
			System.out.println("realpath:"+realpath);
			File dir = new File(realpath);
			if (!dir.exists()) dir.mkdirs(); //如果目录不存在 把这个目录给创建出来 
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory); //获取到上传文件的对象upload
			upload.setHeaderEncoding("UTF-8");
			try {
				//判断一下上传的数据类型
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) { //上传的数据类型 是一个表单类型
						String name = item.getFieldName(); //得到请求参数的名称
						String value = item.getString("UTF-8"); //得到参数值
						System.out.println(name + "=" + value);
					} else {
						String filename = item.getFieldName() //参数名称为u_id 即上传的头像文件存为 u_id.png
								+ item.getName().substring(item.getName().lastIndexOf("."));
						System.out.println("filename:" + filename);
						//说明是一个文件类型   进行上传
						item.write(new File(dir, filename));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
