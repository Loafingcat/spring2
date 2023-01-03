package kr.co.loafingcat.mvc.domain;

public enum ThunbnailType {

	WEB_MAIN(500, 300),
	WEB_SUB(150, 70),
	;
	ThunbnailType(int width, int height) {
		this.width = width;
		this.height = height;
	}
	;
	
	private int width;
	private int height;
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
}
