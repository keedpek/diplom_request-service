package com.example.request_service.repository.specifications;

import com.example.request_service.DTO.request.RequestFilter;
import com.example.request_service.entity.Request;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.enums.RequestStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RequestSpecifications {
  public static Specification<Request> withFilter(RequestFilter filter) {
    if (filter == null) {
      return null;
    }
    return (root, query, cb) -> {

      List<Predicate> predicates = new ArrayList<>();

      if (filter.getStatus() != null) {
        predicates.add(cb.equal(
                root.get("status"),
                RequestStatus.valueOf(filter.getStatus())
        ));
      }

      if (filter.getPriority() != null) {
        predicates.add(cb.equal(
                root.get("priority"),
                RequestPriority.valueOf(filter.getPriority())
        ));
      }

      if (filter.getCategoryCode() != null) {
        predicates.add(cb.equal(
                root.get("category").get("code"),
                filter.getCategoryCode()
        ));
      }

      if (filter.getAssignedToUserId() != null) {
        predicates.add(cb.equal(
                root.get("assignedToUserId"),
                filter.getAssignedToUserId()
        ));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
