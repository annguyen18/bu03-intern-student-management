package com.viettel.intern.service.impl;

import com.viettel.intern.config.locale.Translator;
import com.viettel.intern.entity.base.Course;
import com.viettel.intern.exception.BusinessException;
import com.viettel.intern.repository.base.CourseRepository;
import com.viettel.intern.service.AdminService;
import com.viettel.intern.ultis.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CourseRepository courseRepository;
    @Override
    public Page<Course> searchAndSortCourses(String keyword, int page, int size, String sortBy, String sortOrder) {
        if (SecurityUtil.hasCurrentUserAnyOfAuthorities("admin") || SecurityUtil.hasCurrentUserAnyOfAuthorities("teacher")){
            Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            throw new BusinessException(Translator.toLocale("user.not-authorized"));
        }
    }
}
