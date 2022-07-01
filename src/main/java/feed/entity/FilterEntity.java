package feed.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(value = "filters")
public class FilterEntity {

	@Id
	@Field(name="_id",targetType = FieldType.OBJECT_ID)
	private String filterId;
	
	@Field(name="filter_name")
	private String filterName;
	
	@Field(name="filter_desc")
	private String filterDescription;
	
	
	@Field(name="filter_path")
	private String filterPath;
	
	@Field(name="filter_icon")
	private String filterIcon;
	
	public String getFilterId() {
		return filterId;
	}
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterPath() {
		return filterPath;
	}
	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}
	
	
	
	public String getFilterIcon() {
		return filterIcon;
	}
	public void setFilterIcon(String filterIcon) {
		this.filterIcon = filterIcon;
	}
	
	
	
	
	public String getFilterDescription() {
		return filterDescription;
	}
	public void setFilterDescription(String filterDescription) {
		this.filterDescription = filterDescription;
	}
	
	
	@Override
	public String toString() {
		return "FilterEntity [filterId=" + filterId + ", filterName=" + filterName + ", filterDescription="
				+ filterDescription + ", filterPath=" + filterPath + ", filterIcon=" + filterIcon + "]";
	}
	
	
	
	
	
	
	
	
}
