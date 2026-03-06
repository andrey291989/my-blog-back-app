package com.blog;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Tomcat server...");

            Tomcat tomcat = new Tomcat();

            // Установка порта
            tomcat.setPort(8080);
            System.out.println("Port set to 8080");

            // Создание базовой директории для Tomcat
            String baseDir = createTempDirectory();
            tomcat.setBaseDir(baseDir);
            System.out.println("Base directory: " + baseDir);

            // Создание контекста
            Context context = tomcat.addContext("", new File("src/main/webapp").getAbsolutePath());
            System.out.println("Context created with docBase: " + new File("src/main/webapp").getAbsolutePath());

            // Добавление слушателя для инициализации Spring
            context.addApplicationListener("com.blog.WebAppInitializer");
            System.out.println("WebAppInitializer listener added");

            // Запуск Tomcat
            System.out.println("Starting Tomcat...");
            tomcat.start();
            System.out.println("Tomcat started successfully on port 8080");
            System.out.println("Application should be available at http://localhost:8080/");

            tomcat.getServer().await();
        } catch (Exception e) {
            System.err.println("Failed to start Tomcat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String createTempDirectory() {
        try {
            File tempDir = new File(System.getProperty("java.io.tmpdir"), "blog-app");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            return tempDir.getAbsolutePath();
        } catch (Exception e) {
            return System.getProperty("java.io.tmpdir");
        }
    }
}