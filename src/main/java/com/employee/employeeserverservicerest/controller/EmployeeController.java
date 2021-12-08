package com.employee.employeeserverservicerest.controller;

import java.util.List;

import com.employee.employeeserverservicerest.service.EmployeeService;
import com.employee.employeeserverservicerest.service.CSVService;
import com.employee.employeeserverservicerest.helper.CSVHelper;
import com.employee.employeeserverservicerest.model.Employee;
import com.employee.employeeserverservicerest.response.GetUsersResponse;
import com.employee.employeeserverservicerest.response.UploadResponse;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private CSVService fileService;

    private static boolean validateSortString(String sortString) {
        if (sortString != null) {
            String loweredSortString = sortString.toLowerCase();
            if (!loweredSortString.equals(EmployeeService.NAME) && !loweredSortString.equals(EmployeeService.SALARY)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateMinMaxCompliment(Float min, Float max) {
        if (min != null && max != null) {
            if (max < min) {
                return false;
            }
        }
        return true;
    }

    @GetMapping("/users")
    public ResponseEntity<GetUsersResponse> list(
        @RequestParam(name = "min", defaultValue = "0.0") Float min,
        @RequestParam(name = "max", defaultValue = "4000.0") Float max,
        @RequestParam(name = "offset", defaultValue = "0") Integer offset,
        @RequestParam(name = "limit", required = false) Integer limit,
        @RequestParam(name = "sort", required= false) String sortString
    ) {
        if (!validateSortString(sortString)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GetUsersResponse()
                .setError(String.format("Invalid sort parameter - '%s', please enter either name or salary", sortString)));
        }

        if (!validateMinMaxCompliment(min, max)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GetUsersResponse()
                .setError("Max should be greater than min"));
        }
        
        List<Employee> results = service.findEmployees(min, max, offset, limit, sortString);
        return ResponseEntity.ok().body(new GetUsersResponse().setResults(results));
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);
                return ResponseEntity.status(HttpStatus.OK).body(UploadResponse.success());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(UploadResponse.failure(e.getMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UploadResponse.failure("Please upload a csv file"));
      }
}
