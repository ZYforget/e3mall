package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Value("${ROWS}")
	private Integer ROWS;
	
	@RequestMapping("/search")
	public SearchResult getSearchResult(String keyword,@RequestParam(defaultValue="1")int page,Model model) throws Exception{
		if(keyword!=null &&!"".equals(keyword)){			
			keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
		}
		//检查全局异常处理器
		//int i=1/0;
		SearchResult result = searchService.getSearch(keyword, page, ROWS);
		model.addAttribute("query", keyword);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getRecourdCount());
		model.addAttribute("itemList", result.getItemList());
		return result;
	}
}
