package com.viettel.intern.service.impl;

import com.viettel.intern.entity.base.ClassSchedule;
import com.viettel.intern.repository.base.ClassScheduleRepository;
import com.viettel.intern.service.ScheduleService;

import java.util.List;

public class ClassScheduleServiceImpl implements ScheduleService {
    private ClassScheduleRepository classScheduleRepository;
    @Override
    public List<ClassSchedule> getAllClassSChedule() {
        return classScheduleRepository.findAll();
    }
}
