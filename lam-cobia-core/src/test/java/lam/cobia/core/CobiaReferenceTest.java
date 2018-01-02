package lam.cobia.core;

import lam.cobia.config.spring.CobiaReference;
import lam.cobia.core.service.IMyService;

/**
* <p>
* TODO
* </p>
* @author linanmiao
* @date 2017年12月19日
* @version 1.0
*/
public class CobiaReferenceTest {

	public static void main(String[] args) {
		
		CobiaReference cr = new CobiaReference();
		IMyService myService = cr.refer(IMyService.class);
		String result = myService.doIt("cobia", 1);
		System.out.println(result);
		boolean r = myService.doSomething(20L, 2);
		System.out.println(r);
	}
	
	public interface Test{
		public String doIt(String[] ss);
	}

}
