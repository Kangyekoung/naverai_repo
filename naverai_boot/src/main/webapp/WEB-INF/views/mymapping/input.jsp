<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function(){
	//구현
	$("#ajaxbtn").on("click", function(){
		$.ajax({
			url : "/myoutput",
			data :{'request' : $("#requeset").val()}, //controller 넘길 값
			type : "get",	//전송방식
			dataType : "json", //결과타입
			success : function(server){ //성공
				//controller return 값 
				// server ={"response" : 텍스트답변 , "mp3" : mp3파일명}
				$("#response").html(server.response);
				$("#mp3").atr("src", "/naverimages/" + server.mp3);
			},
			error : function(){ //실패
				alert(e);
			}
		});//ajax end
	});//on end
});//ready end
</script>
</head>
<body>
<!-- ajax 사용 안한 코드
 <form action="/myoutput" method="post">
질문 : <input type=text name="request" />
<input type="submit" value="대화" />
</form> -->

질문 : <input type=text id="request" name="request" />
<input id="ajaxbtn" type="button" value="대화" />


<h1>답변</h1>
<h3>답변(텍스트)<div id="response"></div></h3>
<audio id="mp3" controls > </audio>

</body>
</html>