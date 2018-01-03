package cn.e3mall.fastDFS;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {

	@Test
	public void imageUPLoad()throws Exception{
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。配置文件内容：tracker_server=192.168.25.133:22122
		ClientGlobal.init("F:/JavaEEe3mall/e3-manager-web/src/main/resources/conf/client.conf");
		// 2、创建一个TrackerClient对象。直接new一个。
		TrackerClient client=new TrackerClient();
		// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer server=client.getConnection();
		// 4、创建一个StorageServer的引用，值为null
		StorageServer storage=null;
		// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient storageClient=new StorageClient(server, storage);
		// 6、使用StorageClient对象上传图片。
		String[] file = storageClient.upload_file("F:/记录/美图/images/15c5818ff0de6b3060ec5ff4c34a4c37.jpg", "jpg",null);
		// 7、返回数组。包含组名和图片的路径。
		for (String string : file) {
			System.out.println(string);
		}
	}
	@Test
	public void fileUtils()throws Exception{
		FastDFSClient client=new FastDFSClient("F:/JavaEEe3mall/e3-manager-web/src/main/resources/conf/client.conf");
		String file=client.uploadFile("F:/记录/美图/psbN1MOAALP.jpg");
		System.out.println(file);
	}
}
