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
	$("#ajaxbtn").on("click", function(){  
		$.ajax({
			url :"/myoutput", 
			data : {'request' : $("#request").val()},
			type : "get",
			dataType :"json",
			success :function(server){
				//server = {"response" : 텍스트답변 , "mp3":mp3파일명 }
				$("#response").html(server.response);
				$("#mp3").attr("src" , "/naverimages/" + server.mp3);
			},
			error : function(e){
				alert(e);
			}
				
			
		});//ajax end
		
	} );//on end
});//ready end
</script>
</head>
<body>
<!-- <form action="/myoutput" >
질문 : <input type=text  name="request">
<input type=submit value="대화">
</form> -->

질문 : <input type=text  id="request" name="request">
<input id="ajaxbtn" type=button value="대화">


<H3>답변(텍스트)<div id="response"></div></H3>
<audio id="mp3" src="" controls ></audio>
</body>
</html>