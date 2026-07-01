package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.entity.LeaveRequest;

public interface NotificationService {

    void notifyLeaveApplied(LeaveRequest leaveRequest);

    void notifyLeaveApproved(LeaveRequest leaveRequest);

    void notifyLeaveRejected(LeaveRequest leaveRequest);

    void notifyLeaveCancelled(LeaveRequest leaveRequest);
}
