package com.kh.second.test.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.second.test.model.vo.Sample;

@Controller
public class TestController {
	//로그 출력용 객체를 의존성 주입함
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	//뷰페이지로 이동 처리용 메소드 -----------------------------------
	@RequestMapping("moveAjax.do")
	public String moveTestAjaxPage() {
		return "test/testAjaxPage";
	}
	
	//ajax test method -------------------------------------
	@RequestMapping(value="test1.do", method=RequestMethod.POST)
	public void testAjaxMethod1(Sample sample, HttpServletResponse response) throws IOException {
		logger.info("test1.do run...");
		logger.info("sample : " + sample);
		
		//서비스로 보내고 결과받기 : 생략
		
		//요청한 결과를 클라이언트에게 전송함 : 출력스트림을 통해 전송함
		//1.보내는 정보에 대한 Mimi type 지정함
		response.setContentType("test/html; charset=utf-8");
		//2.출력에 사용할 스트림 생성
		PrintWriter out = response.getWriter();
		
		if(sample.getName().equals("신사임당")) {
			out.append("ok");
			out.flush();
		}else {
			out.append("fail");
			out.flush();
		}
		
		out.close();
	}
}
