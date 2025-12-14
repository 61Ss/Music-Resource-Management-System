package com.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 
 * @author superman
 */
public class CpachaUtil {
	

	final private char[] code = {
		'2', '3', '4', '5', '6', '7', '8', '9'
	};

	final private String[] fontNames = new String[]{
			"黑体", "宋体", "Courier", "Arial", 
			"Verdana", "Times", "Tahoma", "Georgia"};

	final private int[] fontStyles = new int[]{
			Font.BOLD, Font.ITALIC|Font.BOLD
	};
	
	private int vcodeLen = 4;

	private int fontsize = 21;

	private int width = (fontsize+1)*vcodeLen+10;

	private int height = fontsize+12;

	private int disturbline = 3;
	
	
	public CpachaUtil(){}
	
	/**
	 * @param vcodeLen 
	 */
	public CpachaUtil(int vcodeLen) {
		this.vcodeLen = vcodeLen;
		this.width = (fontsize+1)*vcodeLen+10;
	}
	
	/**
	 * @param vcodeLen
	 * @param width
	 * @param height
	 */
	public CpachaUtil(int vcodeLen,int width,int height) {
		this.vcodeLen = vcodeLen;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @param vcode 
	 * @param drawline 
	 * @return
	 */
	public BufferedImage generatorVCodeImage(String vcode, boolean drawline){
		BufferedImage vcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = vcodeImage.getGraphics();
		g.setColor(new Color(246, 240, 250));
		g.fillRect(0, 0, width, height);
		if(drawline){
			drawDisturbLine(g);
		}
		Random ran = new Random();

		for(int i = 0;i < vcode.length();i++){

			g.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], fontsize));

			g.setColor(getRandomColor());

			g.drawString(vcode.charAt(i)+"", i*fontsize+10, fontsize+5);
		}
		
		g.dispose();
		
		return vcodeImage;
	}
	/**
	 * @param vcode
	 * @param drawline 
	 * @return
	 */
	public BufferedImage generatorRotateVCodeImage(String vcode, boolean drawline){
	
		BufferedImage rotateVcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = rotateVcodeImage.createGraphics();

		g2d.setColor(new Color(246, 240, 250));
		g2d.fillRect(0, 0, width, height);
		if(drawline){
			drawDisturbLine(g2d);
		}
	
		for(int i = 0;i < vcode.length();i++){
			BufferedImage rotateImage = getRotateImage(vcode.charAt(i));
			g2d.drawImage(rotateImage, null, (int) (this.height * 0.7) * i, 0);
		}
		g2d.dispose();
		return rotateVcodeImage;
	}
	/**
	 * @return 
	 */
	public String generatorVCode(){
		int len = code.length;
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < vcodeLen;i++){
			int index = ran.nextInt(len);
			sb.append(code[index]);
		}
		return sb.toString();
	}
	/**
	 * @param g 
	 */
	private void drawDisturbLine(Graphics g){
		Random ran = new Random();
		for(int i = 0;i < disturbline;i++){
			int x1 = ran.nextInt(width);
			int y1 = ran.nextInt(height);
			int x2 = ran.nextInt(width);
			int y2 = ran.nextInt(height);
			g.setColor(getRandomColor());
			g.drawLine(x1, y1, x2, y2);
		}
	}
	/**
	 * @param c 
	 * @return
	 */
	private BufferedImage getRotateImage(char c){
		BufferedImage rotateImage = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotateImage.createGraphics();
		g2d.setColor(new Color(255, 255, 255, 0));
		g2d.fillRect(0, 0, height, height);
		Random ran = new Random();
		g2d.setFont(new Font(fontNames[ran.nextInt(fontNames.length)], fontStyles[ran.nextInt(fontStyles.length)], fontsize));
		g2d.setColor(getRandomColor());
		double theta = getTheta();
		g2d.rotate(theta, height/2, height/2);
		g2d.drawString(Character.toString(c), (height-fontsize)/2, fontsize+5);
		g2d.dispose();
		
		return rotateImage;
	}
	/**
	 * @return 
	 */
	private Color getRandomColor(){
		Random ran = new Random();
		return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220)); 
	}
	/**
	 * @return 
	 */
	private double getTheta(){
		return ((int) (Math.random()*1000) % 2 == 0 ? -1 : 1)*Math.random();
	}

	/**
	 * @return 
	 */
	public int getVcodeLen() {
		return vcodeLen;
	}
	/**
	 * @param vcodeLen
	 */
	public void setVcodeLen(int vcodeLen) {
		this.width = (fontsize+3)*vcodeLen+10;
		this.vcodeLen = vcodeLen;
	}
	/**
	 * @return 
	 */
	public int getFontsize() {
		return fontsize;
	}
	/**
	 * @param fontsize
	 */
	public void setFontsize(int fontsize) {
		this.width = (fontsize+3)*vcodeLen+10;
		this.height = fontsize+15;
		this.fontsize = fontsize;
	}
	/**
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height 
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return
	 */
	public int getDisturbline() {
		return disturbline;
	}
	/**
	 * @param disturbline
	 */
	public void setDisturbline(int disturbline) {
		this.disturbline = disturbline;
	}
	
}
