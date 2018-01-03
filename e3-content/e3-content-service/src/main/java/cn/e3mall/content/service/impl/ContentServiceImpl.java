package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContent;
	@Override
	public DataGridResult getContentList(long categoryId,int page,int rows) {
		//设置mybatis的分页查询
		PageHelper.startPage(page, rows);
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContent.selectByExample(example);
		PageInfo<TbContent> info=new PageInfo<>(list);
		DataGridResult result=new DataGridResult();
		result.setRows(list);
		result.setTotal(info.getTotal());
		return result;
	}
	@Override
	public E3Result addContent(TbContent content) {
		//补全属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContent.insert(content);
		return E3Result.ok();
	}

}
