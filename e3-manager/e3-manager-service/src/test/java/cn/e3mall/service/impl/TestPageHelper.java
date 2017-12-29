package cn.e3mall.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

public class TestPageHelper {

	@Test
	public void testPageHepler(){
		//在sqlMapconfig.xml中开启pageHelper的拦截器
		//设置分页信息
		PageHelper.startPage(1, 10);
		//开启spring容器
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从容器中获取mapper的代理对象
		TbItemMapper bean = context.getBean(TbItemMapper.class);
		TbItemExample example=new TbItemExample();
		//执行查询
		List<TbItem> list = bean.selectByExample(example);
		//取分页结果
		PageInfo<TbItem> page=new PageInfo<>(list);
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		System.out.println(page.getPageNum());
		System.out.println(page.getPageSize());
		System.out.println(list.size());
	}
}
