package chatbot;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.NaverService;

@Controller
public class ChatbotController {
	
	@Autowired
	@Qualifier("chatbotservice")
	NaverService service;
	
	@RequestMapping("/chatbotrequest")
	public String chatbotrequest() {
		return "chatbotrequest";
	}
	
	@RequestMapping("/chatbotresponse")
	public ModelAndView chatbotresponse(String request, String event) {
		String response = "";
		if(event.equals("웰컴메시지")) {
			response = ((ChatbotServiceImpl)service).test(request, "open");
		}
		else {
			response = service.test(request);
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
	
	@RequestMapping("/chatajaxprocess")
	@ResponseBody
	public String chatajaxprocess(String request, String event) {
		String response = "";
		if(event.equals("웰컴메시지")) {
			response = ((ChatbotServiceImpl)service).test(request, "open");
		}
		else {
			response = service.test(request);
		}
		return response;
	}
	
}
