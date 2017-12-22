package lam.cobia.rpc.proxy;

import lam.cobia.rpc.Invoker;

/**
* <p>
* proxy factory
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public interface ProxyFactory {
	
	public <T> T getProxy(Invoker<T> invoker);

}
