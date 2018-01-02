package lam.cobia.rpc;

import java.io.Closeable;

/**
* <p>
* invoker
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public interface Invoker<T> extends Closeable{
	
	public String getKey();
	
	public Class<T> getInterface();
	
	public Result invoke(Invocation invocation);
	
	public void close();

}
