package com.kh.second.notice.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.second.notice.model.vo.Notice;
import com.kh.second.notice.service.NoticeService;

@Controller
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("nlist.do")
	public ModelAndView moveNoticeList(ModelAndView mv) {
		ArrayList<Notice> list = noticeService.selectList();
		
		if(list.size() > 0) {
			mv.addObject("list", list);
			mv.setViewName("notice/noticeListView");
		}else {
			mv.addObject("message", "공지사항 목록 페이지 이동 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	@RequestMapping("ndetail.do")
	public ModelAndView moveNoticeDetail(@RequestParam("noticeno") String no, ModelAndView mv) {
		Notice notice = noticeService.selectOne(Integer.parseInt(no));
		
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("notice/noticeDetailView");
		}else {
			mv.addObject("message", "공지사항 상세 뷰 불러오기 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	@RequestMapping("nfdown.do")
	public void noticeFileDown() {
		
	}
	
	@RequestMapping("adnlist.do")
	public ModelAndView moveAdminNoticeList(ModelAndView mv) {
		ArrayList<Notice> list = noticeService.selectList();
		
		if(list.size() > 0) {
			mv.addObject("list", list);
			mv.setViewName("notice/noticeAdminListView");
		}else {
			mv.addObject("message", "공지사항 목록 페이지 이동 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	@RequestMapping("andetail.do")
	public ModelAndView moveAdminNoticeDetail(@RequestParam("noticeno") String no, ModelAndView mv) {
		Notice notice = noticeService.selectOne(Integer.parseInt(no));
		
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("notice/noticeAdminDetailView");
		}else {
			mv.addObject("message", "공지사항 상세 뷰 불러오기 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	@RequestMapping("nwrite.do")
	public String moveWriteForm() {
		return "notice/noticeWriteForm";
	}
	
//	@RequestMapping(value="ninsert.do", method=RequestMethod.POST)
//	public 
	
	@RequestMapping("ndel.do")
	public String noticeDelete(@RequestParam("noticeno") String no, Model model) {
		if(noticeService.deleteNotice(Integer.parseInt(no)) > 0) {
			return "redirect:adnlist.do";
		}else {
			model.addAttribute("message", "공지사항 삭제 실패!");
			return "common/error";
		}
	}
	
	@RequestMapping("npmove.do")
	public ModelAndView moveNoticeUpdateViewPage(@RequestParam("noticeno") String no, ModelAndView mv) {
		Notice notice = noticeService.selectOne(Integer.parseInt(no));
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.setViewName("notice/noticeUpdateView");
		}else {
			mv.addObject("message", "공지사항 상세 뷰 불러오기 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
//	@RequestMapping(value="nupdate.do", method=RequestMethod.POST)
	
	
	
}
