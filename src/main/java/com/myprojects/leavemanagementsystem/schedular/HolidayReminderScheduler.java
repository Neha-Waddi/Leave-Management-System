package com.myprojects.leavemanagementsystem.schedular;

import com.myprojects.leavemanagementsystem.entity.Holiday;
import com.myprojects.leavemanagementsystem.entity.Employee;
import com.myprojects.leavemanagementsystem.enums.EmployeeStatus;
import com.myprojects.leavemanagementsystem.enums.LeaveStatus;
import com.myprojects.leavemanagementsystem.repository.EmployeeRepository;
import com.myprojects.leavemanagementsystem.repository.HolidayRepository;
import com.myprojects.leavemanagementsystem.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayReminderScheduler {

    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailServiceImpl emailService;


//    @Scheduled(fixedRate = 15000)
    @Scheduled(cron = "0 0 9 * * *")
    public void sendHolidayReminder() {
        log.info("Holiday Scheduler Started");
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        holidayRepository.findByHolidayDate(tomorrow)
                .ifPresent(holiday -> {
                    log.info("Holiday Found : {}", holiday.getHolidayName());

                    List<Employee> employees =
                            employeeRepository.findByStatus(EmployeeStatus.ACTIVE);
                    log.info("Employees Found : {}", employees.size());

                    employees.forEach(employee -> {

                        emailService.sendHolidayReminder(
                                employee.getEmail(),
                                holiday
                        );

                        log.info("Holiday reminder sent to {}",
                                employee.getEmail());

                    });

                });

    }
}