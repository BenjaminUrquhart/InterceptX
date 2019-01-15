package net.intercept.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class Drawer extends Canvas{
	
	private Window window;
	private Font font;
	
	private ScheduledExecutorService blinker;
	
	private volatile StringBuilder buffer, temp, input;
	private volatile ArrayList<String> history, windowBuff;
	
	private volatile STDIN stdin;
	private volatile STDOUT stdout;
	
	private volatile int cursorPos, historyPos;
	private volatile boolean cursor;

	protected Drawer(Window window) {
		this.setBackground(Color.BLACK);
		this.addKeyListener(new KeyListener(this));
		this.repaint();
		this.buffer = new StringBuilder();
		this.input = new StringBuilder();
		this.history = new ArrayList<>();
		this.windowBuff = new ArrayList<>();
		this.stdin = new STDIN();
		this.stdout = new STDOUT(this);
		this.cursorPos = 0;
		this.historyPos = 0;
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
		System.setOut(new PrintStream(stdout));
	}
	protected void append(char c) {
		//System.err.println(c);
		if(c == 8) {
			delete();
		}
		else if(c == 10) {
			if(buffer.toString().trim().equals("quit")) {
				System.exit(0);
			}
			if(buffer.length() != 0) {
				stdin.write(c);
				history.add(buffer.toString());
				windowBuff.add(">> " + buffer);
				buffer.delete(0, buffer.length());
				cursorPos = 0;
				historyPos = 0;
			}
		}
		else {
			stdin.write(c);
			buffer.insert(cursorPos, c);
			incrementPos();
		}
		System.err.println(buffer);
		cursor = true;
		this.repaint();
	}
	protected void delete() {
		if(buffer.length() <= 0) return;
		if(cursorPos > buffer.length()) {
			cursorPos = buffer.length();
		}
		if(cursorPos > 0) {
			buffer.deleteCharAt(cursorPos - 1);
		}
		this.decrementPos();
		this.repaint();
	}
	protected void incrementPos() {
		this.cursorPos++;
		if(cursorPos > buffer.length()) {
			cursorPos = buffer.length();
		}
		cursor = true;
		this.repaint();
	}
	protected void decrementPos() {
		this.cursorPos--;
		if(cursorPos < 0) cursorPos = 0;
		cursor = true;
		this.repaint();
	}
	protected void historyUp() {
		if(historyPos == 0) {
			temp = buffer;
		}
		if(historyPos < history.size()) {
			historyPos++;
			buffer = new StringBuilder(history.get(history.size() - historyPos));
		}
		cursorPos = buffer.length();
		this.repaint();
	}
	protected void historyDown() {
		if(historyPos > 0) {
			buffer = new StringBuilder(history.get(history.size() - historyPos));
			historyPos--;
		}
		else if(historyPos == 0 && temp != null){
			buffer = temp;
			temp = null;
		}
		cursorPos = buffer.length();
		this.repaint();
	}
	protected void appendToBuff(String s) {
		windowBuff.add(s);
		this.repaint();
	}
	protected void appendToBuff(char c) {
		if(c == 10) {
			appendToBuff(input.toString());
		}
		else {
			input.append(c);
		}
	}
	protected InputStream getSTDIN() {
		return stdin;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(font);
		g.setColor(Color.GREEN);
		int i = windowBuff.size();
		for(String s : windowBuff) {
			g.drawString(s, 0, window.getHeight() - 37*(i+1));
			i--;
		}
		g.drawString(">> " + buffer.toString(), 0, window.getHeight() - 50);
		if(cursor) {
			g.fillRect(cursorPos*12 + 36, window.getHeight() - 65, 12, 20);
		}
	}
}
