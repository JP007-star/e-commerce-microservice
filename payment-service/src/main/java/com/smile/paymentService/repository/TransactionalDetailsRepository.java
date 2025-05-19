package com.smile.paymentService.repository;

import com.smile.paymentService.entity.TransactionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionalDetailsRepository extends JpaRepository<TransactionalDetails,Long> {


}
