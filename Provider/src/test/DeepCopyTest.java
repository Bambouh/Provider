package test;

import java.util.HashMap;
import java.util.Map;

import provider.uti.DeepCopy;

public class DeepCopyTest implements ITest {

	@SuppressWarnings("serial")
	private Map<String, Integer> testMap1 = new HashMap<String, Integer>() {{
		put("Bonsoir", 44);
		put("Au revoir", 11);
		put("Hihou", 36);
		put("colouu", 111);
		put("Petit déjeuner", 8);
	}};
	
	@Override
	public boolean test() {
		Map<String, Integer> res1 = DeepCopy.copy(testMap1);
		
		//Does it have same size?
		if(res1.size() != testMap1.size())
			return false;
		
		//Has same keys?
		for(String key : testMap1.keySet()) {
			if(!res1.containsKey(key))
				return false;
		}
		
		//Works the same?
		if(!testMap1.get("Bonsoir").equals(res1.get("Bonsoir")))
			return false; 
		
		if(!testMap1.get("Au revoir").equals(res1.get("Au revoir")))
			return false;
		
		if(!testMap1.get("Hihou").equals(res1.get("Hihou")))
			return false;
		
		if(!testMap1.get("colouu").equals(res1.get("colouu")))
			return false;
		
		if(!testMap1.get("Petit déjeuner").equals(res1.get("Petit déjeuner")))
			return false;
		
		//Has deep and not shallow copy in it
		//TODO: test non shallow
		
		return true;
	}

	@Override
	public String getName() {
		return "DeepCopyTest";
	}

	
}
