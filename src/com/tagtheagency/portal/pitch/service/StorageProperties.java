package com.tagtheagency.portal.pitch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value= {"classpath:application.properties"})
@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
	@Value("${pitch.uploaddir}")
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}