package cfr;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

import ch.qos.logback.core.encoder.ByteArrayUtil;

@Controller
public class FaceController {
	
	@Autowired
	@Qualifier("faceservice1")
	NaverService service;//닮은 연예인 서비스
	
	@Autowired
	@Qualifier("faceservice2")
	NaverService service2;//안면 나이 성별 감정 눈코입위치 서비스
	
	//ai_images 파일리스트 보여주는 뷰
	@RequestMapping("/faceinput")
	public ModelAndView faceinput() {
		File f = new File(MyNaverInform.path); //파일과 디렉토리 정보 제공
		String[] filelist = f.list();
		ArrayList<String> newfilelist = new ArrayList();
		String file_ext[] = {"jpg", "gif", "png", "jfif"};
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
		mv.setViewName("faceinput");
		return mv;
	}
	
	@RequestMapping("/faceresult")
	public ModelAndView faceresult(String image){
		//서비스클래스 요청 -"https://naveropenapi.apigw.ntruss.com/vision/v1/celebrity"; 
		String faceresult = service.test(image);
		ModelAndView mv = new ModelAndView();
		//System.out.println("faceresult : " + faceresult);
		mv.addObject("faceresult", faceresult); //"{a:100}"
		mv.setViewName("faceresult");
		return mv;
	}
	
	//안면인식서비스 파일리스트
	@RequestMapping("/faceinput2")
	public ModelAndView faceinput2() {
		File f = new File(MyNaverInform.path); //파일과 디렉토리 정보 제공
		String[] filelist = f.list();
		ArrayList<String> newfilelist = new ArrayList();
		String file_ext[] = {"jpg", "gif", "png", "jfif"};
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
		mv.setViewName("faceinput2");
		return mv;
	}
	
	@RequestMapping("/faceresult2")
	public ModelAndView faceresult2(String image){
		//서비스클래스 요청 -"https://naveropenapi.apigw.ntruss.com/vision/v1/celebrity"; 
		String faceresult = service2.test(image);
		ModelAndView mv = new ModelAndView();
		//System.out.println("faceresult : " + faceresult);
		mv.addObject("faceresult2", faceresult); //"{a:100}"
		mv.setViewName("faceresult3");
		return mv;
	}
}
