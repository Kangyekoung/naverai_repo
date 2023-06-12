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
	var json = JSON.parse('${ocrresult }');// string --> JSON 객체 변환 (JSON.parse) 객체 json.변수명
	$("#output").html(JSON.stringify(json));// json --> string 변환(JSON.stringify) {'변수명':"값".....}
	

	let mycanvas = document.getElementById("ocrcanvas");//캔버스 자바스크립트객체
	let mycontext = mycanvas.getContext("2d"); //붓 물감 만들기
	let myimage = new Image(); //이미지 만들기
	myimage.src = "/naverimages/${param.image}"; //controller의 inputresult의 파라미터 image를 가져와라
	
	myimage.onload = function(){
		if(myimage.width > mycanvas.width){ //이미지 크기 조정
			mycanvas.width = myimage.width;
		}
		mycontext.drawImage(myimage, 0,0,myimage.width, myimage.height); //이미지 그려지기
		
		//ocr분석 결과 그리기
		//root>images>>0>>fields
		
		//이미지 글씨 박스화
		let fieldslist = json.images[0].fields;//배열 단어갯수만큼
		for(let i in fieldslist){
			if(fieldslist[i].lineBreak){ 
				//$("#output2").html(fieldslist[i].inferText); //마지막껏만 출력
				$("#output2").append(fieldslist[i].inferText + "<br>"); //지우지 않고 이어서 추가
			}
			else{
				$("#output2").append(fieldslist[i].inferText + "&nbsp;");
			}
			var x = fieldslist[i].boundingPoly.vertices[0].x; //단어시작x좌표
			var y = fieldslist[i].boundingPoly.vertices[0].y; //단어시작y좌표
			
			var width = fieldslist[i].boundingPoly.vertices[1].x - x;
			var height = fieldslist[i].boundingPoly.vertices[2].y - y; 
			
			/* width : vertices[1].x - vertices[0].x
			   height : vertices[2].y= - y 
			      vertices[0]                      vertices[1]  
			
			 
			      vertices[3]                      vertices[2]  
			
			*/
			
			mycontext.strokeStyle="blue"; //색 지정
			mycontext.lineWidth = 2; //굵기지정
			mycontext.strokeRect(x, y, width, height); //가로 세로 길이 계산해야함
			
			
		}
		
	}//myimage.onload

});
</script>
</head>
<body>
</body>
<div id="output" style="border:2px solid orange"></div>
<div id="output2" style="border:2px solid orange"></div>
<canvas id="ocrcanvas" style="border:2px solid silver" width="500" height="500"></canvas>
</html>