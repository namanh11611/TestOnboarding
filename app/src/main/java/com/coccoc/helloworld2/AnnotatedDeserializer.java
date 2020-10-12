package com.coccoc.helloworld2;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AnnotatedDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        T pojo = new Gson().fromJson(json, typeOfT);
        Log.d("namanh11611", "json element " + json);
        Log.d("namanh11611", "pojo " + pojo.getClass().getSimpleName());

        checkRequiredFields(pojo.getClass().getDeclaredFields(), pojo);

        checkSuperClasses(pojo);

//        Field[] fields = pojo.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            if (field.getAnnotation(FieldRequired.class) != null) {
//                try {
//                    field.setAccessible(true);
//                    if (field.get(pojo) == null) {
//                        throw new JsonParseException("Missing field in JSON: " + field.getName());
//                    }
//                } catch (IllegalAccessException e) {
//                    Logger.getLogger(AnnotatedDeserializer.class.getName()).log(Level.SEVERE, null, e);
//                } catch (IllegalArgumentException e) {
//                    Logger.getLogger(AnnotatedDeserializer.class.getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }

        return pojo;
    }

    private void checkRequiredFields(@NonNull Field[] fields, @NonNull Object pojo)
            throws JsonParseException {
        Log.d("namanh11611", "Begin    check " + pojo.getClass().getName());
        // Checking nested list items too.
        if (pojo instanceof List) {
            final List pojoList = (List) pojo;
            for (final Object pojoListPojo : pojoList) {
                Log.d("namanh11611", "Item " + pojoListPojo.getClass().getName());
                checkRequiredFields(pojoListPojo.getClass().getDeclaredFields(), pojoListPojo);
                checkSuperClasses(pojoListPojo);
            }
        }

        Log.d("namanh11611", "Continue check " + pojo.getClass().getName() + " - " + fields.length);
        for (Field field : fields) {
            // If some field has required annotation.
            if (field.getAnnotation(FieldRequired.class) != null) {
                try {
                    // Trying to read this field's value and check that it truly has value.
                    field.setAccessible(true);
                    Object fieldObject = field.get(pojo);
                    Log.d("namanh11611", "Check field  " + fieldObject.toString());
                    if (fieldObject == null) {
                        // Required value is null - throwing error.
                        throw new JsonParseException(String.format("Missing field in JSON: %1$s -> %2$s",
                                pojo.getClass().getSimpleName(),
                                field.getName()));
                    } else {
                        checkRequiredFields(fieldObject.getClass().getDeclaredFields(), fieldObject);
                        checkSuperClasses(fieldObject);
                    }
                }

                // Exceptions while reflection.
                catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new JsonParseException(e);
                }
            }
        }
    }

    /**
     * Checks whether all super classes have all required fields.
     *
     * @param pojo Object to check required fields in its superclasses.
     *
     * @throws JsonParseException When some required field was not met.
     * */
    private void checkSuperClasses(@NonNull Object pojo) throws JsonParseException {
        Class<?> superclass = pojo.getClass();
        if (!superclass.getName().contains("coccoc")) return;
//        Log.d("namanh11611", "checkSuperClasses       " + superclass.getName());
        while ((superclass = superclass.getSuperclass()) != null) {
            if (!superclass.getName().contains("coccoc")) break;
//            Log.d("namanh11611", "checkSuperClasses while " + superclass.getName());
            checkRequiredFields(superclass.getDeclaredFields(), pojo);
        }
    }
}
