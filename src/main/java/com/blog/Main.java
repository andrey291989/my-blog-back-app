package com.blog;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {

            Tomcat tomcat = new Tomcat();

            tomcat.setPort(8080);

            String baseDir = createTempDirectory();
            tomcat.setBaseDir(baseDir);

            Context context = tomcat.addContext("", new File("src/main/webapp").getAbsolutePath());

            context.addApplicationListener("com.blog.WebAppInitializer");

            tomcat.start();

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