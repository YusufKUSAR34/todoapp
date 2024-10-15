package com.hepsi.emlak.todo.repository;

import com.hepsi.emlak.todo.entity.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo, String> {
    List<Todo> findAllByUserId(String userId);

    Optional<Todo> findByIdAndUserId(String id, String userId);
}
