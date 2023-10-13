package com.ask.springbeanio.service;

import com.ask.springbeanio.config.BeanIODto;
import com.ask.springbeanio.config.BeanIOProperties;
import com.ask.springbeanio.helper.BeanIOHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class BeanIOService {

    @Autowired
    private BeanIOHelper beanIOHelper;


    public byte[] getEncToTextData(HashMap<String, Object> map, String streamName) throws IOException {
        HashMap<String, byte[]> hm = new HashMap<>();
        byte[] bytes = beanIOHelper.jsonToTextEnc(streamName, beanIOHelper.parser(map));
        log.info("## length : {}", bytes.length);
        log.info("## bytes : {}", bytes);

        log.info("## dec : {}",new String(bytes,"euc-kr"));

        FileOutputStream fos = new FileOutputStream("sample.out");
        fos.write(bytes);

        return bytes;
    }

    public HashMap getDecToMapData(byte[] bytes, String streamName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(beanIOHelper.textToDecJson(streamName, bytes),HashMap.class);
    }

}
