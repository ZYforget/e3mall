package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem findItemById(@PathVariable long id){
		TbItem tbItem = itemService.getItemById(id);
		return tbItem;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult findItemPage(int page,int rows){
		DataGridResult result = itemService.getItemListDataGrid(page, rows);
		return result;
	}
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result itemAdd(TbItem item ,String desc){
		E3Result result = itemService.ItemAdd(item, desc);
		return result;
	}
	
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public Map findById(@PathVariable("id") long id){
		TbItem tbItem = itemService.getItemById(id);
		Map map=new HashMap();
		map.put("status", 200);
		return map;
	}
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result addSolr(){
		E3Result result = searchItemService.importItemList();
		return result;
	}
}
