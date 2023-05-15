package com.example.demo.service;

public class LogMessage {
  private final String remoteAddress;
  private final String header;
  private final String key;
  private final String value;

  public LogMessage(String remoteAddress, String header, String key, String value) {
    this.remoteAddress = remoteAddress;
    this.header = header;
    this.key = key;
    this.value = value;
  }

  @Override
  public String toString() {
    return remoteAddress + "::"+header+"::{" + key + "=" + value + "}";
  }
}
