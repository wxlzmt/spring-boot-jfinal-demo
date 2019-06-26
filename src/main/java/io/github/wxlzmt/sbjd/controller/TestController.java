package io.github.wxlzmt.sbjd.controller;

import com.jfinal.core.Controller;

import io.github.wxlzmt.sbjd.config.annotation.ControllerBind;

@ControllerBind(controllerKey = "/t",viewPath="/tt")
public class TestController extends Controller {

    public void index() {
        render("t.html");
    }
}
