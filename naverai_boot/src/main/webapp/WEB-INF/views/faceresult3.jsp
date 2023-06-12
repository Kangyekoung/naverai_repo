<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
window.onload = function(){
	let mycanvas = document.getElementById("facecanvas");
	let mycontext= mycanvas.getContext("2d");
	
	let faceimage = new Image();
	faceimage.src = "/naverimages/${param.image}";//url 통신 이미지 다운로드 시간 대기
	faceimage.onload = function(){
		mycontext.drawImage(faceimage, 0, 0, faceimage.width, faceimage.height);
		// 얼굴좌표
		<%
		String faceresult2 = (String)request.getAttribute("faceresult2");
		JSONObject total = new JSONObject(faceresult2);
		JSONArray faces = (JSONArray)total.get("faces");
		//얼굴크기. faces[i].roi.x faces[i].roi.y faces[i].roi.width faces[i].roi.height
		for(int i = 0; i < faces.length(); i++){
			JSONObject oneperson = (JSONObject)faces.get(i);
			JSONObject roi = (JSONObject)oneperson.get("roi");
			int x = (Integer)roi.get("x");
			int y = (Integer)roi.get("y");	
			int width = (Integer)roi.get("width");
			int height = (Integer)roi.get("height");
		%>

		mycontext.lineWidth = 3;
		mycontext.strokeStyle = "pink";
		mycontext.strokeRect(<%=x %> , <%=y%>, <%=width%>, <%=height%>);//얼굴위치인식

		//일부이미지만 복사
		var copyimage = mycontext.getImageData(<%=x %> , <%=y%>, <%=width%>, <%=height%>);
		mycontext.putImageData(copyimage, <%=x%> ,<%=y+300%>);
		
		mycontext.fillStyle="orange";
		mycontext.fillRect(<%=x %> , <%=y%>, <%=width%>, <%=height%>);//모자이크처리
		
	<%
	} //for end
	%>
	}//faceimage.onload end 

}//window.onload end
</script>
</head>
<body>


<canvas id="facecanvas" width="800" height="500" style="border:2px solid pink"></canvas>
</body>
</html>