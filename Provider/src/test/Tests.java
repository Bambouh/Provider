package test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tests implements ITest {

	public static void main(String[] args) {
		ITest test = new Tests();
		test.test();
	}
	
	@SuppressWarnings("serial")
	List<ITest> tests = new ArrayList<ITest>() {{
		add(new DeepCopyTest());
	}};

	@Override
	public boolean test() {
		boolean isOk = true;
		for(ITest test : tests) {
			boolean res = test.test();
			if(res) {
				Logger.getLogger(getName()).log(Level.FINE, test.getName() + " OK");
			} else {
				Logger.getLogger(getName()).log(Level.FINE, test.getName() + " KO ######");
			}
		}
		return isOk;
	}

	@Override
	public String getName() {
		return "The class to start running all test";
	}
	
	
	
}
