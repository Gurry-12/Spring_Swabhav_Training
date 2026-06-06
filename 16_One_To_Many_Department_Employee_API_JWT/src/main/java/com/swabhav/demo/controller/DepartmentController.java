package com.swabhav.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swabhav.demo.dto.DepartmentRequestDto;
import com.swabhav.demo.dto.DepartmentResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Department API", description = "Manage Department crud role based")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Create Department", description = "Returns a created department.")
    public DepartmentResponseDto createDepartment(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        log.info("REST request to create Department with name: {}", departmentRequestDto.getDepartmentName());
        
        DepartmentResponseDto response = departmentService.createDepartment(departmentRequestDto);
        
        log.info("Successfully created Department with ID: {} and Name: {}", 
                   response.getId(), response.getDepartmentName());
        return response;
    }

    @GetMapping
    @Operation(summary = "Get all Departments", description = "Returns a list of department objects.")
    public List<DepartmentResponseDto> getAllDepartments() {
        log.info("REST request to fetch all Departments");
        
        List<DepartmentResponseDto> departmentResponseDtos = departmentService.getAllDepartments();
        
        log.info("Successfully fetched {} department(s)", departmentResponseDtos.size());
        return departmentResponseDtos;
    }

    @GetMapping("/page")
    @Operation(summary = "Get departments by pagination", description = "Returns list of departments with pagination data.")
    public PageResponseDto<DepartmentResponseDto> getAllDepartmentsWithPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        
        log.info("REST request to fetch departments with pagination - Page: {}, Size: {}", pageNumber, pageSize);
        
        PageResponseDto<DepartmentResponseDto> pageResponse = departmentService
                .getAllDepartmentsWithPagination(pageNumber, pageSize);
        
        log.info("Successfully fetched page - Total Elements: {}, Total Pages: {}, Current Page: {}", 
                   pageResponse.getTotalElements(), pageResponse.getTotalPages(), pageNumber);
        return pageResponse;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID", description = "Returns a single department object.")
    public DepartmentResponseDto getDepartmentById(@PathVariable Long id) {
        log.info("REST request to fetch Department by ID: {}", id);
        
        DepartmentResponseDto response = departmentService.getDepartmentById(id);
        
        if (response == null) {
            log.warn("Department with ID: {} not found", id);
        } else {
            log.info("Successfully fetched Department with ID: {} and Name: {}", 
                       response.getId(), response.getDepartmentName());
        }
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update department by ID", description = "Returns an updated department object.")
    public DepartmentResponseDto updateDepartment(@PathVariable Long id,
                                                  @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        
        log.info("REST request to update Department ID: {} with name: {}", 
                   id, departmentRequestDto.getDepartmentName());
        
        DepartmentResponseDto response = departmentService.updateDepartment(id, departmentRequestDto);
        
        log.info("Successfully updated Department with ID: {} and Name: {}", 
                   response.getId(), response.getDepartmentName());
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete department by ID", description = "Returns 204 No Content.")
    public void deleteDepartment(@PathVariable Long id) {
        log.info("REST request to delete Department with ID: {}", id);
        
        departmentService.deleteDepartment(id);
        
        log.info("Successfully deleted Department with ID: {}", id);
    }
}