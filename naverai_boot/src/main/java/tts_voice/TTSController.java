package tts_voice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;


@Controller
public class TTSController {
	
	@Autowired
	@Qualifier("ttsservice")
	NaverService service;//stt servcie.메소드(NaverService 오버라이딩메소드호출)
	
	
	//ai_images 파일리스트 보여주는 뷰
	@RequestMapping("/ttsinput")
	public ModelAndView faceinput() {
		File f = new File(MyNaverInform.path); //파일과 디렉토리 정보 제공
		String[] filelist = f.list();
		ArrayList<String> newfilelist = new ArrayList();
		String file_ext[] = {"txt"};
		//file_ext 배열 존재하는 확장자만 모델 포함.
		for(String onefile : filelist) {
			//bangtan.1.2.jpg
			String myext = onefile.substring(onefile.lastIndexOf(".")+1);//jpg 
			for(String imgext : file_ext) {
				if(myext.equals(imgext)){
					newfilelist.add(onefile);
					break;
				}
			}
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("filelist", newfilelist);
		mv.setViewName("ttsinput");
		return mv;
	}
	
	@RequestMapping("/ttsresult")
	public ModelAndView faceresult(String text, String speaker) throws IOException{
		String ttsresult = null;
		
		if(speaker == null) {
			ttsresult = service.test(text); //기본언어 kor
		}else {
			ttsresult = ((TTSServiceImpl)service).test(text, speaker);
		}

		ModelAndView mv = new ModelAndView();
	
		mv.addObject("ttsresult", ttsresult); //mp3파일명
		mv.setViewName("ttsresult");
		
		return mv;
	}
	
	
}
