package com.example.demo;

import com.example.demo.model.SupplyRequest;
import com.example.demo.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuickReliefApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RequestService requestService;

    @BeforeEach
    void clearDatabase() {
        jdbcTemplate.update("DELETE FROM requests");
        jdbcTemplate.update("DELETE FROM supplies");
    }

    @Test
    void rootShowsAvailableApiRoutes() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").value("QuickRelief"))
                .andExpect(jsonPath("$.supplies").value("/api/supplies"))
                .andExpect(jsonPath("$.requests").value("/api/requests"));
    }

    @Test
    void addsRestocksAndListsSupplies() throws Exception {
        mockMvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"First Aid Kit","quantity":5}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"First Aid Kit","quantity":3}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/supplies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value("First Aid Kit"))
                .andExpect(jsonPath("$[0].quantity").value(8));
    }

    @Test
    void allocatesSupplyAndReturnsGeneratedRequestId() throws Exception {
        addSupply("Water", 6);

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"Water","quantity":2,"requestedBy":"Campus Team"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("APPROVED"));

        assertEquals(4, supplyQuantity("Water"));
        mockMvc.perform(get("/api/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value("Water"))
                .andExpect(jsonPath("$[0].quantity").value(2));
    }

    @Test
    void rejectsInvalidAndUnavailableRequestsWithUsefulStatuses() throws Exception {
        addSupply("Food Packet", 2);

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"Food Packet","quantity":3,"requestedBy":"Resident"}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Insufficient stock available"));

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"Unknown","quantity":1,"requestedBy":"Resident"}
                                """))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"itemName":"Food Packet","quantity":0,"requestedBy":"Resident"}
                                """))
                .andExpect(status().isBadRequest());

        assertEquals(2, supplyQuantity("Food Packet"));
        assertEquals(0, jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM requests",
                Integer.class
        ));
    }

    @Test
    void rollsBackInventoryDeductionIfRequestCannotBeSaved() {
        addSupply("Medical Kit", 4);
        SupplyRequest request = new SupplyRequest(
                null,
                "Medical Kit",
                2,
                null,
                "R".repeat(101)
        );

        assertThrows(
                DataIntegrityViolationException.class,
                () -> requestService.createRequest(request)
        );
        assertEquals(4, supplyQuantity("Medical Kit"));
        assertEquals(0, jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM requests",
                Integer.class
        ));
    }

    private void addSupply(String itemName, int quantity) {
        jdbcTemplate.update(
                "INSERT INTO supplies (item_name, quantity) VALUES (?, ?)",
                itemName,
                quantity
        );
    }

    private int supplyQuantity(String itemName) {
        return jdbcTemplate.queryForObject(
                "SELECT quantity FROM supplies WHERE item_name = ?",
                Integer.class,
                itemName
        );
    }
}
