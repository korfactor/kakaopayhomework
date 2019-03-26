package com.kakaopay.homework.repository;


import com.kakaopay.homework.model.HouseFund;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<HouseFund, String> {


    List<HouseFund> findByYear(Integer year);

    List<HouseFund> findByYearAndBank(Integer year, String bank);


    @Query("select distinct year from HouseFund order by year ASC")
    List<Integer> findDistinctByYear();

    @Query("select count(month) from HouseFund where year= ?1 and bank=?2")
    Integer findMonthDistinctByYear(Integer year,String bank);

    @Query("select SUM(budget) from HouseFund where year=?1 and bank=?2")
    Long sumBudgetAnnual(Integer year, String bank);

    @Query("select budget from HouseFund where bank=?1 order by year asc,month asc")
    double[] getWholeBudget(String bank);

}
