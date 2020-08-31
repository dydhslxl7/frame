package com.kh.second.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.second.member.model.service.MemberService;
import com.kh.second.member.model.vo.Member;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//DI : 객체 생성은 스프링이 함 (자동 의존성 주입됨)
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {
//		String userid = request.getParameter("userid");
//		String userpwd = request.getParameter("userpwd");
//		Member member = new Member();
//		member.setUserid(userid);
//		member.setUserpwd(userpwd);
		
		logger.info("login.do : "+member.getUserid()+", "+member.getUserpwd());
		
		Member loginMember = memberService.loginCheck(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			status.setComplete(); //요청 성공, 200 전송
			return "common/main";
		}else {
			model.addAttribute("message", "로그인 실패!");
			return "common/error";
		}
		
	}
	
	@RequestMapping("loginPage.do")
	public String moveLoginPage() {
		return "member/loginPage";
	}
	
	@RequestMapping("enrollPage.do")
	public String moveEnrollPage() {
		return "member/enrollPage";
	}
	
	@RequestMapping("logout.do")
	public String logoutMethod(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			return "common/main";
		}else {
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다.");
			return "common/error";
		}
	}
	
	@RequestMapping(value="enroll.do", method=RequestMethod.POST)
	public String memberInsert(Member member, Model model) {
		logger.info("enroll : "+member);
		
		if(memberService.insertMember(member) > 0) {
			return "common/main";
		}else {
			model.addAttribute("message", "새 회원 등록 실패!");
			return "common/error";
		}
		
	}
	
}