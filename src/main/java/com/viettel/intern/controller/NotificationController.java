package com.viettel.intern.controller;

import com.viettel.intern.entity.base.Notification;
import com.viettel.intern.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

//    @GetMapping("/notifications")
//    public Page<Notification> getNotifications(@RequestParam Optional<String> sortBy,
//                                               @RequestParam Optional<Integer> page,
//                                               @RequestParam Optional<Integer> size,
//                                               @RequestParam(name = "createdDate", required = false)
//                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate) {
//
//        Sort.Direction sortDirection = Sort.Direction.DESC;
//        String sortField = sortBy.orElse("createdDate");
//
//        PageRequest pageable = PageRequest.of(page.orElse(0), size.orElse(10), sortDirection, sortField);
//        return notificationService.findAll(createdDate, pageable);
//    }
}
