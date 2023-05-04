package com.springrest.linkcut.Exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String s){
        super(s);
    }
}
