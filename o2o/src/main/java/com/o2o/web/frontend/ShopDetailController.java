package com.o2o.web.frontend;

import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.service.ProductCategoryService;
import com.o2o.service.ProductService;
import com.o2o.service.ShopService;
import com.o2o.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息及店铺下面的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtils.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            // 获取店铺id为shopID的店铺信息
            shop = shopService.getByShopId(shopId);
            // 获取店铺下面的商品列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 根据查询条件分页列出店铺下面的所有商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取页码
        int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
        // 获取一页展示的条数
        int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
        long shopId = HttpServletRequestUtils.getLong(request, "shopId");
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            long productCategoryId = HttpServletRequestUtils.getLong(request,
                    "productCategoryId");
            String productName = HttpServletRequestUtils.getString(request,
                    "productName");
            Product productCondition = compactProductCondition4Search(shopId,
                    productCategoryId, productName);
            ProductExecution pe = productService.getProductList(
                    productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 组合条件查询，并封装到productCondition对象中
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId,
                                                   long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            // 查询某个商品类别下的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            // 通过商品名字模糊查询含该商品名字的店铺列表
            productCondition.setProductName(productName);
        }
        // 只选出商品状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
