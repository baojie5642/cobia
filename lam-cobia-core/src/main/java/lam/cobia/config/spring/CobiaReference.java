package lam.cobia.config.spring;

import lam.cobia.rpc.DefaultProtocol;
import lam.cobia.rpc.Invoker;
import lam.cobia.rpc.Protocol;
import lam.cobia.rpc.proxy.JdkProxyFactory;
import lam.cobia.rpc.proxy.ProxyFactory;

/**
* <p>
* cobia reference
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class CobiaReference {
	
	private Protocol protocol = DefaultProtocol.getInstance();
	
	private ProxyFactory proxyFactory = new JdkProxyFactory();
	
	public <T> T refer(Class<T> clazz) {
		Invoker<T> invoker = protocol.refer(clazz);
		
		return proxyFactory.getProxy(invoker);
	}

}
