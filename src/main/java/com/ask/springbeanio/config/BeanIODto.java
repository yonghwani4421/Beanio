package com.ask.springbeanio.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class BeanIODto {

  private Map<String, Object> header;
  private Map<String, Object> body;
  private String end;

}
