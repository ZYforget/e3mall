package cn.e3mall.content.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	public DataGridResult getContentList(long categoryId,int page,int rows);

	public E3Result addContent(TbContent content);
}
