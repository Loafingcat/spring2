package kr.co.loafingcat.mvc.domain;

public enum MenuType {
	
	community(BoardType.COMMUNITY),
    notice(BoardType.NOTICE),
    faq(BoardType.FAQ),
    inquiry(BoardType.INQUIRY),
    ;
	
	private BoardType boardType;
	
	private MenuType(BoardType boardType) {
		this.boardType = boardType;
	}
	
	public BoardType boarType() {
		return boardType;
	}
}