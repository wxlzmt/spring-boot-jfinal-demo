package io.github.wxlzmt.sbjd.controller;

import com.jfinal.core.Controller;


public class IndexController extends Controller {
    
    public void index() {
        System.out.println("执行到了io.github.wxlzmt.sbjd.controller.IndexController.index()");
        render("index.html");
    }
}
