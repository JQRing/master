package com.o2o.web.shopadmin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "shopadmin")
public class ShopAdminController {

    @RequestMapping(value = "/shopedit")
    public String shopOperation() {
        return "shop/shopedit";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanage";
    }

    @RequestMapping(value = "/productcategorymanage")
    public String productCategoryManage() {
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/productedit")
    public String productEdit() {
        return "shop/productedit";
    }

}
