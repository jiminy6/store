package cn.e3mall.fast;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

/**
     * 说明：图片上传的测试类
     * 创建一个全局对象，获取TrackerServer的地址
     * 获取TrackerClient对象
     * 获取TrackerServer对象
     * 获取StorageServer对象的引用
     * 获取StorageClient对象(封装了StorageServer和TrackerServer对象)
     * 通过storageClient来上传文件
     * @author luowenxin
     * @version 1.0
     * @date 2017年12月2日
     */
public class FastDfsTest {
	@Test
	public void test1() throws Exception{
//	ClientGlobal.init("/develop/workspaces/workspace1/e3-manager-web/src/main/resources/conf/client.conf");
	ClientGlobal.init("/develop/workspaces/workspace1/e3-manager-web/src/main/resources/conf/client.conf");
	TrackerClient trackerClient = new TrackerClient();//获取trackerClient对象
	TrackerServer trackerServer = trackerClient.getConnection();//通过client获取server对象
	StorageServer storageServer=null;//获取StorageServer的引用
	StorageClient storageClient = new StorageClient(trackerServer, storageServer);//获取storageClient，并且通过这个来实现文件上传
	String[] strings = storageClient.upload_file("/develop/pic/a.jpg","jpg",null);
	for (String string : strings) {
		System.out.println(string);
	}
	}
}
