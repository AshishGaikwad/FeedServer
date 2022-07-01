package feed.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import feed.entity.FilterEntity;

@Repository
public interface FiltersRepository extends MongoRepository<FilterEntity, String> {
	
	
	
}
