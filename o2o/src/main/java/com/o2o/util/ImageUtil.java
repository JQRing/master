package com.o2o.util;

import com.o2o.dto.ImageHolder;
import com.o2o.exceptions.ProductOperationException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    private static final String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        // 文件名
        String realFileName = getRandomFileName();
        // 扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        // 创建文件路径
        madirFilePath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath + "/mk.png")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标目录所涉及的目录
     *
     * @param targetAddr
     */
    private static void madirFilePath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File filePath = new File(realFileParentPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机数名字，当前年月日小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        int randNum = random.nextInt(89999) + 10000;
        String newTime = sDateFormat.format(new Date());
        return newTime + randNum;
    }

    /**
     * storePath是文件路径还是目录路径
     * 如果是文件路径则删除该文件
     * 如果是目录路径则删除该目录下的所有文件
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:\\Users\\xhj\\Pictures\\Camera Roll\\balloon.jpg")).size(200, 200)
                .watermark(Positions.CENTER, ImageIO.read(new File("C:\\Users\\xhj\\Pictures\\Camera Roll\\mk.png")),
                        0.5f).outputQuality(.8f).toFile("C:\\Users\\xhj\\Pictures\\Camera Roll\\balloon-watermark.jpg");
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 文件名
        String realFileName = getRandomFileName();
        // 扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        // 创建文件路径
        madirFilePath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath + "/mk.png")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }
}
