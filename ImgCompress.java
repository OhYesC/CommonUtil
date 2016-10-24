package com.ljzforum.platform.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片压缩程序
 * @author yin.zhang
 *
 */
public class ImgCompress {
	protected Logger logger = Logger.getLogger(this.getClass());
	private String realPath;
	private String fileName;
	private BufferedImage imgIn;
	public boolean exists=true;
	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BufferedImage getImgIn() {
		return imgIn;
	}

	public void setImgIn(BufferedImage imgIn) {
		this.imgIn = imgIn;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private int width;
	private int height;

//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) throws Exception {
//		
//		
//		System.out.println(System.getProperty("user.dir"));
//        System.out.println(new File("").getAbsolutePath());
//        //获取到clsspath绝对路径
//        System.out.println(ImgCompress.class.getResource("/"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//        System.out.println(ImgCompress.class.getClassLoader().getResource(""));
//        
////        
////		System.out.println("开始：" + new Date().toLocaleString());
////		ImgCompress imgCom = new ImgCompress("http://g-search3.alicdn.com/img/bao/uploaded/i4/i2/TB1nUJpHVXXXXc2XXXXXXXXXXXX_!!0-item_pic.jpg");
////		BufferedImage imgOut =  imgCom.resize(400, 400);
////		// 输出图像到页面
////		//ImageIO.write(imgOut, "JPEG", os);
////		System.out.println("结束：" + new Date().toLocaleString());
//
//	}

	/**
	 * 构造函数
	 */
	public ImgCompress(String fileName,String realPath) throws IOException {
		this.fileName = fileName;
		this.realPath = realPath;
	/*	if(!fileName.startsWith("\\")){
			fileName="\\"+fileName;
		}*/
		File file = new File(realPath,fileName);
		if(!file.exists()){
			exists=false;
		}else{
//			System.out.println(file.exists());
			imgIn = ImageIO.read(file); // 构造Image对象
			width = imgIn.getWidth(null); // 得到源图宽
			height = imgIn.getHeight(null); // 得到源图长
		}
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w int 最大宽度
	 * @param h int 最大高度
	 */
	public void resizeFix(int w, int h,OutputStream os) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w,os);
		} else {
			resizeByHeight(h,os);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public void resizeByWidth(int w,OutputStream os) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h, os);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public void resizeByHeight(int h,OutputStream os) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h,os);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public void resize(int w, int h,OutputStream os) throws IOException {
		File destFile = new File(realPath+fileName+"_"+w+"x"+h+".jpg");
		BufferedImage bi;
		//如果图片存在，直接返回图片信息
		if(destFile.exists()){
			bi = ImageIO.read(destFile);
		}else{
			// SCALE_SMOOTH /TYPE_INT_RGB  的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
			bi = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			Graphics g2 =bi.getGraphics();
			g2.drawImage(imgIn, 0, 0, w, h, null); // 绘制缩小后的图
			g2.dispose();
			FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bi); // JPEG编码
			out.close();
		}
		ImageIO.write(bi, "JPEG", os);
		bi.flush();
		imgIn.flush();
	}
}
