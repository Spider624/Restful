//package edu.school21.restfull.serialization;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import org.springframework.boot.jackson.JsonComponent;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//
//import java.io.IOException;
//import java.time.LocalTime;
//
//@JsonComponent
//public class LocalTimeDeserializer extends com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer {
//
//    @Override
//    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, HttpMessageNotReadableException {
//        String time = jsonParser.getText();
//        if (time == null || time.isEmpty()){
//            return null;
//        }
//        return LocalTime.parse(time);
//    }
//}
