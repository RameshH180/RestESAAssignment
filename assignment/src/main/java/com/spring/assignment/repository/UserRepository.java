package com.spring.assignment.repository;

import com.spring.assignment.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository /*extends ElasticsearchRepository<User, Long>*/ {


}
