package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private JedisClient client;
	
	@Value("${itemExpire}")
	private Integer itemExpire;
	@Resource
	private Destination topicDestination;
	@Override
	public TbItem getItemById(long id) {
		try {
			//查询Redis
			String json = client.get("item_info:"+id+":base");
			//如果缓存中存在就取出
			if(StringUtils.isNoneBlank(json)){
				//把取出的内容转换成pojo类型
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果不存在就在数据库中查找
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		//查找到之后再把他放入到缓存中，
		try {
			client.set("item_info:"+id+":base", JsonUtils.objectToJson(tbItem));
			//设置缓存中存放的时间
			client.expire("item_info:"+id+":base", itemExpire);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	@Override
	public E3Result ItemAdd(TbItem item, String desc) {
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insert(item);
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(itemDesc);
		//把添加的内容添加到缓存中
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		return E3Result.ok();
	}
	@Override
	public TbItemDesc getItemDescById(long id) {
		//使用jedis从缓存中查找，如果有就从缓存中取内容
		try {
			String json = client.get("item_info:"+id+":desc");
			if(StringUtils.isNotBlank(json)){
				TbItemDesc desc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return desc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc tbItem = tbItemDescMapper.selectByPrimaryKey(id);
		//不存在就在数据库中查找，查找完成之后就放到数据库中
		client.set("item_info:"+id+":desc", JsonUtils.objectToJson(tbItem));
		//设置缓存时间
		client.expire("item_info:"+id+":desc", itemExpire);
		return tbItem;
	}

}
