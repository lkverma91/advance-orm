package com.example.advance_orm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.advance_orm.domain.order.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {

	@EntityGraph(attributePaths = { "items", "paymentMethod" })
	Optional<PurchaseOrder> findWithItemsById(Long id);
}

