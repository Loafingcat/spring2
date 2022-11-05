package kr.co.loafingcat.mvc.parameter;

import kr.co.loafingcat.mvc.domain.BoardType;
import lombok.Data;

@Data
public class BoardParameter {

	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;

	public BoardParameter() {

	}

	public BoardParameter(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
