package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public TbItem getItemById(long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItem;
	}
	@Override
	public DataGridResult getItemListDataGrid(int page, int rows) {
		//获取页数和要显示的条数
		PageHelper.startPage(page, rows);
		//执行分页查询
		TbItemExample example=new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//取分页结果
		PageInfo<TbItem> info=new PageInfo<>(list);
		//取分页总记录数
		long total = info.getTotal();
		//创建一个dataGridresult对象
		DataGridResult  result=new DataGridResult();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}
	@Override
	public E3Result ItemAdd(TbItem item, String desc) {
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insert(item);
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(itemDesc);
		return E3Result.ok();
	}
	@Override
	public TbItem findById(long id) {
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		return tbItem;
	}

}
