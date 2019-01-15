package net.intercept.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class STDIN extends InputStream{

	private volatile List<Character> buffer;
	
	protected STDIN() {
		this.buffer = new ArrayList<>();
	}
	@Override
	public int read() throws IOException {
		while(buffer.isEmpty()) {}
		return buffer.remove(0);
	}
	@Override
	public int available() {
		return buffer.size();
	}
	
	protected void write(char c) {
		buffer.add(c);
	}
}
