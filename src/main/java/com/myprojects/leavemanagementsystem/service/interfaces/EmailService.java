package com.myprojects.leavemanagementsystem.service.interfaces;

import com.myprojects.leavemanagementsystem.entity.Holiday;
import com.myprojects.leavemanagementsystem.entity.LeaveRequest;

public interface EmailService {

    void sendPendingLeaveReminder(
            String managerEmail,
            LeaveRequest leaveRequest);
    void sendHolidayReminder(
            String email,
            Holiday holiday
    );

}