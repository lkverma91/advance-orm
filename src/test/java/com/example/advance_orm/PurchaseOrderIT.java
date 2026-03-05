package com.example.advance_orm;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.advance_orm.domain.order.PurchaseOrder;
import com.example.advance_orm.domain.order.PurchaseOrderItem;
import com.example.advance_orm.domain.payment.CardPaymentMethod;
import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserStatus;
import com.example.advance_orm.repository.PurchaseOrderRepository;
import com.example.advance_orm.repository.UserRepository;
import com.example.advance_orm.testsupport.MySqlTestcontainersBase;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class PurchaseOrderIT extends MySqlTestcontainersBase {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	@Test
	@Transactional
	void compositeKey_items_and_inheritance_paymentMethod_persistAndLoad() {
		User user = new User();
		user.setEmail("order@example.com");
		user.setDisplayName("OrderUser");
		user.setStatus(UserStatus.ACTIVE);
		user.setPreferences(Map.of("k", "v"));
		user = userRepository.save(user);

		PurchaseOrder order = new PurchaseOrder();
		order.setUser(user);
		order.setMetadata(Map.of("source", "test"));
		order.setPaymentMethod(new CardPaymentMethod("1234", "VISA"));

		order.getItems().add(new PurchaseOrderItem(order, 1, "SKU-1", 2, new BigDecimal("10.00")));
		order.getItems().add(new PurchaseOrderItem(order, 2, "SKU-2", 1, new BigDecimal("5.50")));

		order = purchaseOrderRepository.save(order);

		PurchaseOrder loaded = purchaseOrderRepository.findWithItemsById(order.getId()).orElseThrow();
		assertThat(loaded.getItems()).hasSize(2);
		assertThat(loaded.getItems()).extracting(i -> i.getId().getLineNo()).containsExactlyInAnyOrder(1, 2);
		assertThat(loaded.getPaymentMethod()).isInstanceOf(CardPaymentMethod.class);
	}
}

