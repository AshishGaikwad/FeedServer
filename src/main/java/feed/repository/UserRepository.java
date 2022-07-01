package feed.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import feed.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>{
	
	List<UserEntity> findByMobile(String mobile);
	List<UserEntity> findByUserName(String pUsername);
}
