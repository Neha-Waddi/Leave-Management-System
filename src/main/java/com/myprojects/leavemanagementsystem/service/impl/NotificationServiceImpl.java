package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.service.interfaces.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyLeaveApplied(LeaveRequest leaveRequest) {
        log.info("Notify: leave {} applied by employee {}, pending approval from manager {}",
                leaveRequest.getId(), leaveRequest.getEmployee().getId(),
                leaveRequest.getManager() != null ? leaveRequest.getManager().getId() : "N/A");
    }

    @Override
    public void notifyLeaveApproved(LeaveRequest leaveRequest) {
        log.info("Notify: leave {} approved for employee {}",
                leaveRequest.getId(), leaveRequest.getEmployee().getId());
    }

    @Override
    public void notifyLeaveRejected(LeaveRequest leaveRequest) {
        log.info("Notify: leave {} rejected for employee {}",
                leaveRequest.getId(), leaveRequest.getEmployee().getId());
    }

    @Override
    public void notifyLeaveCancelled(LeaveRequest leaveRequest) {
        log.info("Notify: leave {} cancelled for employee {}",
                leaveRequest.getId(), leaveRequest.getEmployee().getId());
    }
}
