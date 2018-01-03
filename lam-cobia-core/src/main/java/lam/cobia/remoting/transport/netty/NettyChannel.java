package lam.cobia.remoting.transport.netty;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lam.cobia.core.constant.Constant;
import lam.cobia.core.exception.CobiaException;
import lam.cobia.remoting.Channel;
import lam.cobia.serialize.Hessian2Serializer;

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
		//hessian2
	    byte[] bytes = Hessian2Serializer.serialize(msg);
		ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
		channel.writeAndFlush(byteBuf);
		
		//json
		/*String json = gson.toJson(msg);
		try {
			ByteBuf byteBuf = Unpooled.copiedBuffer(json.getBytes(Constant.DEFAULT_CHART_UTF8));
			channel.writeAndFlush(byteBuf);
		} catch (UnsupportedEncodingException e) {
			throw new CobiaException(e);
		}*/
		//demo
		/*String message = "abc中文";
		try {
			ByteBuf byteBuf = Unpooled.copiedBuffer(message.getBytes(Constant.DEFAULT_CHART_UTF8));
			channel.writeAndFlush(byteBuf);
		} catch (UnsupportedEncodingException e) {
			throw new CobiaException(e);
		}*/
	}

}
