package lam.cobia.remoting.transport.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import lam.cobia.core.util.NetUtil;
import lam.cobia.remoting.ChannelHandler;
import lam.cobia.remoting.Request;

/**
* <p>
* netty server handler
* </p>
* @author linanmiao
* @date 2017年12月18日
* @version 1.0
*/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	//key:<host:port>
	private ConcurrentMap<String, NettyChannel> channelMap;
	
	private ChannelHandler handler;
	
	private static Gson gson = new Gson();
	
	public NettyServerHandler(ChannelHandler handler) {
		this.handler = handler;
		channelMap = new ConcurrentHashMap<String, NettyChannel>();
	}
	
	public ConcurrentMap<String, NettyChannel> getChannels() {
		return channelMap;
	}
	
    /**
     * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	Channel channel = ctx.channel();
    	String key = NetUtil.parseToString((InetSocketAddress) channel.remoteAddress());
    	if (channelMap.get(key) == null) {
    		channelMap.putIfAbsent(key, new NettyChannel(channel));
    	}
        //handle client connect event...
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	Channel channel = ctx.channel();
    	String key = NetUtil.parseToString((InetSocketAddress) channel.remoteAddress());
    	channelMap.remove(key);
    	//handle client disconnect event...
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ctx.fireChannelRead(msg);
    	io.netty.channel.Channel channel = ctx.channel();
    	System.out.println(channel + ">>>" + msg);
    	lam.cobia.remoting.Channel ch = channelMap.get(channel);
    	ByteBuf byteBuf = (ByteBuf) msg;
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String req = new String(bytes, "utf-8");
		Request request = gson.fromJson(req, Request.class);
    	handler.received(ch, request);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelReadComplete()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.fireChannelReadComplete();
    	ctx.channel().flush();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //ctx.fireUserEventTriggered(evt);
    	super.userEventTriggered(ctx, evt);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelWritabilityChanged()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        //ctx.fireChannelWritabilityChanged();
    	super.channelWritabilityChanged(ctx);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        //handle client error event...
    }
}
