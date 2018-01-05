package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContent;
	
	@Autowired
	private JedisClient client;
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
		client.hdel("CONTENT_KEY", content.getCategoryId().toString());
		return E3Result.ok();
	}
	
	public List<TbContent> indexShow(long cid){
		//查询缓存，如果存在进返回不去查询数据库
		try {
			String hget = client.hget("CONTENT_KEY", cid+"");
			if(StringUtils.isNotBlank(hget)){
				List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置查询条件
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//获取查询到的内容
		List<TbContent> list = tbContent.selectByExample(example);
		//添加进缓存
		try {
			client.hset("CONTENT_KEY", cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
