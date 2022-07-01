package feed.dao;

import java.util.List;

import feed.entity.FilterEntity;

public interface FiltersDao {
	public FilterEntity addFilters(FilterEntity pFilterEntity);
	public List<FilterEntity> getFilters();

}
