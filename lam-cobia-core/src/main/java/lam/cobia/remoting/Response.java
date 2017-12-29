package lam.cobia.remoting;

import java.io.Serializable;

/**
* <p>
* response for rpc
* </p>
* @author linanmiao
* @date 2017年12月29日
* @version 1.0
*/
public class Response implements Serializable{

	private static final long serialVersionUID = -5687604797173954790L;
	
	private long id;
	
	private Object result;
	
	public Response(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public Response setResult(Object result) {
		this.result = result;
		return this;
	}
	
	public Object getResult() {
		return result;
	}

}
