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
	//1 type buton 클릭하면 request 질문을 response 붙여넣는다
	$("input:button").on("click", function(){
		$("#response").append("질문 : " + $("#request").val() + "<br>");
		//답변 받아오기 -ajax
		$.ajax({
			url:"/chatajaxprocess",
			data:{"request": $("#request").val(), "event": $(this).val()},
			type:'get',
			dataType:'json',
			success : function(server){
				let bubbles = server.bubbles;
				for(let b in bubbles){
					//1.기본(텍스트/ 텍스트+url)
					if(bubbles[b].type=='text'){
						$("#response").append("기본답변 : " + bubbles[b].data.description + "<br>");
						if(bubbles[b].data.url != null){ //url 있다면
							$("#response").append
							('<a href=' + bubbles[b].data.url + '>' + bubbles[b].data.description + '</a><br>');
						}
						$.ajax({
							url : '/chatbottts',
							data : {'text' : bubbles[b].data.description},
							type: 'get',
							dataType : 'json',
							success : function(server){
								//alert(server.mp3);
								// id tts audio 태그 재생
								//1.id tts 태그 dom일자
								var audio = $("#tts");
								//2. 1번 src 속성 재성 mp3 지정
								audio.atr("src", "/naverimages/" + server.mp3);
								//3. 1번 play()
								audio.play();
							}
							error : function(e){
								alert(e);
							}
						});
						
						///////////////////////////////
						// 피자주문
						//////////////////////////////
						
					}
					//이미지이거나 멀티링크
					else if(bubbles[b].type=='template'){
						//2.이미지(이미지)
						if(bubbles[b].data.cover.type == 'image'){
							$("#response").append
							('<img src=' + bubbles[b].data.cover.data.imageUrl + ' width=200 height=200 ><br>');
						}
						//3.멀티링크(url) 
						else if(bubbles[b].data.cover.type == 'text'){
							$("#response").append("멀티링크답변 : " + bubbles[b].data.cover.data.description + "<br>");
						}
						//4.이미지+멀티링크 공통(url)
						for(let c in bubbles[b].data.contentTable){
							for(let d in bubbles[b].data.contentTable[c]){
								let link = bubbles[b].data.contentTable[c][d].data.title;
								let href = bubbles[b].data.contentTable[c][d].data.data.action.data.url;
								$("#response").append('<a href=' + href +'> ' + link +'</a><br>');
							}
						}
					
					}
				
				}//for(let b in bubbls) end
			},//success end
			error: function(e){alert(e);}
		});//ajax end
	});//on end
});
</script>
</head>
<body>

질문 : <input type=text id="request">
<input type=button value="답변" name="event1">
<input type=button value="웰컴메시지" name="event2">

<button id="record">음성질문녹음시작</button>
<button id="stop">음성질문녹음종료</button>
<div id="sound"></div>
<br>
대화내용 : <div id="response" style="border:2px solid aqua"></div>
음성답변  <audio id="tts" controls="controls"></audio>

<script>
//html 내용 붙여넣기
</script>

</body>
<script>
 var formData = new FormData();
 
 formData.append("file1", blob ,"a.mp3");
 $.ajax({
	 url : "/mp3upload",
	 data : formData,
	 type : "post",
	 processData : false,
 	contentType : false,
 	success : function(){
 		//a.mp3파일 서버
 		$.ajax({
 				url:"/chatbotstt",
 				data : {"mp3file" : "a.mp3"}
 				type: "get", 
 				dataType : 'json',
 				success : function(server){
 				 $("#request").val(server.text);
 				}
 		});
 		
 		
 		$.ajax({
 			url: "/pizzaorder",
 			data : {"kind":kind, "size":size, "price":totalPrice, "phone":phone},
 			type : "get",
 			dataType:"json",
 			success : function(server){
 				alert(server.insertrow);
 			}
 		});
 	},
 	error : function(e){
 		alert(e)
 	}
 
 	
 });

 </script>
</html>