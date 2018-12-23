package com.bing.lan.project.api.controller;

import com.bing.lan.project.api.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{version}/mail")
public class MailController extends BaseController {

    @RequestMapping("/mail")
    public String mail() {
        //http://localhost:8080/api/v1/mail/mail
        return "forward:/WEB-INF/views/mail.jsp";
    }

    @RequestMapping("/send")
    public String send(String userId, String title, String content) {

        //https://blog.csdn.net/mic_hero/article/details/50157339
        //https://blog.csdn.net/xietansheng/article/details/51673073

        return "forward:/WEB-INF/views/sendMailSuccess.jsp";
    }
}
