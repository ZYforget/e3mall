package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
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

}
