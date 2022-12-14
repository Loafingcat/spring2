<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example</title>
</head>
<body>
	<c:forEach var="id" items="${ids}">
		<p>${id}</p>
	</c:forEach>
	<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
	$(function() {
		var json = {
				user: {
					name: '홍길동',
					age: 14,
					address: '대한민국'
				}
			};
		console.log(json);
		$.ajax({
			url: '/example/parameter/example6/saveData',
			type: 'post',
			data: JSON.stringify(json),
			contentType: 'application/json',
			dataType: 'json',
			success: function(data) {
				console.log(data);
			}
		});
	});
	</script>
</body>
</html>