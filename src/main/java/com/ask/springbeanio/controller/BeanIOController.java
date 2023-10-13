package com.ask.springbeanio.controller;

import com.ask.springbeanio.service.BeanIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/beanio")
public class BeanIOController {
    @Autowired
    private BeanIOService beanIOService;

    @PostMapping("/encoding")
    public byte[] encoding(@RequestBody HashMap<String, Object> map, @RequestHeader("streamName") String streamName) throws IOException {
        return beanIOService.getEncToTextData(map,streamName);
    }

    @PostMapping("/decoding")
    public Object decoding(@RequestBody byte[] bytes, @RequestHeader("streamName") String streamName) throws IOException {
        return beanIOService.getDecToMapData(bytes, streamName);
    }
}
