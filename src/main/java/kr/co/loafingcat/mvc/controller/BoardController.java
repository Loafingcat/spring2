package kr.co.loafingcat.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.loafingcat.configuration.exception.BaseException;
import kr.co.loafingcat.configuration.http.BaseResponse;
import kr.co.loafingcat.configuration.http.BaseResponseCode;
import kr.co.loafingcat.framework.data.domain.MySQLPageRequest;
import kr.co.loafingcat.framework.data.domain.PageRequestParameter;
import kr.co.loafingcat.framework.web.bind.annotation.RequestConfig;
import kr.co.loafingcat.mvc.domain.Board;
import kr.co.loafingcat.mvc.domain.MenuType;
import kr.co.loafingcat.mvc.parameter.BoardParameter;
import kr.co.loafingcat.mvc.parameter.BoardSearchParameter;
import kr.co.loafingcat.mvc.service.BoardService;

/*
 * 게시판 컨트롤러
 * @author loafingcat
 * */

@Controller
@RequestMapping("/board")
@Api(tags = "게시판 API")//클래스를 Swagger 리소스 대상으로 표시
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired//보드컨트롤러에서 보드서비스로 연결
	private BoardService boardService;

	/*
	 * 목록 리턴
	 * 
	 * @return
	 */
	@GetMapping("/{menuType}")
	public String list(@PathVariable MenuType menuType, BoardSearchParameter parameter, MySQLPageRequest pageRequest, Model model) {
		logger.info("menuType : {}", menuType);
		logger.info("pageRequest : {}", pageRequest);
		PageRequestParameter<BoardSearchParameter> pageRequestParameter = new PageRequestParameter<BoardSearchParameter>(pageRequest, parameter);
		List<Board> boardList = boardService.getList(pageRequestParameter);
		model.addAttribute("boardList", boardList);
		return "/board/list";
	}

	/*
	 * 상세 정보 리턴 
	 * 
	 * @param boarSeq
	 * 
	 * @return
	 */
	@GetMapping("/detail/{boardSeq}")
	public String detail(@PathVariable int boardSeq, Model model) {
		Board board = boardService.get(boardSeq);
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}
		model.addAttribute("board", board);
		return "/board/detail";
	}

	/**
	 * 등록화면
	 * 
	 * */
	@GetMapping("/form")
	@RequestConfig(loginCheck = false)
	public void form(BoardParameter parameter, Model model) {
		model.addAttribute("parameter", parameter);
	}
	
	/**
	 * 수정 화면
	 * 
	 * */
	@GetMapping("/edit/{boardSeq}")
	@RequestConfig(loginCheck = false)
	public String edit(@PathVariable(required = true) int boardSeq, BoardParameter parameter, Model model) {
		Board board = boardService.get(parameter.getBoardSeq());
		// null 처리
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}
		model.addAttribute("board", board);
		model.addAttribute("parameter", parameter);
		return "/board/form";
	}
	
	
	
	/*
	 * 등록 처리
	 * 
	 * @param board
	 */
	
	@PostMapping("/save")
	@RequestConfig(loginCheck = false)//만든 어노테이션인 RequestConfig 로그인체크 true/false 여기서 조절
	// @PutMapping
	// @PostMapping 개발하는 추세로 보면 이 둘을 쓰는게 맞지만 아직 테스트할 환경이 되지 않으니
	// GetMapping으로 함. 실무에서는 데이터를 저장하거나 삭제할 때 Get은 웬만하면 쓰지 않는게 좋음
	@ResponseBody
	@ApiOperation(value = "등록/ 수정 처리", notes = "신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"),
			@ApiImplicitParam(name = "title", value = "제목", example = "spring"),
			@ApiImplicitParam(name = "contents", value = "내용", example = "spring 강좌"), })
	public BaseResponse<Integer> save(BoardParameter parameter) {
		
		// 제목 필수 체크
		if (ObjectUtils.isEmpty(parameter.getTitle())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "title", "제목" });
		}
		// 내용 필수 체크
		if (ObjectUtils.isEmpty(parameter.getContents())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "contents", "내용" });
		}
		boardService.save(parameter);
		return new BaseResponse<Integer>(parameter.getBoardSeq());
		
	}

	/**
	 * 대용량 등록 처리.
	 */
	@ApiOperation(value = "대용량 등록처리1", notes = "대용량 등록처리1")
	@PutMapping("/saveList1")
	public BaseResponse<Boolean> saveList1() {
		int count = 0;
		// 테스트를 위한 랜덤 1000건의 데이터를 생성
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while (true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
			list.add(new BoardParameter(title, contents));
			if (count >= 10000) {
				break;
			}
		}
		long start = System.currentTimeMillis();
		boardService.saveList1(list);
		long end = System.currentTimeMillis();
		logger.info("실행 시간 : {}", (end - start) / 1000.0);
		return new BaseResponse<Boolean>(true);
	}

	/**
	 * 대용량 등록 처리.
	 */
	@PutMapping("/saveList2")
	@ApiOperation(value = "대용량 등록처리2", notes = "대용량 등록처리2")
	public BaseResponse<Boolean> saveList2() {
		int count = 0;
		// 테스트를 위한 랜덤 1000건의 데이터를 생성
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while (true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
			list.add(new BoardParameter(title, contents));
			if (count >= 10000) {
				break;
			}
		}
		long start = System.currentTimeMillis();
		boardService.saveList2(list);
		long end = System.currentTimeMillis();
		logger.info("실행 시간 : {}", (end - start) / 1000.0);
		return new BaseResponse<Boolean>(true);
	}

	/*
	 * 삭제 처리
	 * 
	 * @param boardSeq
	 */
	@DeleteMapping("/{boardSeq}")//DeleteMapping 어노테이션이 붙어있기 때문에 HTTP Request를 Delete 방식으로 보냈을 때에만 RestController에서 해당 Request를 받는다.
	@ResponseBody
	@ApiOperation(value = "삭제 처리", notes = "게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"), })
	public BaseResponse<Boolean> delete(@PathVariable int boardSeq) {
		Board board = boardService.get(boardSeq);
		if (board == null) {
			return new BaseResponse<Boolean>(false);
		}
		boardService.delete(boardSeq);
		return new BaseResponse<Boolean>(true);
	}
}
