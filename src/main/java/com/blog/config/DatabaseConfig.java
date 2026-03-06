package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    // Configuration is now handled by application.properties
    // This class is kept for backward compatibility
}