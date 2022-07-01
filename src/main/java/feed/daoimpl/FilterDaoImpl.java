package feed.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feed.dao.FiltersDao;

import feed.entity.FilterEntity;
import feed.repository.FiltersRepository;

@Service
public class FilterDaoImpl implements FiltersDao{
	
	@Autowired
	private FiltersRepository lFiltersRepository;
	
	@Override
	public FilterEntity addFilters(FilterEntity pFilterEntity) {
		try {
			pFilterEntity = lFiltersRepository.save(pFilterEntity);
			return pFilterEntity;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<FilterEntity> getFilters() {
		try {
			return lFiltersRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}

}
