package com.hyunto.startspringboot.persistence;

import com.hyunto.startspringboot.domain.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long>{
}
