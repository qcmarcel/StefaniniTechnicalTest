package com.stefanini.technicaltest.repositories.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Getter
public abstract class RepositoryAdapterBase<E, I, R extends CrudRepository<E, I>> {

    private final R repository;
    private final String entityName;
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final Function<E, String> keyExtractor;
    private final ObjectMapper mapper;

    protected RepositoryAdapterBase(R repository, RedisTemplate<String, String> redisTemplate, final Function<E, String> keyExtractor, final Class<E> entityClass, ObjectMapper mapper) {
        this.repository = repository;
        this.entityName = entityClass.getSimpleName();
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
        this.keyExtractor = keyExtractor;
        this.mapper = mapper;
    }

    public Optional<E> find(I id) {
        return this.findCacheKey(String.valueOf(id).toLowerCase()).or(() -> searchAndPutInCache(id));
    }

    public Optional<E> save(E entity) {
        return Optional.of(repository.save(entity)).map(this.peek(this::saveCache));
    }

    public List<E> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    public List<E> findAll(final int limit) {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<E> findAll(final Comparator<E> sort) {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .sorted(sort)
                .collect(Collectors.toList());
    }

    public List<E> findAll(final Comparator<E> sort, final int limit) {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .sorted(sort)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<E> searchWithOutCache(I id) {
        return repository.findById(id);
    }

    protected Optional<E> searchAndPutInCache(I id) {
        return repository.findById(id).map(this.peek(this::saveCache));
    }

    public void deleteCache(final String key) {
        hashOperations.delete(entityName, key.toLowerCase());
    }

    public void saveCache(final E entity) {
        hashOperations.put(entityName, keyExtractor.apply(entity).toLowerCase(), Objects.requireNonNull(jsonValue(entity)));
        expireKey();
    }

    public Optional<E> findCache(I id) {
        return find(id).flatMap(e -> findCacheKey(keyExtractor.apply(e)));
    }

    public Optional<E> findCacheKey(final String key) {
        return Optional.ofNullable(hashOperations.get(entityName, key.toLowerCase()))
                .map(this::entityValue);
    }

    public Integer generateId() {
        return Math.toIntExact(repository.count());
    }

    protected <T> UnaryOperator<T> peek(Consumer<T> c) {
        return x -> {
            c.accept(x);
            return x;
        };
    }

    public void delete(final E entity) {
        repository.delete(entity);
        deleteCache(keyExtractor.apply(entity));
    }

    protected void saveCache(String key, E entity) {
        hashOperations.put(entityName, key, Objects.requireNonNull(jsonValue(entity)));
        expireKey();
    }

    public abstract TypeReference<E> getTypeReference();

    private String jsonValue(final E entity) {
        try {
            return this.mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    private E entityValue(final String json) {
        try {
            return this.mapper.readValue(json, getTypeReference());
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    private void expireKey() {

        Long expire = this.redisTemplate.getExpire(entityName);
        if (!(expire == null || expire >= 0)) {
            this.redisTemplate.expire(entityName, Duration.ofHours(5L));
        }

    }
}
