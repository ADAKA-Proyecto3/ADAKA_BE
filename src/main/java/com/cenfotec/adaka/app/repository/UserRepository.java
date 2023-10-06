package com.cenfotec.adaka.app.repository;

import com.cenfotec.adaka.app.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
