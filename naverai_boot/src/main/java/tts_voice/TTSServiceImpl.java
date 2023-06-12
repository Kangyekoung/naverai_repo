package tts_voice;

//네이버 음성합성 Open API 예제
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

@Service("ttsservice")
public class TTSServiceImpl implements NaverService{

	@Override
	public String test(String textfile) {
		// TODO Auto-generated method stub
		return test(textfile, "jinho");
	}
	public String test(String textfile, String speaker) {
		String tempname = null;
		 String clientId = MyNaverInform.voice_clientID;//애플리케이션 클라이언트 아이디값";
	     String clientSecret = MyNaverInform.voice_secret;//애플리케이션 클라이언트 시크릿값";
	     try {
	         //String text = URLEncoder.encode("지금은 네이버 플랫폼을 활용한 ai 서비스 진행중입니다.", "UTF-8"); //
	    	 String text = "";
	         FileReader fr = new FileReader(MyNaverInform.path + textfile);
	         //fr.read(); //한 글자씩 읽어오기 // fr은 한줄씩 읽을 수가 없다. 그래서 scanner 써야함
	    	 //한 줄 읽기할려면
	         Scanner sc = new Scanner(fr);
	         while(sc.hasNextLine()) {
	        	 text += sc.nextLine();
	         }
	         
	         text = URLEncoder.encode(text, "UTF-8");
	         
	    	 String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
	         URL url = new URL(apiURL);
	         HttpURLConnection con = (HttpURLConnection)url.openConnection();
	         con.setRequestMethod("POST");
	         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	         con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
	         // post request
	         String postParams = "speaker=" + speaker + "&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
	         con.setDoOutput(true);
	         DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	         wr.writeBytes(postParams);
	         wr.flush();
	         wr.close();
	         int responseCode = con.getResponseCode();
	         BufferedReader br;
	         if(responseCode==200) { // 정상 호출
	             InputStream is = con.getInputStream();
	             int read = 0;
	             byte[] bytes = new byte[1024];
	             tempname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	             // 랜덤한 이름으로 mp3 파일 생성
	             //String tempname = Long.valueOf(new Date().getTime()).toString(); // ?\2023060911223.mp3 //1000/1초
	             File f = new File(MyNaverInform.path + tempname + ".mp3");
	             f.createNewFile();
	             OutputStream outputStream = new FileOutputStream(f);
	             while ((read =is.read(bytes)) != -1) {
	                 outputStream.write(bytes, 0, read);
	             }
	             is.close();
	             System.out.println(tempname + "파일은 해당되는 경로에서 확인하세요");
	         } else {  // 오류 발생
	             br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	             String inputLine;
	             StringBuffer response = new StringBuffer();
	             while ((inputLine = br.readLine()) != null) {
	                 response.append(inputLine);
	             }
	             br.close();
	             System.out.println(response.toString());
	         }
	     } catch (Exception e) {
	         System.out.println(e);
	     }
		return tempname + ".mp3";
	}															
}