package lam.cobia.core.service;
/**
* <p>
* TODO
* </p>
* @author linanmiao
* @date 2018年1月2日
* @version 1.0
*/
public class MyService implements IMyService {

	@Override
	public String doIt(String s, int i) {
		System.out.println(s + "," + i);
		return s + i;
	}

	@Override
	public boolean doSomething(long lon, int i) {
		System.out.println(lon + "," + i);
		return i > 0;
	}

}
