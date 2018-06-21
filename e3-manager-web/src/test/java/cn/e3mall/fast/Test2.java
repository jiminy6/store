package cn.e3mall.fast;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class Test2 {
	@Test
	public void test1(){
		System.out.println(11);
	}
	@Test
	public void test2() throws FileNotFoundException, IOException, MyException{
		ClientGlobal.init("/develop/Repository/e3-manager-web/src/main/resources/conf/client.conf");
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
