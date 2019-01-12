package net.intercept.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class Drawer extends Canvas{
	
	private Window window;
	private Font font;
	
	private ScheduledExecutorService blinker;
	
	private volatile StringBuilder buffer;
	
	private volatile int cursorPos;
	private volatile boolean cursor;

	protected Drawer(Window window) {
		this.setBackground(Color.BLACK);
		this.addKeyListener(new KeyListener(this));
		this.repaint();
		this.buffer = new StringBuilder();
		this.cursorPos = 0;
		this.window = window;
		this.blinker = Executors.newScheduledThreadPool(5);
		this.cursor = false;
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, new File("OCR-A.ttf")).deriveFont(20f);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.blinker.scheduleWithFixedDelay(() -> {
			cursor = !cursor;
			this.repaint();
		}, 0, 750, TimeUnit.MILLISECONDS);
	}
	protected void append(char c) {
		//System.out.println(c);
		if(c == 8) {
			delete();
		}
		else {
			buffer.append(c);
			incrementPos();
		}
		cursor = true;
		this.repaint();
	}
	//broken for now
	protected void delete() {
		if(buffer.length() <= 0) return;
		buffer.deleteCharAt(cursorPos - 1);
		this.cursorPos--;
		this.repaint();
	}
	protected void incrementPos() {
		this.cursorPos++;
	}
	protected void decrementPos() {
		this.cursorPos--;
		if(cursorPos < 0) cursorPos = 0;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(font);
		System.out.println(buffer);
		g.setColor(Color.GREEN);
		g.drawString(buffer.toString(), 0, window.getHeight() - 50);
		if(cursor) {
			g.fillRect(cursorPos*12, window.getHeight() - 65, 12, 20);
		}
	}
}
