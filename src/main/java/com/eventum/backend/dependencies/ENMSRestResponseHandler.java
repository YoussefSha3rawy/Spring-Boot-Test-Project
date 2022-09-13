package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Component
public class ENMSRestResponseHandler {

  public <T> ResponseEntity<T> handleJsonOKStatus() {
    return new ResponseEntity<>(createJSONTypeHeaders(), HttpStatus.OK);
  }

  public <T> ResponseEntity<T> handleJsonOKStatus(T body) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.OK);
  }

  public <T> ResponseEntity<T> handleJsonNoContentStatus() {
    return new ResponseEntity<>(createJSONTypeHeaders(), HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<?> handleJsonOKStatus(URI location) {
    ResponseEntity<?> result = handleJsonOKStatus();
    result.getHeaders().setLocation(location);
    return result;
  }

  public <T> ResponseEntity<T> handleJsonOKStatus(T body, URI location) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.OK);
  }

  public <T> ResponseEntity<T> handleJsonFailStatus(T body) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public <T> ResponseEntity<T> handleJsonFailStatus(T body, HttpStatus status) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), status);
  }

  public <T> ResponseEntity<T> handleJsonSpecificResponseStatus(T body, int httpStatus) {
    return ResponseEntity.status(httpStatus).headers(createJSONTypeHeaders()).body(body);
  }

  public <T> ResponseEntity<T> handleJsonFailStatus(T body, URI location) {
    ResponseEntity<T> result = new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    result.getHeaders().setLocation(location);
    return result;
  }

  public <T> ResponseEntity<T> handleJsonFailStatus(URI location, HttpStatus status) {
    ResponseEntity<T> result = new ResponseEntity<>(createJSONTypeHeaders(), status);
    result.getHeaders().setLocation(location);
    return result;
  }

  public <T> ResponseEntity<String> handleMessageFailStatus(String msg, HttpStatus status) {
    return new ResponseEntity<>(msg, createJSONTypeHeaders(), status);
  }

  public ResponseEntity<byte[]> handleFileDownloadOKStatus(long contentLength, String fileName, byte[] body) {
    // FIXME This shouldn't be used, should be replaced by handleFileDownloadOKStatus(File)
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/force-download");
    headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(body, headers, HttpStatus.OK);
    return responseEntity;
  }

  public ResponseEntity<InputStreamResource> handleFileDownloadOKStatus(File file) throws FileNotFoundException {
    InputStream inputStream = new FileInputStream(file);
    InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/force-download");
    headers.add("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
    headers.setContentLength(file.length());
    return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);

  }

  public ResponseEntity<StreamingResponseBody> handleFileDownloadOKStatus(FileSystem fs, Path path, InputStreamResource inputStreamResource,
      String fileName, Runnable callback) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/force-download");
    headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    headers.setContentLength(fs.getFileStatus(path).getLen());
    return new ResponseEntity<>((outputStream) -> {
      InputStream inputStream = inputStreamResource.getInputStream();
      FileCopyUtils.copy(inputStream, outputStream);
      inputStream.close();
      fs.close();
      if (callback != null) {
        callback.run();
      }
    }, headers, HttpStatus.OK);
  }

  public ResponseEntity<?> handleFileDownloadFailStatus() {
    return new ResponseEntity<>(createHeaders(MediaType.APPLICATION_OCTET_STREAM), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public <T> ResponseEntity<T> handleJsonFailStatus(URI location) {
    ResponseEntity<T> result = new ResponseEntity<>(createJSONTypeHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    result.getHeaders().setLocation(location);
    return result;
  }

  public <T> ResponseEntity<T> handleImageOKStatus(T body) {
    return new ResponseEntity<>(body, createPNGTypeHeaders(), HttpStatus.OK);
  }

  public <T> ResponseEntity<T> handleNoHeadersOKStatus(T body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  private static HttpHeaders createJSONTypeHeaders() {
    return createHeaders(MediaType.APPLICATION_JSON);
  }

  private static HttpHeaders createPNGTypeHeaders() {
    return createHeaders(MediaType.IMAGE_PNG);
  }

  private static HttpHeaders createHeaders(MediaType mediaType) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(mediaType);
    headers.setDate(System.currentTimeMillis());
    return headers;
  }

  public <T> ResponseEntity<T> handleJsonAccessDenied(T body) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.FORBIDDEN);
  }

  public <T> ResponseEntity<T> handleUnprocessableEntity(T body) {
    return new ResponseEntity<>(body, createJSONTypeHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  public ResponseEntity<StreamingResponseBody> handleFileDownloadOKStatus(FileSystem fs, Path path, InputStreamResource inputStreamResource,
      String name) throws IOException {
    return handleFileDownloadOKStatus(fs, path, inputStreamResource, name, null);
  }
}
