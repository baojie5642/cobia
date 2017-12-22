package lam.cobia.rpc;

import java.io.Closeable;

/**
* <p>
* protocol
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public interface Protocol extends Closeable{
	
	public <T> Exporter<T> export(Invoker<T> invoker);
	
	public <T> Invoker<T> refer(Class<T> clazz);
	
	public void close();
}
