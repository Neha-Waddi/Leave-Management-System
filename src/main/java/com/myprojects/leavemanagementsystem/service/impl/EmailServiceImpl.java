package com.myprojects.leavemanagementsystem.service.impl;

import com.myprojects.leavemanagementsystem.entity.Holiday;
import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendPendingLeaveReminder(
            String managerEmail,
            LeaveRequest leaveRequest) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(managerEmail);

        message.setSubject("Pending Leave Approval Reminder");

        message.setText(
                """
                Dear Manager,

                Employee %s has a pending leave request.

                Leave ID : %d

                Please review it.

                Regards,
                Leave Management System
                """.formatted(
                        leaveRequest.getEmployee().getFirstName(),
                        leaveRequest.getId()
                )
        );

        mailSender.send(message);
    }

    @Override
    public void sendHolidayReminder(
            String email,
            Holiday holiday) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Upcoming Holiday Reminder");

        message.setText(
                """
                Dear Employee,
    
                This is a reminder that tomorrow is a company holiday.
    
                Holiday Name : %s
                Holiday Date : %s
    
                Description :
                %s
    
                Enjoy your holiday!
    
                Regards,
                Leave Management System
                """.formatted(
                        holiday.getHolidayName(),
                        holiday.getHolidayDate(),
                        holiday.getDescription()
                )
        );

        mailSender.send(message);
    }

}
