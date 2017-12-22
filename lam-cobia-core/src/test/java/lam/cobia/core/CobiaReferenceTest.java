package lam.cobia.core;

import lam.cobia.config.spring.CobiaReference;

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
		Test test = new Test(){

			@Override
			public String doIt(String[] ss) {
				System.out.println(ss);
				return "s of " + ss;
			}};
		CobiaReference cr = new CobiaReference();
		Test t = cr.refer(Test.class);
		String s = t.doIt(new String[]{"ab", "bb", "cd"});
		System.out.println(s);
	}
	
	public interface Test{
		public String doIt(String[] ss);
	}

}
