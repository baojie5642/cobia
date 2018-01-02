package lam.cobia.rpc;

import lam.cobia.core.exception.CobiaException;
import lam.cobia.core.util.NetUtil;
import lam.cobia.remoting.Channel;
import lam.cobia.remoting.Client;
import lam.cobia.remoting.DefaultFuture;
import lam.cobia.remoting.Request;
import lam.cobia.remoting.Response;

/**
* <p>
* default invoker
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public class DefaultInvoker<T> extends AbstractInvoker<T>{
	
	private Client[] clients;

	public DefaultInvoker(Class<T> classInterface, Client[] clients) {
		super(classInterface);
		this.clients = clients;
		if (clients == null || clients.length == 0) {
			throw new IllegalArgumentException("client is null or length of client array is zero.");
		}
	}

	@Override
	public Result doInvoke(Invocation invocation) {
		//select one of clients to invoke the invocation
		Client client = clients[0];
		if (client.isClose()) {
			throw new CobiaException("client(to server " + NetUtil.parseToString(client.getServerAddress()) + ") has been closed.");
		}
		Channel channel = client.getChannel();
		Request request = Request.newRequest();
		request.setData(invocation);
		DefaultFuture future = new DefaultFuture(request, channel);
		channel.send(request);
		Object obj = future.get();
		if (obj instanceof Response) {
			Response response = (Response) obj;
			DefaultResult result = new DefaultResult().setValue(response.getResult());
			return result;
		}
		return null;
	}
	
	@Override
	public void close() {
		for (Client client : clients) {			
			client.close();
		}
	}

}
