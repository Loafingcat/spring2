<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="card-header">
			${board.title}
			</div>
			<div class="card-body">
				<blockquote class="blockquote mb-0">
					<p>${board.contents}</p>
					<footer class="blockquote-footer"><fmt:formatDate value="${board.regDate}"pattern="yyyy.MM.dd HH:mm" /></footer>
				</blockquote>
			</div>
		</div>
		<form id="form" method="get" action="/list">
			<div class="row mb-3">
				<label for="title" class="col-sm-2 col-form-label"><spring:message
						code="search.keyword" /></label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="keyword"
						value=" ${parameter.keyword}" id="keyword"
						placeholder="<spring:message code="placeholder.required" />">
				</div>
			</div>
			<button type="submit" class="btn btn-primary">
				<spring:message code="button.search" />
			</button>
			<table class="table caption-top">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col"><spring:message code="board.title" /></th>
						<th scope="col"><spring:message code="board.viewCount" /></th>
						<th scope="col"><spring:message code="board.regDate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="board" items="${boardList}">
						<tr>
							<th scope="row">${status.count}</th>
							<td><a href="/board/${board.boardSeq}">${board.title}</a></td>
							<td>${board.viewCount}</td>
							<td><fmt:formatDate value="${board.regDate}"pattern="yyyy.MM.dd HH:mm" /></td>
						</tr>
					</c:forEach>
					<c:if test="${fn:length(boardList) == 0}">
						<tr>
							<td colspan="4"><spring:message code="msg.board.empty" /></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</form>
	</div>
	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
		$(function() {

			var $form = $('#form');
			$form.bind('submit', function() {
				$.ajax({
					url : '/board/save',
					type : 'post',
					data : $form.serialize(),
					dataType : 'json',
					success : function(data) {
						if (data.code == 'SUCCESS') {

						} else {
							alert(data.message)
						}
						console.log(data);
					}
				});
				return false;
			});
		});
	</script>
</body>
</html>
</html>