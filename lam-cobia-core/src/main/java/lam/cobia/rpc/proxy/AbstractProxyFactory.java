package lam.cobia.rpc.proxy;

import lam.cobia.rpc.Invoker;

/**
* <p>
* abstract proxy factory
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public abstract class AbstractProxyFactory implements ProxyFactory{
	
	public <T> T getProxy(Invoker<T> invoker) {
		//...
		Class<?>[] interfaces = new Class<?>[]{invoker.getInterface()};
		return getProxy(invoker, interfaces);
	}
	
	public abstract <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces);

}
