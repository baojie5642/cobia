package lam.cobia.remoting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
* <p>
* default future
* </p>
* @author linanmiao
* @date 2017年12月22日
* @version 1.0
*/
public class DefaultFuture implements ResponseFuture{
	
	private final Request request;
	
	private final Channel channel;
	
	private static final ConcurrentMap<Long, DefaultFuture> RESP_FUTURE = new ConcurrentHashMap<Long, DefaultFuture>();
	
	//private static final ConcurrentMap<Long, Channel> CHANNELS = new ConcurrentHashMap<Long, Channel>();
	
	public DefaultFuture(Request request, Channel channel) {
		this.request = request;
		this.channel = channel;
		this.RESP_FUTURE.put(request.getId(), this);
		//this.CHANNELS.put(request.getId(), this.channel);
	}
	
	Channel getChannel() {
		return channel;
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(TimeUnit timeUnit, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
