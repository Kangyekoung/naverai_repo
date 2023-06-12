package stt_csr;

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
public class STTController {
	
	@Autowired
	@Qualifier("sttservice")
	NaverService service;//stt servcie.메소드(NaverService 오버라이딩메소드호출)
	
	
	//ai_images 파일리스트 보여주는 뷰
	@RequestMapping("/sttinput")
	public ModelAndView faceinput() {
		File f = new File(MyNaverInform.path); //파일과 디렉토리 정보 제공
		String[] filelist = f.list();
		ArrayList<String> newfilelist = new ArrayList();
		String file_ext[] = {"mp3", "m4a", "wav"};
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
		mv.setViewName("sttinput");
		return mv;
	}
	
	@RequestMapping("/sttresult")
	public ModelAndView faceresult(String image, String lang) throws IOException{
		String sttresult = null;
		
		if(lang == null) {
			sttresult = service.test(image); //기본언어 kor
		}else {
			sttresult = ((STTServiceImpl)service).test(image, lang);
		}

		ModelAndView mv = new ModelAndView();
	
		mv.addObject("sttresult", sttresult); //"{a:100}"
		mv.setViewName("sttresult");
		
		//추가 MyNaverInform.path경로 mp3파일이름_20230609112022.txt파일로 저장
		//구현
		
		//fileWriter 저장
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now_string = sdf.format(now);
		String filename = image.substring(0, image.lastIndexOf(".")) + "_" + now_string + ".txt";
		
		FileWriter fw = new FileWriter(MyNaverInform.path + filename, false);
		
		JSONObject josntext = new JSONObject(sttresult);
		String text = (String)josntext.get("text");
		
		fw.write(text);
		fw.close();
		
		return mv;
	}
	
	
}
