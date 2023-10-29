package com.sv.paymentservice.service;

import com.sv.paymentservice.domain.Payment;
import com.sv.paymentservice.dto.PaymentDTO;
import com.sv.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sv.paymentservice.domain.Payment.toDto;
import static com.sv.paymentservice.domain.Payment.toEntity;
import static com.sv.paymentservice.domain.PaymentStatus.COMPLETED;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        log.debug("Request to save Payment : {}", paymentDTO);
        Payment payment = paymentRepository.save(
                toEntity(PaymentDTO.builder()
                        .orderId(paymentDTO.orderId())
                        .status(COMPLETED)
                        .total(paymentDTO.total())
                        .build())
        );
        return toDto(payment);
    }

    @Override
    public PaymentDTO update(PaymentDTO paymentDTO) {
        return save(paymentDTO);
    }

    @Override
    public Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO) {
        log.debug("Request to partially update Payment : {}", paymentDTO);
        return paymentRepository
                .findById(paymentDTO.id())
                .map(existingPayment -> {
                    if (paymentDTO.status() != null) existingPayment.setStatus(paymentDTO.status());
                    if (paymentDTO.orderId() != null) existingPayment.setOrderId(paymentDTO.orderId());
                    if (paymentDTO.total() != null) existingPayment.setTotal(paymentDTO.total());
                    return existingPayment;
                })
                .map(paymentRepository::save)
                .map(Payment::toDto);    }

    @Override
    public Page<PaymentDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Payment> result = paymentRepository.findAll(pageable);
        return result.map(Payment::toDto);
    }

    @Override
    public Optional<PaymentDTO> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id).map(Payment::toDto);    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }

    @Override
    public boolean exist(Long id) {
        return paymentRepository.existsById(id);
    }
}
