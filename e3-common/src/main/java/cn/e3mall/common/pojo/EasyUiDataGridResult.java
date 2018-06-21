package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUiDataGridResult implements Serializable {

	/**
	 * 注意!要进行网络传输,一定要实现序列化接口.
	 */
	private static final long serialVersionUID = 1L;

	private long total;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}

}
