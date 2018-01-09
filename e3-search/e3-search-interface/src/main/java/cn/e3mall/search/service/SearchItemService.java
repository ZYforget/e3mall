package cn.e3mall.search.service;

import cn.e3mall.common.utils.E3Result;

public interface SearchItemService{

	E3Result importItemList();
	
	E3Result addIDocument(Long itemId)throws Exception;
}
