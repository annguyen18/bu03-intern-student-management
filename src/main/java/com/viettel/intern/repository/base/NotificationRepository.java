package com.viettel.intern.repository.base;

import com.viettel.intern.entity.base.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    Page<Notification> findByCreatedDate(Date createdDate, Pageable pageable);
}
