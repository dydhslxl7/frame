<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second</title>
<script type="text/javascript" src="/second/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(function(){
	//테스트 1 버튼을 클릭했을 때, 서버 컨트롤러 메소드로 값 보내기
	$("#test1").on("click", function(){
		$.ajax({
			url : "test1.do",
			data : {name: $("#name").val(), age: $("#age").val()},
			type : "post",
			success: function(result){
				if(result == "ok"){
					alert("값 보내기 성공!");
					console.log("result : "+ result);
					$("#d1").html("<h5>" + result + "</h5>");
				}else { //'ok'가 아닌 다른 문자열 값이면
					alert("값 전송 실패!");
					console.log("result : "+ result);
				}
			},
			error : function(reqest, status, errorData){
				console.log("error code : " + request.status
						+ "\nMessage : " + request.responseText
						+ "\nError : " + errorData);
			}
		}); //ajax
	}); //test1 click
}); //document.ready
</script>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<hr>
<h1>spring second : Ajax test page</h1>
<ol>
<li>서버쪽 컨트롤러 메소드로 값 보내기 <button id="test1">테스트 1</button></li>
이름 입력 : <input type="text" id="name"> <br>
나이 입력 : <input type="number" id="age">
<p><div id="d1"></div></p>
<li>컨트롤러에서 보낸 json 객체 받아서 출력하기 <button id="test2">테스트 2</button></li>
<p><div id="d2"></div></p>
<li>컨트롤러에서 보낸 json 배열 받아서 출력하기 <button id="test3">테스트 3</button></li>
<p><div id="d3"></div></p>
<li>컨트롤러에서 보낸 Map 객체 받아서 출력하기 <button id="test4">테스트 4</button></li>
<p><div id="d4"></div></p>
<li>컨트롤러로 json 객체 보내기 <button id="test5">테스트 5</button></li>
<p><div id="d5"></div></p>
<li>컨트롤러로 json 배열 보내기 <button id="test6">테스트 6</button></li>
<p><div id="d6"></div></p>
</ol>


<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>