package mymapping;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;


@Controller
public class MapController {

	@Autowired
	@Qualifier("mapservice")
	NaverService service;
	
	@Autowired
	@Qualifier("ttsservice")
	NaverService service2;
	
	@GetMapping("/myinput")
	public String input() {
		return "mymapping/input";
	}
	/*
	 * a
	사용할("/myoutput")
	public ModelAndView output(String request) throws IOException {
		String response = service.test(request);
		
		//텍스트 답변을 txt 파일로 생성
		FileWriter fw = new FileWriter(MyNaverInform.path + "response.txt");
		fw.write(response);
		fw.close();
		
		//4> 3번 파일을 TTSServiceImpl 로 전달하여 실행 내용을 리턴받는다.
		String mp3 = service2.test("response.txt"); //기본언어 kor
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("response", response); //답변(텍스트)
		mv.addObject("mp3", mp3); //mp3파일명
		mv.setViewName("mymapping/output");
		return mv;
	}*/
	
	//ajax 사용할 경우
	@RequestMapping("/myoutput")
	@ResponseBody //ajax (sts3 - pom.xml 라이브러리 추가/ sts4 boot - 자동추가)
	public String output(String request) throws IOException {
		String response = service.test(request);
		
		//텍스트 답변을 txt 파일로 생성
		FileWriter fw = new FileWriter(MyNaverInform.path + "response.txt");
		fw.write(response);
		fw.close();
		
		//4> 3번 파일을 TTSServiceImpl 로 전달하여 실행 내용을 리턴받는다.
		String mp3 = service2.test("response.txt"); //기본언어 kor
		
		return "{\"response\" : \"" +  response + "\", \"mp3\" : \""  + mp3 + "\"}";
	}
}
