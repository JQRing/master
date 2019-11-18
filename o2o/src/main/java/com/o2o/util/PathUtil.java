package com.o2o.util;

import java.io.File;

public class PathUtil {

    public static final String SEPERATOR = System.getProperty("file.separator");

    /**
     * 返回不同系统下图片根路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "F:/project/image/";
        } else {
            basePath = "/home/download/image";
        }
        basePath = basePath.replace("/", SEPERATOR);
        return basePath;
    }

    /**
     * 返回项目图片的子路径
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/item/shop" + shopId + "/";
        return imagePath.replace("/", SEPERATOR);
    }
}
