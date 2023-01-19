<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<form id="form" method="post" action="/save">
			<input type="hidden" name="boardSeq" value="${board == null ? 0 : board.boardSeq}" />
			<div class="row mb-3">
				<label for="title" class="col-sm-2 col-form-label"><spring:message code="board.title" /></label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="title" value=" ${board.title}" id="title" placeholder="<spring:message code="placeholder.required" />">
				</div>
			</div>
			<div class="row mb-3">
				<label for="contents" class="col-sm-2 col-form-label"><spring:message
						code="board.contents" /></label>
				<div class="col-sm-10">
					<textarea class="form-control" name="contents" id="contents"
						placeholder="<spring:message code="placeholder.required" />">${board.contents}</textarea>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">
				<spring:message code="button.save" />
			</button>
		</form>
	</div>
	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
	$(function() {
		
		var $form = $('#form'); 
		$form.bind('submit', function() {
		$.ajax({
			url: '/${menuType}/save',
			type: 'post',
			data: $form.serialize(),
			dataType: 'json',
			success: function(response) {
				console.log(response);
				if (response.code == 'SUCCESS') {
					alert('저장되었습니다.')
					location.href = '/${menuType}/' + response.data;
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