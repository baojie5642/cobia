package lam.cobia.rpc;

import java.io.Closeable;

/**
* <p>
* exporter
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public interface Exporter<T> extends Closeable{
	
	Invoker<T> getInvoker();
	
	public void close();

}
