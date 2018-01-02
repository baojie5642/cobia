package lam.cobia.rpc;
/**
* <p>
* default exporter
* </p>
* @author linanmiao
* @date 2018年1月2日
* @version 1.0
*/
public class DefaultExporter<T> implements Exporter<T> {

	private final Invoker<T> invoker;
	
	private final String key;
	
	private volatile boolean closed = false;
	
	public DefaultExporter(Invoker<T> invoker, String key) {
		this.invoker = invoker;
		this.key = key;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public Invoker<T> getInvoker() {
		return invoker;
	}

	@Override
	public void close() {
		if (!closed) {
			closed = true;
			getInvoker().close();
		}
	}

}
