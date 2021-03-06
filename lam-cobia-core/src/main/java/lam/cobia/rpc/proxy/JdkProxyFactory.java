package lam.cobia.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lam.cobia.core.exception.CobiaException;
import lam.cobia.rpc.DefaultInvocation;
import lam.cobia.rpc.Invocation;
import lam.cobia.rpc.Invoker;
import lam.cobia.rpc.Result;

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
			Result result = invoker.invoke(invocation);
			if (result == null) {
				throw new CobiaException("result == null");
			}
			return result.getValue();
		}
		
	}

	@Override
	public <T> Invoker<T> getInvoker(T ref, Class<T> clazz) {
		Invoker<T> invoker = new AbstractProxyInvoker<T>(ref, clazz) {
			@Override
			protected Object doInvoke(T proxy, String method, Class<?>[] parameterTypes, Object[] arguments) {
				try {
					Method m = proxy.getClass().getMethod(method, parameterTypes);
					return m.invoke(proxy, arguments);
				} catch (NoSuchMethodException e) {
					throw new CobiaException(e);
				} catch (SecurityException e) {
					throw new CobiaException(e);
				} catch (IllegalAccessException e) {
					throw new CobiaException(e);
				} catch (IllegalArgumentException e) {
					throw new CobiaException(e);
				} catch (InvocationTargetException e) {
					throw new CobiaException(e);
				}
			}
		};
		return invoker;
	}

}
