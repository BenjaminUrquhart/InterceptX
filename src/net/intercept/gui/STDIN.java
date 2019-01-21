package net.intercept.gui;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class STDIN {

	private volatile List<String> next;
	
	protected STDIN() {
		this.next = Collections.synchronizedList(new ArrayList<>());
	}
	public String nextLine() {
		while(next.isEmpty()) {}
		return next.remove(0);
	}
	protected void write(String s) {
		next.add(s);
	}
}
