package cn.e3mall.search.service;

import cn.e3mall.search.pojo.SearchResult;

public interface SearchService {

	SearchResult getSearch(String keyWords,int page,int rows)throws Exception;
}
