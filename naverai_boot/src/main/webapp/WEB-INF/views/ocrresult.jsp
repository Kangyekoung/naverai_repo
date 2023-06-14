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
	var json = JSON.parse('${ocrresult }');//  string-->JSON 객체 변환 (JSON.parse). json.변수명
 	$("#output").html(JSON.stringify(json) ); //  json --> string  변환 (JSON.stringify) {'변수명':"값".....}

 	let mycanvas = document.getElementById("ocrcanvas");//자바스크립트객체
	let mycontext = mycanvas.getContext("2d");
	let myimage = new Image();
	myimage.src = "/naverimages/${param.image}";
	
	myimage.onload = function(){
		if(myimage.width > mycanvas.width){
			mycanvas.width = myimage.width ;
		}
		mycontext.drawImage(myimage, 0, 0, myimage.width, myimage.height);
		//이미지 글씨 박스화
		let fieldslist = json.images[0].fields;//배열 단어갯수만큼
		for(let i in fieldslist){
			if(fieldslist[i].lineBreak == true){
			$("#output2").append(fieldslist[i].inferText + "<br>");
			}
			else{
				$("#output2").append(fieldslist[i].inferText + "&nbsp;");
			}
			
			var x = fieldslist[i].boundingPoly.vertices[0].x;//단어시작x좌표
			var y = fieldslist[i].boundingPoly.vertices[0].y;//단어시작y좌표	
			var width = fieldslist[i].boundingPoly.vertices[1].x - x;//단어가로크기
			var height = fieldslist[i].boundingPoly.vertices[2].y - y;//단어세로크기
			
			mycontext.strokeStyle="blue";
			mycontext.lineWidth = 2;
			mycontext.strokeRect(x,y,width,height);
		}//for end
		
	}//myimage.onload 

});//ready
</script>
</head>
<body>
<div id="output" style="border:2px solid orange"></div>
<div id="output2" style="border:2px solid green"></div>
<canvas id="ocrcanvas" style="border:2px solid silver" width="500" height="500"></canvas>
</body>
</html>