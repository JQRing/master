package com.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "/index")
    public String index() {
        return "frontend/index";
    }

    @RequestMapping(value = "/shopdetail")
    public String shopDetail() {
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/productdetail")
    public String productDetail() {
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "frontend/shoplist";
    }

}
