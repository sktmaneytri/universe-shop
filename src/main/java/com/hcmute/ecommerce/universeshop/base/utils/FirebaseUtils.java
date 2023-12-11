package com.hcmute.ecommerce.universeshop.base.utils;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import com.hcmute.ecommerce.universeshop.base.config.FirebaseConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FirebaseUtils {
    @Resource
    FirebaseConfig firebaseConfig;

    String downloadUrlImage = "";
    String appCheckToken = "";
    private String generateAppCheckToken() {
        try {
            appCheckToken = Arrays.toString(FirebaseAuth.getInstance(firebaseConfig.firebaseApp())
                    .createCustomToken("gymrattest-fca2d")
                    .getBytes());

            return appCheckToken;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * - Get storage on firebase
     * - Set type for fileUpload and generateAppCheckToken
     * - Create a new fileUpload on storage
     * @param imageFile: Get from computer
     * @param fileName: Get the name of file from filePath
     * @return downloadUrl used to add to songPoster field
     * @throws IOException
     */
    public String uploadImage(MultipartFile imageFile, String fileName) throws IOException {
        try {
            InputStream fileContentImage = imageFile.getInputStream();

            Storage storage = StorageClient.getInstance(firebaseConfig.firebaseApp())
                    .bucket("gymrat-fca2d.appspot.com")
                    .getStorage();

            String timestamp = String.valueOf(System.currentTimeMillis());
            String objectName = "image/" + timestamp + "_" + fileName;

            BlobInfo blobInfo = BlobInfo.newBuilder("gymrat-fca2d.appspot.com", objectName)
                    .setContentType("image/jpeg")
                    .setMetadata(ImmutableMap.of("firebaseStorageDownloadTokens", generateAppCheckToken()))
                    .build();

            // Upload the file to Firebase Storage
            storage.create(blobInfo, fileContentImage,
                    Storage.BlobWriteOption.userProject("gymrat-fca2d"),
                    Storage.BlobWriteOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));

            String token = UUID.randomUUID().toString();
            String encodedToken = URLEncoder.encode(token, "UTF-8");

            // Construct the download URL using the full object name
            String downloadUrlImage = "https://firebasestorage.googleapis.com/v0/b/" +
                    "gymrat-fca2d.appspot.com" +
                    "/o/" +
                    URLEncoder.encode(objectName, "UTF-8") +
                    "?alt=media";

            // Append the token to the download URL
            downloadUrlImage = downloadUrlImage + "&token=" + encodedToken;

            return downloadUrlImage;
        } catch (Exception e) {
            // Handle any other exception
            throw new RuntimeException(e);
        }
    }

}