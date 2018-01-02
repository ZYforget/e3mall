package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper catMapper;
	@Override
	public List<TreeNode> getCatList(long parentId) {
		//获取用逆向工程生成的dao层的TbItemCatExample,用parentId查询节点列表
		TbItemCatExample example=new TbItemCatExample();
		//给定查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//查询
		List<TbItemCat> list = catMapper.selectByExample(example);
		List<TreeNode> listTree=new ArrayList<TreeNode>();
		//遍历查询内容，把前台需要的内容封装到TreeNode中，把TreeNode添加到集合中
		for(TbItemCat tbItemCat:list){
			TreeNode treeNode=new TreeNode();
			//节点的ID
			treeNode.setId(tbItemCat.getId());
			//节点的名称
			treeNode.setText(tbItemCat.getName());
			//该节点是树节点还是叶子节点
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			listTree.add(treeNode);
		}
		return listTree;
	}

}
