package lam.cobia.rpc;

import java.util.Arrays;

/**
* <p>
* default invocation
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public class DefaultInvocation implements Invocation{
	
	private String method;
	
	private Class<?>[] paramenterTypes;
	
	private Object[] arguments;
	
	public DefaultInvocation setMethod(String method) {
		this.method = method;
		return this;
	}
	
	public DefaultInvocation setParamenterTypes(Class<?>[] paramenterTypes) {
		this.paramenterTypes = paramenterTypes;
		return this;
	}
	
	public DefaultInvocation setArguments(Object[] arguments) {
		this.arguments = arguments;
		return this;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return paramenterTypes;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return "DefaultInvocation [method=" + method + ", paramenterTypes=" + Arrays.toString(paramenterTypes)
				+ ", arguments=" + Arrays.toString(arguments) + "]";
	}
	
}
