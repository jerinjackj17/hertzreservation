package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.CustomerRequestDTO;
import com.hertz.hertzreservation.dto.CustomerResponseDTO;
import com.hertz.hertzreservation.dto.BirthdayEventDTO;
import com.hertz.hertzreservation.entity.Customer;
import com.hertz.hertzreservation.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/*
 * CustomerServiceImpl
 *
 * Responsibilities:
 * 1. Persist customer to MongoDB
 * 2. Detect birthday during customer creation
 * 3. Publish birthday event to kafka-event-service
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository repository;
    private final RestTemplate restTemplate;

    // readable timestamp format e.g. "2026-03-18 14:32:05"
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
     * Kafka event service endpoint inside Docker network
     */
    private static final String KAFKA_EVENT_URL = "http://kafka-event-service:8082/events/birthday";

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {

        logger.info("Creating customer with email={}", request.getEmail());

        /*
         * Prevent duplicate customers
         */
        repository.findByEmail(request.getEmail())
                .ifPresent(c -> {
                    logger.error("Customer already exists with email={}", request.getEmail());
                    throw new RuntimeException("Customer with this email already exists");
                });

        /*
         * Build entity from request
         */
        Customer customer = new Customer(
                request.getFirstName(),
                request.getLastName(),
                request.getDateOfBirth(),
                request.getEmail(),
                request.getRentalsLastYear());

        /*
         * Save customer to MongoDB
         */
        Customer saved = repository.save(customer);

        logger.info("Customer saved successfully with id={}", saved.getId());

        /*
         * Birthday detection logic
         */
        logger.info("Checking birthday condition for customer={}", saved.getEmail());

        LocalDate today = LocalDate.now();

        int dobMonth = saved.getDateOfBirth().getMonthValue();
        int dobDay = saved.getDateOfBirth().getDayOfMonth();

        int todayMonth = today.getMonthValue();
        int todayDay = today.getDayOfMonth();

        logger.info("DOB={} Today={}", saved.getDateOfBirth(), today);
        logger.info("DOB month={} day={}", dobMonth, dobDay);
        logger.info("Today month={} day={}", todayMonth, todayDay);

        /*
         * If birthday today → publish Kafka event
         */
        if (dobMonth == todayMonth && dobDay == todayDay) {

            logger.info("Birthday detected for {}", saved.getEmail());

            BirthdayEventDTO event = new BirthdayEventDTO();

            event.setEventType("BIRTHDAY_EVENT");
            event.setCustomerName(saved.getFirstName() + " " + saved.getLastName());
            event.setCompanyName("Hertz");
            event.setEmail(saved.getEmail());

            // readable local timestamp instead of Instant unix format
            event.setEventTime(LocalDateTime.now().format(FORMATTER));

            try {

                restTemplate.postForObject(
                        KAFKA_EVENT_URL,
                        event,
                        String.class);

                logger.info("Birthday event successfully sent to kafka-event-service");

            } catch (Exception ex) {

                logger.error("Failed to send birthday event to kafka-event-service", ex);
            }
        }

        return mapToResponse(saved);
    }

    @Override
    public CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO request) {

        logger.info("Updating customer with id={}", id);

        Customer existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Customer not found with id={}", id);
                    return new RuntimeException("Customer not found");
                });

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setEmail(request.getEmail());
        existing.setRentalsLastYear(request.getRentalsLastYear());

        Customer updated = repository.save(existing);

        logger.info("Customer updated successfully id={}", updated.getId());

        return mapToResponse(updated);
    }

    @Override
    public void deleteCustomer(String id) {

        logger.info("Deleting customer with id={}", id);

        if (!repository.existsById(id)) {
            logger.error("Customer not found for deletion id={}", id);
            throw new RuntimeException("Customer not found");
        }

        repository.deleteById(id);

        logger.info("Customer deleted successfully id={}", id);
    }

    @Override
    public CustomerResponseDTO getCustomerByEmail(String email) {

        logger.info("Fetching customer by email={}", email);

        Customer customer = repository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Customer not found email={}", email);
                    return new RuntimeException("Customer not found");
                });

        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        logger.info("Fetching all customers");

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /*
     * Entity → Response DTO mapper
     */
    private CustomerResponseDTO mapToResponse(Customer customer) {

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getEmail(),
                customer.getRentalsLastYear());
    }
}