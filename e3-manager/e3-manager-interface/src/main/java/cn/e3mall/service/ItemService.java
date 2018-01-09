package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {

	public TbItem getItemById(long id);
	
	DataGridResult getItemListDataGrid(int page, int rows);
	
	public E3Result ItemAdd(TbItem item,String desc);
	
	public TbItemDesc getItemDescById(long id);
}
