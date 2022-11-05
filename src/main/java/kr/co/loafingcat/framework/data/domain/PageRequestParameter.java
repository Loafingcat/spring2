package kr.co.loafingcat.framework.data.domain;

import lombok.Data;


/**
 * 페이지 요청 정보와 파라미터 정보
 * */
@Data
public class PageRequestParameter<T> {
	
	private MySQLPageRequest pageRequest;
	private T parameter;
	
	public PageRequestParameter(MySQLPageRequest pageRequest, T parameter) {
		this.pageRequest = pageRequest;
		this.parameter = parameter;
	}

}
