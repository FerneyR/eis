package com.attendance.attendance.controllers;

import com.attendance.attendance.dto.AttendanceDTO;
import com.attendance.attendance.services.AttendanceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(AttendanceController.class)
class TestAttendanceController {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AttendanceController attendanceController;

    @MockBean
    private AttendanceService attendanceService;

    @Test
    void testSave() throws Exception {
        when(attendanceService.save(any(AttendanceDTO.class))).thenReturn("El empleado con el id 1 salió a las: 00:00:00.0");

        mockMvc.perform(MockMvcRequestBuilders.post("/attendance/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCustomer\":1,\"idEmployee\":1,\"photo\":\"Photo\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("El empleado con el id 1 salió a las: 00:00:00.0"));
    }
}
