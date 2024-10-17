package com.example.trello.common.path;

import java.io.File;

public class GlobalPath {
    public static final String DELIMITER = "_";
    public static final String SEMICOLON = ":";
    // OS에 맞는 구분자를 반환
    public static final String SEPARATOR = File.separator;
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HOST = "localhost";
    public static final String PORT = "8080";
    public static final String FILES = "files";
    // http://localhost:8080
    public static final String BASE_URL = HTTP + SEMICOLON  + SEPARATOR + SEPARATOR + HOST + SEMICOLON + PORT;
    public static final String S3_BASE_URL = "https://spring-trello-files.s3.ap-northeast-2.amazonaws.com";
}
