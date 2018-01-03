package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbCategoryMapper;
	@Override
	public List<TreeNode> getContentCategoryList(long parentId) {
		//为查询目录树传入要查询的Id
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//返回查询到的所有category
		List<TbContentCategory> list = tbCategoryMapper.selectByExample(example);
		List<TreeNode> treeNodes=new ArrayList<TreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			//遍历查询到的category，添加到treeNode中
			TreeNode node=new TreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			treeNodes.add(node);
		}
		return treeNodes;
	}
	@Override
	public E3Result addContentCategory(long parentId, String name) {
		//声明一个category的实体类
		TbContentCategory category=new TbContentCategory();
		//向实体类中添加对象属性
		category.setIsParent(false);
		category.setName(name);
		category.setParentId(parentId);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		category.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		category.setStatus(1);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		tbCategoryMapper.insert(category);
		TbContentCategory key = tbCategoryMapper.selectByPrimaryKey(parentId);
		if(!key.getIsParent()){
			key.setIsParent(true);
			tbCategoryMapper.updateByPrimaryKey(key);
		}
		return E3Result.ok(category);
	}

}
