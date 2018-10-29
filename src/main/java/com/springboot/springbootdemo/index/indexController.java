package com.springboot.springbootdemo.index;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.lang.model.SourceVersion;

@Controller
public class indexController {

    @GetMapping(value = "login")
    public String loginUI(){
        return "login";
    }

    @RequestMapping(value = "index")
    public String indexUI(String username, String password, ModelAndView modelAndView){
        System.out.println(11);
        IniRealm iniRealm =new IniRealm("classpath:user.ini");
        //1.构建securtymanager
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            System.out.println("indexController.indexUI");
            System.out.println("userName = " + username);
            System.out.println("passWord = " + password);
            subject.login(usernamePasswordToken);
        }catch (Exception e){
            modelAndView.addObject("state","用户名或密码错误");
            return "login";
        }
        return "index";
    }
}
