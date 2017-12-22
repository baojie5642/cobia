package lam.cobia.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lam.cobia.rpc.DefaultInvocation;
import lam.cobia.rpc.Invocation;
import lam.cobia.rpc.Invoker;

/**
* <p>
* jdk proxy Factory
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public class JdkProxyFactory extends AbstractProxyFactory{

	@Override
	public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return (T) Proxy.newProxyInstance(classLoader, interfaces, new DefaultInvocationHandler(invoker));
	}
	
	private static class DefaultInvocationHandler implements InvocationHandler {
		
		private Invoker<?> invoker;
		
		public DefaultInvocationHandler(Invoker<?> invoker) {
			this.invoker = invoker;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Invocation invocation = new DefaultInvocation()
					.setMethod(method.getName()).setParamenterTypes(method.getParameterTypes()).setArguments(args);
			return invoker.invoke(invocation);
		}
		
	}

}
