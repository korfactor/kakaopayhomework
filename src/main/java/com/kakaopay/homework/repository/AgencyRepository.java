package com.kakaopay.homework.repository;

import com.kakaopay.homework.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends CrudRepository<Agency, String> {
    //Optional<Agency> findByName(String name);

    Agency findByName(String institute_name);
    List<Agency> findAll();

    @Query("select name from Agency order by id asc")
    List<String> findAllInstituename();


}
