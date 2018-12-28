package com.bing.lan.project.api.controller;

import com.bing.lan.project.api.BaseController;
import com.bing.lan.project.api.mail.MailSenderFactory;
import com.bing.lan.project.api.mail.MailSenderType;
import com.bing.lan.project.api.mail.SimpleMailSender;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/{version}/mail")
public class MailController extends BaseController {

    @RequestMapping("/mail")
    public String mail() {
        //http://localhost:8080/api/v1/mail/mail
        return "forward:/WEB-INF/views/mail.jsp";
    }

    @RequestMapping("/send")
    public String send(HttpServletRequest request, MultipartFile userIdsExcelFile,
            String title, String content, String mail) throws IOException {

        //MultipartHttpServletRequest defaultMultipartActionRequest = (MultipartHttpServletRequest) request;
        //Map<String, MultipartFile> fileMap = defaultMultipartActionRequest.getFileMap();

        if (userIdsExcelFile != null) {
            String contentType = userIdsExcelFile.getContentType();
            String name = userIdsExcelFile.getName();
            String originalFilename = userIdsExcelFile.getOriginalFilename();
            long size = userIdsExcelFile.getSize();

            System.out.println("upload() contentType: " + contentType);
            System.out.println("upload() name: " + name);
            System.out.println("upload() originalFilename: " + originalFilename);
            System.out.println("upload() size: " + size);

            InputStream inputStream = userIdsExcelFile.getInputStream();

            //String dir = "E:\\workspace\\IDEA_workspace\\LifeTools\\file";
            //String uuid = UUID.randomUUID().toString();
            //String lastName = userIdsExcelFile.getOriginalFilename().split("\\.")[1];
            //String fileName = uuid + "." + lastName;
            //FileOutputStream fileOutputStream = new FileOutputStream(new File(dir, fileName));

            //String lastName = userIdsExcelFile.getOriginalFilename().split("\\.")[1];
            //File tempFile = File.createTempFile(UUID.randomUUID().toString().replace("-", ""), "." + lastName);
            //tempFile.deleteOnExit();
            //FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

            //StreamUtils.copy(inputStream, fileOutputStream);
            //fileOutputStream.close();

            Sheet sheet = getSheet(inputStream);

            // 获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();
            // 获得所有数据
            for (int i = 1; i <= totalRowNum; i++) {
                // 获得第i行对象
                Row row = sheet.getRow(i);
                Cell cell = row.getCell((short) 0);
                System.out.println("upload() cell: " + cell);
            }
        }

        //https://blog.csdn.net/mic_hero/article/details/50157339
        //https://blog.csdn.net/xietansheng/article/details/51673073

        // 发送邮件
        SimpleMailSender sms = MailSenderFactory
                .getSender(MailSenderType.SERVICE);
        //try {
        //    sms.send(mail, title, content);
        //} catch (AddressException e) {
        //    e.printStackTrace();
        //} catch (MessagingException e) {
        //    e.printStackTrace();
        //}

        return "forward:/WEB-INF/views/sendMailSuccess.jsp";
    }

    @RequestMapping("/downloadTemp")
    @ResponseBody
    public void downloadTemp(HttpServletResponse response) throws IOException {
        //https://www.cnblogs.com/wangyang108/p/6030420.html
        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");

        for (int row = 0; row < 1; row++) {
            HSSFRow rows = sheet.createRow(row);
            for (int col = 0; col < 1; col++) {
                // 向工作表中添加数据
                rows.createCell(col).setCellValue("userIds");
            }
        }

        //File xlsFile = new File("poi.xls");
        //FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        //workbook.write(xlsStream);

        // 直接下载 或者弹下载窗口  否则会直接显示在浏览器中
        response.setHeader("Content-Disposition", "attachment;filename=temp.xls");
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
    }

    public Sheet getSheet(InputStream fis) {
        Workbook wookbook = null;
        try {
            // 2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);// 得到工作簿
        } catch (Exception ex) {
            // ex.printStackTrace();
            try {
                // 2007版本的excel，用.xlsx结尾
                wookbook = new XSSFWorkbook(fis);// 得到工作簿
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (wookbook != null) {
            return wookbook.getSheetAt(0);
        }
        return null;
    }
}
