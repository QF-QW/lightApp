package com.QW.servlet;

import com.QW.pojo.ActiveList;
import com.QW.pojo.Societies;
import com.QW.pojo.User;
import com.QW.service.ActiveListServer.ActiveListServerImpl;
import com.QW.service.SocietiesServer.SocietiesServerImpl;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class ActiveListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("type") == null) {
            if (!ServletFileUpload.isMultipartContent(req)) {
                try {
                    resp.sendRedirect("/lightapp/static/html/home.html");
                } catch (IOException e) {
                    System.out.println("跳转错误");
                }
            }

            /*文件保存路径*/
            String uploadPath = this.getServletContext().getRealPath("/static/uploadimg");/*真实路径*/
            File upLoadFile = new File(uploadPath);
            if (!upLoadFile.exists()) {
                upLoadFile.mkdirs();
            }

            /*大文件缓存临时文件*/
            String tmpPath = this.getServletContext().getRealPath("/static/tmp");
            File tmpFile = new File(tmpPath);
            if (!tmpFile.exists()) {
                tmpFile.mkdirs();
            }

            /*处理文件路径，限制文件大小设置上传*/
            DiskFileItemFactory diskFileItemFactory = new org.apache.commons.fileupload.disk.DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(10240);
            diskFileItemFactory.setRepository(tmpFile);
//
            /*获取文件上传类*/
            ServletFileUpload upLoad = new ServletFileUpload(diskFileItemFactory);
//        upLoad.setProgressListener(new ProgressListener() {
//
//            @Override
//            public void update(long pByteRead, long pContentLength, int pItems) {
//                System.out.println("总大小："+pContentLength+"\t已近上传"+pByteRead);
//            }
//        } );


            ActiveList activeList = new ActiveList();
            List<FileItem> fileItems = null;
            try {
                fileItems = upLoad.parseRequest(req);

                for (org.apache.commons.fileupload.FileItem fileItem : fileItems) {/*之后每次获取的都是单个文件*/

                    if (fileItem.isFormField()) {/*判断是不是普通的表单，就是普通的请求参数*/
                        String fieldName = fileItem.getFieldName();/*获取name的参数*/
                        String value = null;/*获取value*/
                        try {
                            value = fileItem.getString("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("FileUpload 编码错误");
                        }

                        /*获取非文件的键值对*/
                        System.out.println(fieldName + ":" + value);

                        switch (fieldName) {
                            case "coordinate":
                                activeList.setCoordinate(value);
                                break;
                            case "createId": {
                                assert value != null;
                                activeList.setCreateId(Integer.parseInt(value));
                            }
                            break;
                            case "activeName":
                                activeList.setActiveName(value);
                                break;
                            case "introduction":
                                activeList.setIntroduction(value);
                                break;
                            case "className":
                                activeList.setClassName(value);
                                break;
                            case "people": {
                                assert value != null;
                                activeList.setPeople(Integer.parseInt(value));
                            }
                            break;
                            case "dayTime":
                                activeList.setDayTime(value);
                                break;
                            case "dayTimeSimple":
                                activeList.setDayTimeSimple(value);
                                break;
                            case "day": {
                                assert value != null;
                                activeList.setDay(Integer.parseInt(value));
                            }
                            break;
                            case "week":
                                activeList.setWeek(value);
                                break;
                            default:
                                System.out.println("出现意外的参数:" + fieldName);
                        }


                    } else {
                        String name = fileItem.getName();
                        System.out.println("文件名：" + name);

                        if (!(name.trim()).equals("")) {
                            /*文件处理：*/
                            String fileName = name.substring(name.lastIndexOf("/") + 1);
                            String fileExtName = name.substring(name.lastIndexOf(".") + 1);

                            System.out.println("上传文件名：" + name + "\t文件名：" + fileName + "\t后缀名" + fileExtName);
                            /*存储文件夹处理*/
                            String uuidPath = UUID.randomUUID().toString();

                            String realPath = uploadPath + "/" + uuidPath;
                            File realPathFile = new File(realPath);

                            if (!realPathFile.exists()) {
                                realPathFile.mkdir();
                            }

                            /*处理文件流*/

                            /*输入流、输出流*/
                            InputStream inputStream = fileItem.getInputStream();
                            FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);

                            String saveUrl = Init.imgUploadUrl + uuidPath + "/" + fileName;

                            /*开始存储*/
                            byte[] buffer = new byte[1024 * 1024];
                            int len = 0;

                            while ((len = inputStream.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }

                            /*完毕之后*/
                            fos.close();
                            inputStream.close();
                            fileItem.delete();

                            System.out.println(saveUrl);


                            activeList.setHeadUrl(saveUrl);
                        }


                    }
                }
            } catch (FileUploadException | IOException e) {
                e.printStackTrace();
            }

            new ActiveListServerImpl().createActiveList(activeList);


            try {
                resp.sendRedirect("/lightapp/static/html/activeList.html");
            } catch (IOException e) {
                System.out.println("跳转错误");
            }
        } else if ("getActiveList".equals(req.getParameter("type"))) {
            try {

                ActiveListServerImpl activeListServer = new ActiveListServerImpl();
                JSONObject result = activeListServer.getActiveList(Integer.parseInt(req.getParameter("page")));

                resp.getWriter().println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("searchJoin".equals(req.getParameter("type"))) {
            int activeId = Integer.parseInt(req.getParameter("activeId"));

            User user = (User) req.getSession().getAttribute("user");

            ActiveListServerImpl activeListServer = new ActiveListServerImpl();

            if (activeListServer.searchJoinServer(activeId, user.getId())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "true");
                try {
                    resp.getWriter().println(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "false");
                try {
                    resp.getWriter().println(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else if("join".equals(req.getParameter("type"))){
            int activeId = Integer.parseInt(req.getParameter("activeId"));

            User user = (User)req.getSession().getAttribute("user");

            ActiveListServerImpl activeListServer = new ActiveListServerImpl();

            activeListServer.joinServer(activeId, user.getId());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "申请成功！");

            try {
                resp.getWriter().println(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                resp.sendRedirect("/lightapp/static/html/activeList.html");
            } catch (IOException e) {
                System.out.println("跳转错误");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
