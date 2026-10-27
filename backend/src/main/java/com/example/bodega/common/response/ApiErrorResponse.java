package com.example.bodega.common.response;
import java.time.Instant;
import java.util.Map;
public record ApiErrorResponse(Instant timestamp, int status, String error, String message, Map<String, String> fields) { }
