package kr.co.loafingcat.mvc.parameter;

import java.util.List;

import kr.co.loafingcat.mvc.domain.MenuType;
import lombok.Data;

/**
 * 게시물 검색 파라미터
 * 
 * @author loafingcat
 */
@Data
public class BoardSearchParameter {

	private String keyword;
	private List<MenuType> boardTypes;

	public BoardSearchParameter() {

	}
}
