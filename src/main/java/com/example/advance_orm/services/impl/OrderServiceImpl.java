package com.example.advance_orm.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.advance_orm.domain.order.PurchaseOrder;
import com.example.advance_orm.domain.order.PurchaseOrderItem;
import com.example.advance_orm.domain.payment.CardPaymentMethod;
import com.example.advance_orm.domain.payment.UpiPaymentMethod;
import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.dto.request.CreateOrderRequest;
import com.example.advance_orm.dto.response.OrderResponse;
import com.example.advance_orm.repository.PurchaseOrderRepository;
import com.example.advance_orm.repository.UserRepository;
import com.example.advance_orm.services.OrderService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final PurchaseOrderRepository purchaseOrderRepository;
	private final UserRepository userRepository;

	public OrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository, UserRepository userRepository) {
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.userRepository = userRepository;
	}

	@Override
	public OrderResponse create(CreateOrderRequest request) {
		User user = userRepository.findById(request.userId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

		PurchaseOrder order = new PurchaseOrder();
		order.setUser(user);
		order.setMetadata(request.metadata());

		for (int i = 0; i < request.items().size(); i++) {
			CreateOrderRequest.Item item = request.items().get(i);
			PurchaseOrderItem entity = new PurchaseOrderItem(order, i + 1, item.sku(), item.quantity(), item.unitPrice());
			order.getItems().add(entity);
		}

		CreateOrderRequest.Payment payment = request.payment();
		if (payment.type() == CreateOrderRequest.PaymentType.CARD) {
			order.setPaymentMethod(new CardPaymentMethod(payment.cardLast4(), payment.cardNetwork()));
		} else if (payment.type() == CreateOrderRequest.PaymentType.UPI) {
			order.setPaymentMethod(new UpiPaymentMethod(payment.vpa()));
		}

		order = purchaseOrderRepository.save(order);
		return OrderResponse.from(order);
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse get(Long id, boolean details) {
		PurchaseOrder order = details
				? purchaseOrderRepository.findWithItemsById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND))
				: purchaseOrderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return OrderResponse.from(order);
	}
}

