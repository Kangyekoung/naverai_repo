package objectdetection;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

@Controller
public class ObjectDetectionController {
	@Autowired
	@Qualifier("objectdetectionservice")
	NaverService service;
	
	@RequestMapping("/objectinput")
	public ModelAndView objectinput() {
		
		File f = new File(MyNaverInform.path);//파일과 디렉토리 정보 제공
		String[] filelist = f.list();
		
		String file_ext[] = {"jpg", "gif", "png", "jfif"};
		//file_ext 배열 존재하는 확장자만 모델 포함. 
		
		ArrayList<String> newfilelist = new ArrayList();
		for(String onefile : filelist) {
			//bangtan.1.2.jpg
			String myext = onefile.substring(onefile.lastIndexOf(".") + 1);//jpg
			for(String imgext : file_ext) {
				if(myext.equals(imgext)) {
					newfilelist.add(onefile);
					break;
				}
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("filelist", newfilelist);
		mv.setViewName("objectinput");
		return mv;
	}
	
	@RequestMapping("/objectresult")
	public ModelAndView objectresult(String image) {
		String objectresult = service.test(image);
		ModelAndView mv = new ModelAndView();
		mv.addObject("objectresult", objectresult);
		mv.setViewName("objectresult");
		return mv;
	}
	
	
}
