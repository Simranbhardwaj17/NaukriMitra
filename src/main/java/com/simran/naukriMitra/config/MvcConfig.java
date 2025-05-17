package com.simran.naukriMitra.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// This configuration class will map request for /photos to serve files from a directory on our file system.
public class MvcConfig implements WebMvcConfigurer{

	public static final String UPLOAD_DIR = "photos";  //set name of UPLOAD_DIR as photos

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory(UPLOAD_DIR, registry);  //override the default implementation to set up a custom resource handler
	}

	private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
		Path path = Paths.get(uploadDir);   //set up the path to the upload directory
		registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");  //at end, I append a slash. So  this is going to convert the upload directory string to a path
	}
}


//Line::23
//So basically this is going to convert the upload directory string to a path.
//
//This maps the web URL request starting with: "/photos/**" to a file system location 
//
//for example   file:<absolute path to the photos directory>
//
//So basically here anything coming in for a given web request for /photos, it'll map over to the actual files on our given file system to show or share or expose those given photos.
//
//And also recall here the star star will match on any subdirectories or sub URLs for those given a request.