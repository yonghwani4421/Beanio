package com.ask.springbeanio.helper;


import com.ask.springbeanio.config.BeanIODto;
import com.ask.springbeanio.config.BeanIOProperties;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.beanio.BeanReader;
import org.beanio.Marshaller;
import org.beanio.StreamFactory;
import org.beanio.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BeanIOHelper {

    private final StreamFactory streamFactory;
    private final BeanIOProperties beanIOProperties;

    public String marshal(BeanIODto dto, final String StreamName) {
        Marshaller marshaller = streamFactory.createMarshaller(StreamName);
        return marshaller.marshal(beanIOProperties.getRecordName(), dto).toString();
    }

    public BeanIODto unmarshal(String record) {
        Unmarshaller unmarshaller = streamFactory.createUnmarshaller(beanIOProperties.getStreamName());
        return (BeanIODto) unmarshaller.unmarshal(record);
    }

    public byte[] jsonToTextEnc(final String streamName, final BeanIODto dto) throws IOException {
        Marshaller marshaller = streamFactory.createMarshaller(streamName);

        String sendData = marshaller.marshal(dto).toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(baos,beanIOProperties.getEncoding());
        BufferedWriter bufferedWriter = new BufferedWriter(OutputStreamWriter);
        bufferedWriter.write(sendData);
        bufferedWriter.flush();
        bufferedWriter.close();

        return baos.toByteArray();
    }


    public BeanIODto textToDecJson(final String streamName, final byte[] bytes) throws UnsupportedEncodingException {

        String text = new String(bytes,beanIOProperties.getEncoding());
        Unmarshaller unmarshaller = streamFactory.createUnmarshaller(beanIOProperties.getStreamName());
        return (BeanIODto)unmarshaller.unmarshal(text);

    }

    public static String mapToJson(final Map<String, Object> hm) {
        Gson gson = new Gson();
        String json = gson.toJson(hm);
        return json;
    }

    public static HashMap<String, Object> jsonToHashmap(final String json) {
        Gson gson = new Gson();
        HashMap<String, Object> hm = gson.fromJson(json, HashMap.class);
        return hm;
    }

    public static BeanIODto parser(HashMap<String, Object> map) {

        BeanIODto dto = new BeanIODto();
        Map<String, Object> header = (Map<String, Object>) map.get("header");
        Map<String, Object> body = (Map<String, Object>) map.get("body");
        header.put("GUID", UUID.randomUUID().toString());
        dto.setHeader(header);
        dto.setBody(body);

        return dto;
    }



}
