package com.example.twitter;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ActiveProfiles("local")
@Transactional
public class IntegrationTest {
}
