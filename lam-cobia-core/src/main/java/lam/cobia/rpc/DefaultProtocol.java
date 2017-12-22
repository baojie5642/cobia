package lam.cobia.rpc;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lam.cobia.core.constant.ConfigConstant;
import lam.cobia.remoting.Client;
import lam.cobia.remoting.transport.netty.NettyClient;

/**
* <p>
* default protocol
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class DefaultProtocol implements Protocol{
	
	private static class DefaultProtocolHolder {
		private static DefaultProtocol INSTANCE = new DefaultProtocol(); 
	}

	private final Object sharedObject;

	private ConcurrentMap<Invoker<?>, Object> invokerMap;
	
	private ConcurrentMap<String, Exporter<?>> exporterMap;
	
	private DefaultProtocol() {
		this.sharedObject = new Object();
		this.invokerMap = new ConcurrentHashMap<Invoker<?>, Object>();
		this.exporterMap = new ConcurrentHashMap<String, Exporter<?>>();
	}
	
	public static DefaultProtocol getInstance() {
		return DefaultProtocolHolder.INSTANCE;
	}
	
	@Override
	public <T> Exporter<T> export(Invoker<T> invoker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Invoker<T> refer(Class<T> clazz) {
		//create DefaultInvoker<T> object with tcp client[]
		DefaultInvoker<T> invoker = new DefaultInvoker<T>(clazz, getClients(clazz));
		invokerMap.put(invoker, sharedObject);
		return invoker;
	}
	
	private Client[] getClients(Class<?> clazz) {
		InetSocketAddress remoteAddress = new InetSocketAddress(ConfigConstant.DEFAULT_SERVER_HOSTNAME, ConfigConstant.DEFAULT_SERVER_PORT);
		NettyClient client = new NettyClient(remoteAddress);
		return new Client[]{client};
	}

	@Override
	public void close() {
		for (Invoker<?> invoker : invokerMap.keySet()) {
			invoker.close();
		}
		for (Exporter<?> exporter : exporterMap.values()) {
			exporter.close();
		}
	}

}
