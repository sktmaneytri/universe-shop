package com.hcmute.ecommerce.universeshop.base.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    InputStream serviceAccount;
    /**
     * Initialize (Build) Firebase App
     * @throws IOException
     */
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Use a relative path to access the file within the package
        serviceAccount = new FileInputStream("src/main/resources/gymrat-fca2d-firebase-adminsdk-pe0ke-a18b04538c.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://gymrat-fca2d-default-rtdb.asia-southeast1.firebasedatabase.app") // Replace with your Firebase Realtime Database URL
                .setStorageBucket("gymrat-fca2d.appspot.com")
                .build();
        return FirebaseApp.initializeApp(options);
    }
}
