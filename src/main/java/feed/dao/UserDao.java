package feed.dao;

import feed.entity.UserEntity;

public interface UserDao {
	public UserEntity getUserProfile(String pMobileNo);
	public UserEntity saveUser(UserEntity pUserEntity);
}
