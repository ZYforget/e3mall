package cn.e3mall.search.acticemq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.search.service.SearchItemService;

public class ItemChangeListener implements MessageListener {

	@Autowired
	private SearchItemService searchItemServiceImpl;
	
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage=(TextMessage) message;
		Long itemId=null;
		try {
			itemId = Long.parseLong(textMessage.getText());
			searchItemServiceImpl.addIDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
