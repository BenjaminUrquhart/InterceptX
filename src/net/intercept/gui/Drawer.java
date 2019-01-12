package net.intercept.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

@SuppressWarnings("serial")
public class Drawer extends Canvas{
	
	private Window window;
	private Font font;
	
	private String buffer;
	
	private int cursorPos;

	protected Drawer(Window window) {
		this.setBackground(Color.BLACK);
		this.addKeyListener(new KeyListener(this));
		this.repaint();
		this.buffer = "";
		this.cursorPos = 0;
		this.window = window;
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, new File("OCR-A.ttf"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	protected void append(char c) {
		System.out.println(c);
		if(c == 8) {
			buffer += c;
		}
		else {
			buffer += c;
		}
		incrementPos();
		this.repaint();
	}
	protected void delete() {
		buffer = buffer.substring(0, cursorPos) + (buffer.length() < cursorPos ? buffer.substring(cursorPos + 1, buffer.length()) : "");
		this.decrementPos();
		this.repaint();
	}
	protected void incrementPos() {
		if(this.cursorPos < buffer.length()) this.cursorPos++;
	}
	protected void decrementPos() {
		if(this.cursorPos > 0) this.cursorPos--;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//g.setFont(font);
		System.out.println(buffer);
		g.setColor(Color.GREEN);
		g.drawString(buffer, 0, window.getHeight() - 50);
	}
}
