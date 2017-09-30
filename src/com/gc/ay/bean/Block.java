package com.gc.ay.bean;

public class Block {
	public static final int JKXW = 0, DH = 1, DBSX = 2, RCSH = 3, BKZZ = 4,
			WTHD = 5, GJX = 6, GWSC = 7, ZHSZ = 8, TC = 9;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int color;
	private String name;
	private int ico;
	private int id;

	public Block() {
	}

	public Block(String pName, int pICO, int pColor) {
		color = pColor;
		ico = pICO;
		name = pName;
	}

	public Block(int pId, String pName, int pICO, int pColor) {
		id = pId;
		color = pColor;
		ico = pICO;
		name = pName;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIco() {
		return ico;
	}

	public void setIco(int ico) {
		this.ico = ico;
	}

}
