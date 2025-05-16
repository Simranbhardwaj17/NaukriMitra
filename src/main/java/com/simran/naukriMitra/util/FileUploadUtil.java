package com.simran.naukriMitra.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	//mthd:-saveFile that pass and upload directory, a file name and a reference to the multi-part file (multi-part file actually has the image file that the user uploaded from the form)
	public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {  
		Path uploadPath = Paths.get(uploadDir);     //set up upload path, make sure that that given directory exists
		//If not, create the directory
		if (!Files.exists(uploadPath)) {   
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {   //read the input stream
			Path path = uploadPath.resolve(filename);   //Path setup
			System.out.println("FilePath " + path);
			System.out.println("fileName " + filename);
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);   //copy the content from the input stream to the path
			
		} catch(IOException ioe) {
			throw new IOException("Could not save image file: " + filename, ioe);
		})
	}
}
