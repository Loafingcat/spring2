package kr.co.loafingcat.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.loafingcat.framework.data.domain.PageRequestParameter;
import kr.co.loafingcat.mvc.domain.Board;
import kr.co.loafingcat.mvc.parameter.BoardParameter;
import kr.co.loafingcat.mvc.parameter.BoardSearchParameter;

/*
 * 게시판 Repository
 * @author loafingCat
 * */
@Repository//보드리포지토리도 스프링 빈에 등록한 것이다.
public interface BoardRepository {

	List<Board> getList(PageRequestParameter<BoardSearchParameter> pageRequestParameter);
	
	Board get(int boardSeq);
	
	void save(BoardParameter board);
	
	void saveList(Map<String, Object> paramMap);
	
	void update(BoardParameter board);
	
	void delete(int boardSeq);
	
}
