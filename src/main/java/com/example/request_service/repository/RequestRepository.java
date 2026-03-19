package com.example.request_service.repository;

import com.example.request_service.entity.Request;
import com.example.request_service.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID>, JpaSpecificationExecutor<Request> {
  List<Request> findByStatus(RequestStatus status);

  List<Request> findByCreatedByUserId(UUID userId);

  List<Request> findByAssignedToUserId(UUID userId);
}
