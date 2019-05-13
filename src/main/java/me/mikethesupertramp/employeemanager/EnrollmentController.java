package me.mikethesupertramp.employeemanager;

import me.mikethesupertramp.employeemanager.dao.Employee;
import me.mikethesupertramp.toolkit.rfid.SequenceListener;

import java.util.Optional;

public class EnrollmentController implements SequenceListener {
    private EmployeeDatabaseManager db;

    public EnrollmentController(EmployeeDatabaseManager db) {
        this.db = db;
    }

    @Override
    public void onSequence(String sequence) {
        int cardId = Integer.parseInt(sequence);

        Optional<Employee> result = db.employee.geByCardId(cardId);

        if (result.isPresent()) {
            Employee employee = result.get();
            if (employee.getCardID1() == cardId) {
                if (!employee.isPresent()) {
                    onEmployeeEnter(employee);
                } else {
                    //todo show error
                }
            } else {
                if (employee.isPresent()) {
                    onEmployeeExit(employee);
                } else {
                    //todo show error
                }
            }
        } else {
            //todo show error message
            System.out.println("User not found");
        }
    }

    private void onEmployeeExit(Employee employee) {
        employee.setPresent(false);
        db.employee.update(employee);
    }

    private void onEmployeeEnter(Employee employee) {
        employee.setPresent(true);
        db.employee.update(employee);
    }
}
