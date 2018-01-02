package lam.cobia.rpc;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lam.cobia.core.constant.Constant;
import lam.cobia.remoting.ChannelHandler;
import lam.cobia.remoting.Client;
import lam.cobia.remoting.DefaultChannelHanlder;
import lam.cobia.remoting.ExchangeServer;
import lam.cobia.remoting.HeaderExchangeServer;
import lam.cobia.remoting.transport.netty.NettyClient;
import lam.cobia.remoting.transport.netty.NettyServer;

/**
* <p>
* default protocol
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class DefaultProtocol implements Protocol{

	private final Object sharedObject;

	private ConcurrentMap<Invoker<?>, Object> invokerMap;
	
	private ConcurrentMap<String, Exporter<?>> exporterMap;
	
	private ConcurrentMap<String, ExchangeServer> serverMap;
	
	private DefaultProtocol() {
		this.sharedObject = new Object();
		this.invokerMap = new ConcurrentHashMap<Invoker<?>, Object>();
		this.exporterMap = new ConcurrentHashMap<String, Exporter<?>>();
		this.serverMap = new ConcurrentHashMap<String, ExchangeServer>();
	}

	private static class DefaultProtocolHolder {
		private static DefaultProtocol INSTANCE = new DefaultProtocol(); 
	}
	
	public static DefaultProtocol getInstance() {
		return DefaultProtocolHolder.INSTANCE;
	}
	
	@Override
	public <T> Exporter<T> export(Invoker<T> invoker) {

	    DefaultExporter<T> exporter = new DefaultExporter<T>(invoker, invoker.getKey());

	    exporterMap.put(invoker.getKey(), exporter);
	    
	    openServer(invoker);
	    
		return exporter;
	}

	@Override
	public <T> Invoker<T> refer(Class<T> clazz) {
		//create DefaultInvoker<T> object with tcp client[]
		DefaultInvoker<T> invoker = new DefaultInvoker<T>(clazz, getClients(clazz));
		invokerMap.put(invoker, sharedObject);
		return invoker;
	}
	
	private void openServer(Invoker<?> invoker) {
		ExchangeServer server = serverMap.get(invoker.getKey());
		if (server == null) {
			serverMap.putIfAbsent(invoker.getKey(), createServer(invoker));
		}
	}
	
	private ExchangeServer createServer(Invoker<?> invoker) {
		
		Exporter<?> exporter = exporterMap.get(invoker.getKey());
		
		ChannelHandler channelHandler = new DefaultChannelHanlder(exporter.getInvoker());
		
		NettyServer nettyServer = new NettyServer(Constant.DEFAULT_SERVER_PORT, channelHandler);
		HeaderExchangeServer server = new HeaderExchangeServer(nettyServer);
		return server;
	}
	
	private Client[] getClients(Class<?> clazz) {
		InetSocketAddress remoteAddress = new InetSocketAddress(Constant.DEFAULT_SERVER_HOSTNAME, Constant.DEFAULT_SERVER_PORT);
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
