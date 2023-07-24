package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.Notification;
import com.viettel.intern.repository.base.NotificationRepository;
import com.viettel.intern.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public void save(Notification notification) {
        repository.save(notification);
    }

    @Override
    public Page<Notification> findAll(Date createdDate, Pageable pageable) {
        if (createdDate != null) {
            return notificationRepository.findByCreatedDate(createdDate, pageable);
        } else {
            return notificationRepository.findAll(pageable);
        }
    }
}
