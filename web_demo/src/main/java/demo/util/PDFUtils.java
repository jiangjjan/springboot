package demo.util;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <code> pom依赖
 * <dependency>
 * <groupId>org.apache.pdfbox</groupId>
 * <artifactId>pdfbox</artifactId>
 * <version>2.0.21</version>
 * </dependency>\
 * </code>
 */
public class PDFUtils {


    @Test
    public void testPdf2ImageDifferentHeight() throws IOException {
        String file = "https://adiconlimscloud01.oss-cn-hangzhou.aliyuncs.com/report/file/2021-12-24/0efb07ec-f80a-4f8b-9564-279d41faaa37.pdf";
        String fileCN = "https://adiconlimscloud01.oss-cn-hangzhou.aliyuncs.com/test/report/test/file/2022-06-17/S03004221305-jy8329042380-西门误-2096.pdf";
        String fileA = "https://adiconlimscloud01.oss-cn-hangzhou.aliyuncs.com/test/report/test/file/2022-06-17/S03004221641-jy8329042380-郑枘-5680.pdf";

//        Files.write(Paths.get("D:/aba.jpg"), pdf2ImageByDisk(new UrlResource(fileA)).toByteArray());
        String book = "C:\\Users\\24954\\OneDrive\\文档\\book\\《深入浅出Java Swing程序设计》.(范明翔,陈锦辉).pdf";
//        Files.write(Paths.get("D:/aa.jpg"), pdf2ImageByDisk(new UrlResource(fileCN)).toByteArray());
        System.out.println(fileCN.substring(fileCN.lastIndexOf("/")+1));
    }

    public static FastByteArrayOutputStream pdf2ImageSameHeight(Resource resource) throws IOException {
        FastByteArrayOutputStream outFile = new FastByteArrayOutputStream();
        String imageType = "jpg";

        //临时变量
        int width, height; //每张图片的宽度和高度必须一致,否则会报错
        int[] data;
        BufferedImage image;
        BufferedImage newImage = null;
        try (InputStream pdfFile = resource.getInputStream();
             PDDocument pdDocument = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            int pages = pdDocument.getNumberOfPages();
            for (int i = 0; i < pages; i++) {

                if (i == 20)
                    break;
                //PDF可能有多页 将PDF的每一页渲染成一张图片
                image = renderer.renderImage(i, 2.7f);
                width = image.getWidth();
                height = image.getHeight();
                if (Objects.isNull(newImage)) {
                    newImage = new BufferedImage(width, height * pages, BufferedImage.TYPE_INT_RGB);  // 默认所有的pdf高度都一致
                }
                data = new int[width * height];
                newImage.setRGB(0, i * height, width, height, image.getRGB(0, 0, width, height, data, 0, width), 0, width);
                image.getGraphics().dispose();
            }
            if (newImage != null) {
                ImageIO.write(newImage, imageType, outFile);// 写图片
                newImage.getGraphics().dispose();
            }
        }
        return outFile;
    }


    public static FastByteArrayOutputStream pdf2ImageDifferentHeight(Resource resource) throws IOException {
        FastByteArrayOutputStream outFile = new FastByteArrayOutputStream();
        String imageType = "jpg";
        List<int[]> images; //一份pdf会有多页,存储每页pdf转为图片后的RGB数据
        int[] allImageHeight; //记录所有的图片的高度,先创建好新的图片的高度,然后逐块的填充

        //临时变量
        int width = 0, height; //每张图片的宽度和高度, 宽度必须一致,否则会报错,高度可以不一致
        int dstHeight = 0; //所有的图片合在一起时总共的高度
        int[] data;   //图片内的数据
        BufferedImage image;
        try (InputStream pdfFile = resource.getInputStream();
             PDDocument pdDocument = PDDocument.load(pdfFile, MemoryUsageSetting.setupMainMemoryOnly())) {
            PDFRenderer renderer = new PDFRenderer(pdDocument);

            int pages = pdDocument.getNumberOfPages();
            images = new ArrayList<>(pages);
            allImageHeight = new int[pages];
            for (int i = 0; i < pages; i++) {

                //PDF可能有多页 将PDF的每一页渲染成一张图片
                image = renderer.renderImage(i, 2.7f); //这个数值属于比较清晰,又带点模糊的边界,小于这个值开始出现重影
                width = image.getWidth();
                height = image.getHeight();
                dstHeight += height;
                allImageHeight[i] = height; // 保存height,后续合并使用
                data = new int[width * height];
                images.add(image.getRGB(0, 0, width, height, data, 0, width));
            }

            BufferedImage imageNew = new BufferedImage(width, dstHeight, BufferedImage.TYPE_INT_RGB);
            int tempHeight = 0; //开始的 图片y轴为0
            for (int i = 0; i < images.size(); i++) {
                imageNew.setRGB(0, tempHeight, width, allImageHeight[i],
                        images.get(i), 0, width);
                tempHeight += allImageHeight[i]; //将高度下移,准备填充下面的区域
            }
            ImageIO.write(imageNew, imageType, outFile);// 写图片

            images.clear();

        }
        return outFile;
    }

    public static FastByteArrayOutputStream test(Resource resource) throws IOException {
        FastByteArrayOutputStream outFile = new FastByteArrayOutputStream();
        try (InputStream pdfFile = resource.getInputStream();
             PDDocument pdDocument = PDDocument.load(pdfFile, MemoryUsageSetting.setupMainMemoryOnly())) {
            PDFRenderer renderer = new PDFRenderer(pdDocument);

            int pages = pdDocument.getNumberOfPages();
            for(int i=0;i<pages;i++){
                BufferedImage bufferedImage = renderer.renderImageWithDPI(i,100);
                ImageIO.write(bufferedImage,"jpg",outFile);
            }


        }
        return outFile;
    }

    public static FastByteArrayOutputStream pdf2ImageByDisk(Resource resource) throws IOException {

        FastByteArrayOutputStream outFile = new FastByteArrayOutputStream();
        String imageType = "jpg";

        try (InputStream pdfFile = resource.getInputStream();
             PDDocument pdDocument = PDDocument.load(pdfFile, MemoryUsageSetting.setupTempFileOnly())) {
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            List<BufferedImage> bufferedImages =  new ArrayList<>();

            int pages = pdDocument.getNumberOfPages();
            int height = 0, width = 0;
            BufferedImage image;
            for (int i = 1; i <= pages; i++) {
                //PDF可能有多页 将PDF的每一页渲染成一张图片
                image = renderer.renderImage(i-1, 2.7f); //这个数值属于比较清晰,又带点模糊的边界,小于这个值开始出现重影
                bufferedImages.add(image);
                width = image.getWidth();
                height+=image.getHeight();
            }

            image = new BufferedImage(width,height,1);
            int tempHeight = 0;
            for(BufferedImage temp:bufferedImages){
                image.getGraphics().drawImage(temp,0,tempHeight,null);
                tempHeight+=temp.getHeight();
            }

            ImageIO.write(image, imageType, outFile);// 写图片
        }

        return outFile;
    }
}
