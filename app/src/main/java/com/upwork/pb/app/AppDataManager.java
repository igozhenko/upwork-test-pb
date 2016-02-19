package com.upwork.pb.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upwork.pb.model.User;

import java.io.IOException;

public class AppDataManager {

    private static final String KEY_USER = "keyUser";
    private final SharedPreferences mPreferences;

    AppDataManager(Context context) {
        mPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    @NonNull
    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper;
    }

    @Nullable
    public User getUser() {
        String userJson = mPreferences.getString(KEY_USER, null);
        if (null == userJson) return null;
        ObjectMapper objectMapper = createObjectMapper();
        try {
            return objectMapper.readValue(userJson, User.class);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
            return null;
        }
    }

    public void setUser(@Nullable User user) {
        if (null == user) {
            mPreferences.edit().remove(KEY_USER).apply();
            return;
        }
        ObjectMapper objectMapper = createObjectMapper();
        try {
            mPreferences.edit().putString(KEY_USER, objectMapper.writeValueAsString(user)).apply();
        } catch (JsonProcessingException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }
}
