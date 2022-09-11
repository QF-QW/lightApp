package Test;

import com.QW.service.GetCourseServer.GetCourseServer;
import com.QW.service.GetCourseServer.GetCourseServerImpl;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class seleniumTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*注册驱动*/
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        EdgeDriver driver = new EdgeDriver();

        /*打开页面*/
        driver.get("https://cas.gdaib.edu.cn/lyuapServer/login");
        /*输入账号*/
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("20217714322");
        /*输入密码*/
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("23142X");

        /*提交*/
        WebElement submit = driver.findElement(By.className("btn-submit"));
        submit.click();

        driver.get("http://jwxt.gdaib.edu.cn/jsxsd/xskb/xskb_list.do?jx0404id=&cj0701id=&zc=&demo=%E8%AF%BE%E8%A1%A8%E6%9A%82%E6%9C%AA%E5%85%AC%E5%B8%83%EF%BC%8C%E4%B8%8D%E8%83%BD%E6%9F%A5%E7%9C%8B%E8%AF%BE%E8%A1%A8%EF%BC%81&xnxq01id=2021-2022-1&sfFD=1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().alert().accept();
        driver.get("http://jwxt.gdaib.edu.cn/jsxsd/xskb/xskb_list.do?jx0404id=&cj0701id=&zc=&demo=%E8%AF%BE%E8%A1%A8%E6%9A%82%E6%9C%AA%E5%85%AC%E5%B8%83%EF%BC%8C%E4%B8%8D%E8%83%BD%E6%9F%A5%E7%9C%8B%E8%AF%BE%E8%A1%A8%EF%BC%81&xnxq01id=2021-2022-1&sfFD=1");



        /*获取数据*/
        List<WebElement> fonts = driver.findElementsByClassName("kbcontent");
        HashSet<String> hashCourseValue = new HashSet<>();

        /*遍历课程、去重*/
        for (WebElement webElement : fonts) {

            String webString = webElement.getText().trim();
            if (!"".equals(webString)) {
                String weekDay = webElement.getAttribute("id");
                String day = String.valueOf(weekDay.charAt(weekDay.lastIndexOf("-") - 1));
                String[] values = webString.split("---------------------");
                for(int i = 0;i< values.length;i++){
                    values[i] ="星期"+day+"\n"+ values[i].trim();
                }
                hashCourseValue.addAll(Arrays.asList(values));
            }
        }

        /*数据获取*/

        /*数据缺少处理*/
        String[] strings = new String[5];;
        strings[4] = "未排教室";
        /*保存为JSON*/
        int index = 0;
        JSONObject courses = new JSONObject();
        JSONObject course;


        for (String value : hashCourseValue) {
            String[] courseInfo = value.split("\n");
            if(courseInfo.length!=5){
                System.arraycopy(courseInfo, 0, strings, 0, 4);


                courseInfo = strings;
            }

            /*周数与节数分离*/
            String weekTime = courseInfo[3];

            String startTimeStr = weekTime.substring(weekTime.indexOf("[") + 1, weekTime.indexOf("-"));
            String endTimeStr = weekTime.substring(weekTime.lastIndexOf("-") + 1, weekTime.indexOf("节"));
            int[] startTime = new int[2];
            int[] endTime = new int[2];

            switch (startTimeStr){
                case "01" : {
                    startTime = new int[]{8,30};
                }break;
                case "03" : {
                    startTime = new int[]{10,0};
                }break;
                case "05" :{
                    startTime = new int[]{11,30};
                }break;
                case "06":{
                    startTime = new int[]{14,0};
                }break;
                case "08":{
                    startTime = new int[]{15,30};
                }break;
                case "10":{
                    startTime = new int[]{18,30};
                }break;
                case "12":{
                    startTime = new int[]{20,0};
                }break;
            }

            switch (endTimeStr){
                case "02" : {
                    endTime = new int[]{9,50};
                }break;
                case "04" : {
                    endTime = new int[]{11,20};
                }break;
                case "05" :{
                    endTime = new int[]{12,10};
                }break;
                case "07":{
                    endTime = new int[]{15,20};
                }break;
                case "09":{
                    endTime = new int[]{16,50};
                }break;
                case "11":{
                    endTime = new int[]{19,50};
                }break;
                case "13":{
                    endTime = new int[]{21,20};
                }break;
            }

            System.out.println("startTime:"+ Arrays.toString(startTime)+"endTime"+ Arrays.toString(endTime));





            /*保存*/
            course = new JSONObject();


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
