package com.example.oldguy.modules.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: ViewController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/2 0002 上午 10:22
 **/
@RequestMapping("view")
@Controller
public class ViewController {

    @RequestMapping("/index")
    public String index(){

        return "index";
    }

}
