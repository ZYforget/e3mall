package cn.e3mall.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;

	
	@Value("${CONTENT_CATEGORYID}")
	private long content_categoryId;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		List<TbContent> list = contentService.indexShow(content_categoryId);
		model.addAttribute("ad1List", list);
		return "index";
	}
}
