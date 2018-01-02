package lam.cobia.rpc;
/**
* <p>
* abstract invoker
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public abstract class AbstractInvoker<T> implements Invoker<T>{
	
	private final Class<T> classInterface;
	
	public AbstractInvoker(Class<T> classInterface) {
		this.classInterface = classInterface;
	}
	
	@Override
	public String getKey() {
		return classInterface.getName();
	}

	@Override
	public Class<T> getInterface() {
		return classInterface;
	}

	@Override
	public Result invoke(Invocation invocation) {
		//do some common operation...
		
		return doInvoke(invocation);
	}
	
	public abstract Result doInvoke(Invocation invocation);
	
	public abstract void close();

}
