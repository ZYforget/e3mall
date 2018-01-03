package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictureController {

	//获取存放图片的服务器的IP地址，在改地址中给定绝对地址，不然会出现路径错误
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload" ,produces=MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8" )
	@ResponseBody
	public String fileUpLoad(MultipartFile uploadFile){
		try {
			//取文件扩展名
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			//创建一个fastdfs的客户端
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
			//执行上传处理
			String file = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			//拼接完整的上传的图片的URL
			String url=IMAGE_SERVER_URL+file;
			//返回KindEditor需要的json类型
			Map<String,Object> map=new HashMap<>();
			map.put("error", 0);
			map.put("url", url);
			String json = JsonUtils.objectToJson(map);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map=new HashMap<>();
			map.put("error", 1);
			map.put("message", "图片上传失败");
			String json = JsonUtils.objectToJson(map);
			return json;
		}
	}
}
