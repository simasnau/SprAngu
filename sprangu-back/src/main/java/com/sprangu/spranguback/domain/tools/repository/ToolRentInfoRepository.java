package com.sprangu.spranguback.domain.tools.repository;

import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRentInfoRepository extends JpaRepository<ToolRentInfo, Long>, ToolRentInfoRepositoryCustom {
}
