package com.o2o.web.frontend;

import com.o2o.entity.HeadLine;
import com.o2o.entity.ShopCategory;
import com.o2o.service.HeadLineService;
import com.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService; /*9:31*/
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 初始化前端展示系统的主页信息，包括获取一级店铺类别及头条列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try {
            // 获取状态为1的可用头条列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }
}
