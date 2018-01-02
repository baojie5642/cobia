package lam.cobia.config.spring;

import java.util.ArrayList;
import java.util.List;

import lam.cobia.rpc.DefaultProtocol;
import lam.cobia.rpc.Exporter;
import lam.cobia.rpc.Invoker;
import lam.cobia.rpc.Protocol;
import lam.cobia.rpc.proxy.AbstractProxyFactory;
import lam.cobia.rpc.proxy.ProxyFactory;

/**
* <p>
* cobia service
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class CobiaService {
	
	private Protocol protocol = DefaultProtocol.getInstance();
	
	private ProxyFactory proxyFactory = AbstractProxyFactory.getDefault();
	
	private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

	public <T> void export(T ref, Class<T> clazz) {
		Invoker<T> invoker = proxyFactory.getInvoker(ref, clazz);
		Exporter<T> exporter = protocol.export(invoker);
		exporters.add(exporter);
	}
	
}
