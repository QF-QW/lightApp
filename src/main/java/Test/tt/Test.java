//package Test.tt;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.ProgressListener;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.UUID;
//
//public class Test extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        /*先判断是不是附件表单*/
//        if(!ServletFileUpload.isMultipartContent(req)){
//            return;
//        }
//
//        /*创建两个文件目录*/
//        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");/*真实路径、相对路径不知道行不行*/
//        File upLoadFile = new File(uploadPath);
//        if(!upLoadFile.exists()){
//            upLoadFile.mkdirs();
//        }
//        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
//        File tmpFile = new File(tmpPath);
//        if(!tmpFile.exists()){
//            tmpFile.mkdirs();
//        }
//
//        /*创建、初始化磁盘工厂*/
//        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
//        diskFileItemFactory.setSizeThreshold(1024*1024);
//        diskFileItemFactory.setRepository(tmpFile);
//
//        /*干正事，创建servletFileUpload*/
//        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
//        servletFileUpload.setProgressListener(new ProgressListener() {
//            @Override
//            public void update(long pByteRead, long pContentLength, int pItem) {
//                System.out.println("总大小："+pContentLength+"\t已经缓存大小:"+pByteRead+"缓存的物品？："+pItem);
//            }
//        });
//        List<FileItem> fileItems = servletFileUpload.parseRequest(new ServletRequestContext(req));
//
//        for(FileItem fileItem : fileItems){
//            if(fileItem.isFormField()){
//                /*自己处理*/
//                String name = fileItem.getName();
//                String value = fileItem.getString();
//                System.out.println("普通字段名："+name+"\t数值名：" + value);
//            }else{
//                String name = fileItem.getName();
////                String fieldName = fileItem.getFieldName();
//                System.out.println("文件名："+name+"\t\t"+fileItem);
//
//                if(!name.trim().equals("")){
//                    String fileName = name.substring(name.lastIndexOf("/") + 1);/*其实你要是不怕可以直接不查的*/
////                    String fileExtName = name.substring(name.lastIndexOf(".") + 1);
//
//                    String uuidPath = UUID.randomUUID().toString();
//                    String fileLoadPath = uploadPath+"/"+uuidPath;
//
//                    File realPathFile = new File(fileLoadPath);
//                    if(!realPathFile.exists()){
//                        realPathFile.mkdirs();
//                    }
//
//                    FileOutputStream fileOutputStream = new FileOutputStream(realPathFile + "/" + fileName);
//
//                    InputStream inputStream = fileItem.getInputStream();
//
//                    int len = 0;
//                    byte[] bytes = new byte[1024*1024];
//
//                    while((len = inputStream.read(bytes))!=-1){
//                        fileOutputStream.write(bytes,0,len);
//                    }
//                    inputStream.close();
//                    inputStream.close();
//                    fileItem.delete();
//
//
//
//                }
//            }
//        }
//
//
//
//    }
//}
