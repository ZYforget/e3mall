package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.common.utils.E3Result;

public interface ContentCategoryService {

	public List<TreeNode> getContentCategoryList(long parentId);
	
	public E3Result addContentCategory(long parentId,String name);
}
