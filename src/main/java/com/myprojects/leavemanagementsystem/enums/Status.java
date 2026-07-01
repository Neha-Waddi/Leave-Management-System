package com.myprojects.leavemanagementsystem.enums;

/**
 * NOTE: Both Employee.status and LeaveRequest.status are typed as this same
 * enum in the entity classes provided, so it has to carry both employee
 * lifecycle values and leave workflow values for the code to compile.
 * This is a design smell - see the "suggestions" notes for a recommendation
 * to split this into EmployeeStatus and LeaveStatus.
 */
public enum Status {
    // Employee lifecycle
    ACTIVE,
    INACTIVE,

    // Leave request workflow
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}
