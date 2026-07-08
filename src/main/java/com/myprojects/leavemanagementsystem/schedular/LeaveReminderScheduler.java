package com.myprojects.leavemanagementsystem.schedular;
import com.myprojects.leavemanagementsystem.entity.LeaveRequest;
import com.myprojects.leavemanagementsystem.enums.LeaveStatus;
import com.myprojects.leavemanagementsystem.repository.LeaveRequestRepository;
import com.myprojects.leavemanagementsystem.service.interfaces.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveReminderScheduler {

    private final LeaveRequestRepository repository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    public void remindManagers() {

        LocalDateTime threeDaysAgo =
                LocalDateTime.now().minusDays(3);

        List<LeaveRequest> requests =
                repository.findByStatusAndAppliedAtBefore(
                        LeaveStatus.PENDING,
                        threeDaysAgo
                );

        requests.forEach(request ->

                emailService.sendPendingLeaveReminder(
                        request.getManager().getEmail(),
                        request
                )


        );
    }
}