package com.fourimpact.TaskManagementWithDbPersistence.Exception;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String resourceName, Long id){
        super(resourceName + " not found with id: " + id);
    }
}
