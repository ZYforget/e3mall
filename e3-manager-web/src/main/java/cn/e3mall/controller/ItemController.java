package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
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
}
