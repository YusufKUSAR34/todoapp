package com.hepsi.emlak.todo.repository;

import com.hepsi.emlak.todo.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {

    User findByUserName(String userName);
}
