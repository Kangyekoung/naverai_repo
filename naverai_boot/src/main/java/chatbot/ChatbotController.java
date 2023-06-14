package chatbot;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;

@Controller
public class ChatbotController {
	@Autowired
	@Qualifier("chatbotservice")
	ChatbotServiceImpl service;
	
	@Autowired
	@Qualifier("chatbotttsservice")
	ChatbotTTSServiceImpl ttsservice ;
	
	@Autowired
	@Qualifier("chatbotsttservice")
	ChatbotSTTServiceImpl sttservice;
	
	@Autowired
	@Qualifier("pizzaservice")
	PizzaServiceImpl pizzaservice;
	
	
	@RequestMapping("/chatbotrequest")
	public String chatbotrequest() {
		return "chatbotrequest";
	}
	
	@RequestMapping("/chatbotresponse")
	public ModelAndView chatbotresponse(String request, String event){
		String response = "";
		if(event.equals("웰컴메시지") ){
			response = service.test(request, "open");
		}
		else {
			response = service.test(request, "send");
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("response", response);
		mv.setViewName("chatbotresponse");
		return mv;
	}
	
	//기본답변만 분석한 뷰
	@RequestMapping("/chatbotajaxstart")
	public String chatbotajaxstart() {
		return "chatbotajaxstart";
	}
	
	//기본+이미지+멀티링크답변 분석한 뷰
	@RequestMapping("/chatbotajax")
	public String chatbotajax() {
		return "chatbotajax";
	}
	
	@RequestMapping("/chatbotajaxprocess")
	//@ResponseBody
	public @ResponseBody String chatbotajaxprocess(String request, String event){
		String response = "";
		if(event.equals("웰컴메시지") ){
			response = service.test(request, "open");
		}
		else {
			response = service.test(request, "send");
		}
		return response;
	}
	
	@RequestMapping("/chatbottts")
	@ResponseBody
	public String chatbottts(String text){//챗봇답변
		String mp3 = ttsservice.test(text);//답변텍스트를 -- 해당경로 저장 -- mp3파일이름 리턴
		return "{\"mp3\":\"" + mp3 +"\"}"   ;
		//20230613시분초.mp3
	}
	
	//음성질문 서버 업로드
	@PostMapping("/mp3upload")
	@ResponseBody
	public String mp3upload(MultipartFile file1) throws IOException{
		String uploadFile = file1.getOriginalFilename();//a.mp3
		String uploadPath = MyNaverInform.path;
		File saveFile = new File(uploadPath + uploadFile);
		file1.transferTo(saveFile);
		return "{\"result\" :\"잘 받았습니다\"}";
	}
	
	//업로드한 음성질문mp3파일을 텍스트변환
	@RequestMapping("/chatbotstt")
	@ResponseBody
	public String chatbotstt(String mp3file) {
		String text = sttservice.test(mp3file);	
		return text ;//json형태
	}
	
	//피자주문을 db pizza 테이블에 저장
	@RequestMapping("pizzaorder")
	@ResponseBody
	public String pizzaorder(PizzaDTO dto) {
		int insertrow = pizzaservice.insertPizza(dto);
		return "{\"insertrow\" : " + insertrow + "}";
	}
}











