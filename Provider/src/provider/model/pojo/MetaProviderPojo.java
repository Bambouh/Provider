package provider.model.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaProviderPojo {

	private int id;
	private String name;
	private Date startDate, endDate;
	
	private List<ProviderPojo> providers;
	private List<Integer> providerIds;
	
	public MetaProviderPojo(int id, String name, Date startDate,
			Date endDate, List<ProviderPojo> providers) {
		
		providerIds = new ArrayList<Integer>(providers.size());
		for(ProviderPojo provider : providers) {
			providerIds.add(provider.getId());
			if(provider.getId() < 1) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"Cannot create metaProvider if one child has no ID");
				return;
			}
		}
		this.providers = providers;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	int getId() {
		return id;
	}
	
	void setId(int id) {
		this.id = id;
	}
	
	String getName() {
		return name;
	}
	
	Date getStartDate() {
		return startDate;
	}
	
	Date getEndDate() {
		return endDate;
	}
	
	List<ProviderPojo> getChildren() {
		return providers;
	}
	
	List<Integer> getChildrenIds() {
		return providerIds;
	}

}
