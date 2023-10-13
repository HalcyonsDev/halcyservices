package com.halcyon.fraud.repository;

import com.halcyon.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFraudCheckHistoryRepository extends JpaRepository<FraudCheckHistory, Long> {
}