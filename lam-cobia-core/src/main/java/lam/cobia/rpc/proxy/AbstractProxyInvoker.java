package lam.cobia.rpc.proxy;

import lam.cobia.rpc.DefaultResult;
import lam.cobia.rpc.Invocation;
import lam.cobia.rpc.Invoker;
import lam.cobia.rpc.Result;

/**
* <p>
* abstract proxy invoker
* </p>
* @author linanmiao
* @date 2018年1月2日
* @version 1.0
*/
public abstract class AbstractProxyInvoker<T> implements Invoker<T> {
	
	private final T proxy;
	
	private final Class<T> clazz;
	
	public AbstractProxyInvoker(T proxy, Class<T> clazz) {
		this.proxy = proxy;
		this.clazz = clazz;
	}
	
	@Override
	public String getKey() {
		return clazz.getName();
	}
	
	@Override
	public Class<T> getInterface() {
		return clazz;
	}
	
	@Override
	public Result invoke(Invocation invocation) {
		Object result = doInvoke(proxy, invocation.getMethod(), invocation.getParameterTypes(), invocation.getArguments());
		return new DefaultResult().setValue(result);
	}
	
	protected abstract Object doInvoke(T proxy, String method, Class<?>[] parameterTypes, Object[] arguments);
	
	@Override
	public void close() {
	}

}
