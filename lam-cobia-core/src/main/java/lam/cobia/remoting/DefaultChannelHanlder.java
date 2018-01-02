package lam.cobia.remoting;

import lam.cobia.core.exception.CobiaException;
import lam.cobia.rpc.Invocation;
import lam.cobia.rpc.Invoker;
import lam.cobia.rpc.Result;

/**
* <p>
* default channel handler
* </p>
* @author linanmiao
* @date 2018年1月2日
* @version 1.0
*/
public class DefaultChannelHanlder implements ChannelHandler{

	private Invoker<?> invoker;
	
	public DefaultChannelHanlder(Invoker<?> invoker) {
		this.invoker = invoker;
	}
	
	@Override
	public void received(Channel channel, Object msg) {
		System.out.println(channel + ">>>" + msg);
		if (msg instanceof Request) {
			Request request = (Request) msg;
			
			if (request.getData() instanceof Invocation) {
				Invocation invocation = (Invocation) request.getData();
				Result result = invoker.invoke(invocation);
				
				if (!result.hasException()) {
					Response response = new Response(request.getId()).setResult(result.getValue());
					
					channel.send(response);
				} else {
					throw new CobiaException("Occurs exception", result.getException());
				}
			} else {
				throw new CobiaException("Do not support type:" + request.getData().getClass().getName());
			}
			
		}
		
	}

}
