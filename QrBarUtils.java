package com.ljzforum.platform.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
/**
 * 矩阵型二维码生成器
 * @author yin.zhang
 *
 */
public class QrBarUtils {
	
    private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;  
    private QrBarUtils() {  
    }  
    
    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;  
    }  
    
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, file)) {  
            throw new IOException("Could not write an image of format " + format + " to " + file);  
        }  
    }  
  
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, stream)) {  
            throw new IOException("Could not write an image of format " + format);  
        }  
    } 
    
    /**
     * 生成矩阵型二维码
     * @param code 字符串
     * @param format 图片格式
     * @param width  图片宽度
     * @param height 图片高度
     * @param file   输出的文件
     */
    public static void generateQrCode(String code,String format,int width,int height, File file){
		try {
	        BitMatrix bitMatrix = getBitMatrix(code, width, height);
			QrBarUtils.writeToFile(bitMatrix,format,file);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 生成矩阵型二维码
     * @param code 字符串
     * @param width  宽度
     * @param height 高度
     */
    public static BufferedImage generateQrCode(String code,int width,int height){
		try {
	        BitMatrix bitMatrix = getBitMatrix(code, width, height);
			return QrBarUtils.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
    }
    /**
     * 生成矩阵型二维码
     * @param out  输出数据
     * @param code 字符串
     * @param format 图片格式(jpg\png\jpeg)
     * @param width  宽度
     * @param height 高度
     */
    public static void generateQrCode(OutputStream out,String code,String format,int width,int height){
		try {
	        BitMatrix bitMatrix = getBitMatrix(code, width, height);
			QrBarUtils.writeToStream(bitMatrix, format, out);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private static BitMatrix getBitMatrix(String code, int width, int height) throws WriterException {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
		return bitMatrix;
	}
	
	
    public static void main(String[] args) throws Exception {  
        String text = "NiuYueYue I Love You！"; // 二维码内容  
        int width = 300; // 二维码图片宽度  
        int height = 300; // 二维码图片高度  
        String format = "gif";// 二维码的图片格式  
          
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
          
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);  
        // 生成二维码  
        File outputFile = new File("d:" + File.separator + "new.gif");  
        QrBarUtils.writeToFile(bitMatrix, format, outputFile);  
    }  
}  
