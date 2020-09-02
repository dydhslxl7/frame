package com.kh.second.test.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.second.test.model.vo.Sample;
import com.kh.second.test.model.vo.User;

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
	
	//클라이언트에게서 요청을 받으면, json객체를 리턴하는 메소드
	@RequestMapping(value="test2.do", method=RequestMethod.POST)
	@ResponseBody //리턴하는 json문자열을 response객체에 담아서 보내라는 의미의 어노테이션임
	public String testAjaxMethod2(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test2.do run...");
		response.setContentType("application/json; charset=utf-8");
		
		JSONObject job = new JSONObject();
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", URLEncoder.encode("홍길동", "utf-8"));
		job.put("content", URLEncoder.encode("json 객체를 뷰리졸버를 통해 리턴하는 테스트 글임", "utf-8"));
		
		return job.toJSONString(); //뷰리졸버로 리턴함
	}
	
	@RequestMapping(value="test3.do", method=RequestMethod.POST)
	@ResponseBody
	public String testAjaxMethod3(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test3.do run...");
		
		//List를 json배열로 바꾸고, 요청한 페이지로 전송함
		List<User> list = new ArrayList<User>();
		
		list.add(new User("u111", "p111", "홍길동", 25, "hong111@kh.org", 
				"010-1111-1111", new java.sql.Date(new java.util.GregorianCalendar(1993, 10, 24).getTimeInMillis())));
		list.add(new User("u222", "p222", "이순신", 40, "lee222@kh.org", 
				"010-2222-2222", new java.sql.Date(new java.util.GregorianCalendar(1983, 9, 30).getTimeInMillis())));
		list.add(new User("u333", "p333", "이방원", 21, "lee333@kh.org", 
				"010-3333-3333", new java.sql.Date(new java.util.GregorianCalendar(1999, 10, 10).getTimeInMillis())));
		list.add(new User("u444", "p444", "김유신", 34, "kim444@kh.org", 
				"010-4444-4444", new java.sql.Date(new java.util.GregorianCalendar(1978, 5, 14).getTimeInMillis())));
		list.add(new User("u555", "p555", "퇴계이황", 35, "hwang555@kh.org", 
				"010-5555-2555", new java.sql.Date(new java.util.GregorianCalendar(2002, 8, 24).getTimeInMillis())));
		
		//전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//json 배열 객체 생성
		JSONArray jarr = new JSONArray();
		
		//list를 jarr로 옮기기(복사)
		for(User user : list) {
			//user객체 저장용 json객체 생성
			JSONObject job = new JSONObject();
			job.put("userid", user.getUserid());
			job.put("userpwd", user.getUserpwd());
			job.put("username", URLEncoder.encode(user.getUsername(), "utf-8"));
			job.put("age", user.getAge());
			job.put("email", user.getEmail());
			job.put("phone", user.getPhone());
			job.put("birth", user.getBirth().toString()); //날짜는 반드시 string으로 변환
			
			//jarr에 json객체 저장
			jarr.add(job);
		}
		sendJson.put("list", jarr);
		
		return sendJson.toJSONString(); //jsonView로 리턴됨.
	}
	
	@RequestMapping(value="test4.do", method=RequestMethod.POST)
	public ModelAndView testAjaxMethod4(ModelAndView mv) throws UnsupportedEncodingException {
		logger.info("test4.do run...");
		
		//Map객체를 ModelAndView객체에 담아서 JsonView로 보냄
		//그러면 json객체로 클라이언트에게 전송감
		Sample samp = new Sample("이 율곡", 55);
		samp.setName(URLEncoder.encode(samp.getName(), "utf-8"));
		
		Map<String, Sample> map = new HashMap<String, Sample>();
		map.put("samp", samp);
		
		mv.addObject(map);
		//servlet-context.xml에 bean 등록된 JsonView의 id명을 뷰 이름으로 지정함
		mv.setViewName("jsonView");
		
		return mv; //map객체가 json객체로 변경되어 전송됨
	}
	
	
	
	
	
	
}
