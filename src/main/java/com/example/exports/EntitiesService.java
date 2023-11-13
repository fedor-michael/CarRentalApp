package com.example.exports;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntitiesService {

    private final Set<Class<?>> currentEntities;

    public Class<?> getEntity(String entityName) {
        return currentEntities.stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getSimpleName().equalsIgnoreCase(entityName))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<String> extractTableName(Class<?> entityClass) {
        Annotation annotation = entityClass.getAnnotation(Table.class);
        if (annotation instanceof Table) {
            Table tableAnnotation = (Table) annotation;
            return Optional.ofNullable(tableAnnotation.name());
        }
        return Optional.empty();
    }

}
