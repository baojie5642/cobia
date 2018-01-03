package lam.cobia.remoting;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
* <p>
* request model
* </p>
* @author linanmiao
* @date 2017年12月22日
* @version 1.0
*/
public class Request implements Serializable{
	
	private static final long serialVersionUID = 7473629229906171995L;

	private final long id;

	private Object data;
	
	private static final AtomicLong INVOKER_ID = new AtomicLong();
	
	public Request() {
		this.id = newId();
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public long getId() {
		return id;
	}
	
	public Object getData() {
		return data;
	}
	
	public static Request newRequest() {
		return new Request();
	}
	
	private static long newId() {
		return INVOKER_ID.incrementAndGet();
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", data=" + data + "]";
	}
	
}
