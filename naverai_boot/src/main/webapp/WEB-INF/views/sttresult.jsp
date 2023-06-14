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
});
</script>
</head>
<body>
<H3>${sttresult }</H3>
<audio id="mp3" src="/naverimages/${param.image }" controls ></audio>
<script>
document.getElementById("mp3").play();

/*var playPromise = document.getElementById("mp3").play();
if(playPromise !== undefined) {
	playPromise.then(()=>{alert("정상")})
	.catch(error=>{alert(error);})
}*/

/* if (playPromise !== undefined) {
    playPromise.then(_ => {
      // Automatic playback started!
      // Show playing UI.
      // We can now safely pause video...
      //document.getElementById("mp3").pause();
    	alert(1);
    })
    .catch(error => {
      // Auto-play was prevented
      // Show paused UI.
    	alert(error);
    });
  } */

</script>
</body>
</html>