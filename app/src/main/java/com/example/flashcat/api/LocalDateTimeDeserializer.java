package com.example.flashcat.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.Locale;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Định nghĩa định dạng ngày giờ phù hợp với chuỗi ngày giờ từ API
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        // Phân tích cú pháp chuỗi ngày giờ thành đối tượng LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(json.getAsString(), formatter);
        // Chuyển đổi đối tượng LocalDateTime sang định dạng mong muốn
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDate = dateTime.format(targetFormatter);
        // Phân tích cú pháp chuỗi ngày giờ đã định dạng lại thành đối tượng LocalDateTime mới
        return LocalDateTime.parse(formattedDate, targetFormatter);
    }
}

