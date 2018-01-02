package lam.cobia.core;

import lam.cobia.config.spring.CobiaService;
import lam.cobia.core.service.IMyService;
import lam.cobia.core.service.MyService;

/**
* <p>
* TODO
* </p>
* @author linanmiao
* @date 2018年1月2日
* @version 1.0
*/
public class CobiaServiceTest {
	
	public static void main(String[] args) {
		IMyService myService = new MyService();
		
		CobiaService cobiaService = new CobiaService();
		
		cobiaService.export(myService, IMyService.class);
	}

}
