package com.ruoyi.common.utils.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * PDF工具类，基于itext
 *
 * @author Henriport
 * @since 2021/11/19
 */
public class PDFUtils {
public static byte[] signPdf(String pdfUrl, String sealUrl) throws DocumentException, IOException {
        Document document = null;
        PdfStamper stamper = null;
        PdfReader reader = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            // pdf文件路径
            URL url = new URL(pdfUrl);
            // 读取pdf文件
            reader = new PdfReader(url);
            // 获取页数
            int pageCount = reader.getNumberOfPages();
            // 生成副本
            stamper = new PdfStamper(reader, outputStream);
            // 生成document
            document = new Document(reader.getPageSize(1));
            // 获取页面宽度
            float width = document.getPageSize().getWidth();
            // 获取页面高度
            float height = document.getPageSize().getHeight();
            System.out.println("width = " + width + ", height = " + height);
            // 读取seal
            Image sealImage = Image.getInstance(sealUrl);
            // 根据域的大小缩放印章
            sealImage.scaleToFit(150, 150);
            // 设置印章位置
            sealImage.setAbsolutePosition(width - 150 - 50, 50);
            // 在最后一页盖章
            PdfContentByte sign = stamper.getOverContent(pageCount);
            sign.addImage(sealImage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                data = outputStream.toByteArray();
                reader.close();
            }
            return data;
        }
    }

}
