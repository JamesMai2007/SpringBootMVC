package com.example.sbmvc.controller;

import com.example.sbmvc.validatecode.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    /*@Autowired
    private Myconfig myconfig;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login.html")
    public String loginHtml(){
        return "/view/login.html";
    }*/

    /*@PostMapping("/login")
    public String login(@RequestParam String username , @RequestParam String password) throws ServiceException {
        //System.out.println(myconfig.getUserList());
        UserDetails user = userService.loadUserByUsername(username);
        if(user != null)
        {
            String encPaw = passwordEncoder.encode(password);

            if(user.getPassword().equalsIgnoreCase(encPaw))
            {
                Authentication auth = new UsernamePasswordAuthenticationToken(username , encPaw);
                SecurityContextHolder.getContext().setAuthentication(auth);

                return "redirect:/index.html";
            }
        }


        return "redirect:/login.html?error=LoginError";
    }*/


    @Autowired
    Captcha captcha;

    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletRequest request , HttpServletResponse response)throws ServletException, IOException {
        captcha.create(request, response);
    }

}
