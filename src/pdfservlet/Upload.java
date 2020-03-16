package pdfservlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

@WebServlet("/Upload")
public class Upload extends HttpServlet {
	Logger logger = LogManager.getLogger(this.getClass()); 
	
	//파일저장 경로 입력
	String path = "C:/Users/bsm91/Desktop/새 폴더";
       
    public Upload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssS");
		
		File repository = new File(path);
		factory.setRepository(repository);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
			
			for(FileItem f : items) {
				if(f.getName() == null ) {
					continue;
				}
				
				String tmpFileNm = f.getName().split("\\.")[0];
				
				String tmpExtNm = "";
				
				if(f.getName().split("\\.").length > 1) {
					tmpExtNm = "." + f.getName().split("\\.")[1];
				}
				
				String uploadFileNm = tmpFileNm + "_" + sdf.format(new Date()) + tmpExtNm;
				
				File uploadFile = new File(path + "\\" + uploadFileNm);
				
				if(f.isFormField() == false) { 
					if(f.getName() != null && f.getName().equals("") == false) {
						f.write(uploadFile);						
					} else { 
						logger.debug("name is null");
					}
				} else { 
					logger.debug("no file field");
				}
			}
			
			//request.getRequestDispatcher("index.jsp").forward(request, response);
			
		} catch (FileUploadException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}