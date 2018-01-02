package lam.cobia.remoting.transport.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.Gson;

import lam.cobia.remoting.Channel;

/**
* <p>
* netty channel
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class NettyChannel implements Channel{
	
	private io.netty.channel.Channel channel;
	
	private static Gson gson = new Gson();
	
	private static ConcurrentMap<io.netty.channel.Channel, Channel> channelMap = new ConcurrentHashMap<>();
	
	public NettyChannel(io.netty.channel.Channel channel) {
		this.channel = channel;
	}
	
	public static Channel getChannel(io.netty.channel.Channel channel) {
		Channel ch = channelMap.get(channel);
		if (ch == null) {
			channelMap.putIfAbsent(channel, new NettyChannel(channel));
			ch = channelMap.get(channel);
		}
		return ch;
	}
	
	public static void remove(io.netty.channel.Channel channel) {
		channelMap.remove(channel);
	}

	@Override
	public void send(Object msg) {
		System.out.println(channel + ":" + msg);
		String json = gson.toJson(msg);
		channel.writeAndFlush(json);
		channel.flush();
		//channel.writeAndFlush("abc");
	}

}
