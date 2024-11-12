package com.estudos.desafios.criptografia.service;

import com.estudos.desafios.criptografia.controller.dto.CreateTransactionRequest;
import com.estudos.desafios.criptografia.controller.dto.TransactionResponse;
import com.estudos.desafios.criptografia.controller.dto.UpdateTransactionRequest;
import com.estudos.desafios.criptografia.entity.TransactionEntity;
import com.estudos.desafios.criptografia.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        CreateTransactionRequest request = new CreateTransactionRequest("token123", "doc456", (long) 1000.0);
        TransactionEntity entity = new TransactionEntity();

        service.create(request);

        verify(repository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    public void testFindById_Success() {
        Long id = 1L;
        TransactionEntity entity = new TransactionEntity();
        entity.setTransationId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        TransactionResponse response = service.findById(id);

        assertNotNull(response);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testFindById_NotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findById(id));
    }

    @Test
    public void testListAll() {
        int page = 0;
        int size = 10;

        TransactionEntity entity = new TransactionEntity();
        Page<TransactionEntity> pageResult = new PageImpl<>(List.of(entity));

        when(repository.findAll(any(PageRequest.class))).thenReturn(pageResult);

        Page<TransactionResponse> result = service.listAll(page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testUpdate_Success() {
        Long id = 1L;
        UpdateTransactionRequest request = new UpdateTransactionRequest((long) 2000.0);
        TransactionEntity entity = new TransactionEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.update(id, request);

        verify(repository, times(1)).save(entity);
    }

    @Test
    public void testUpdate_NotFound() {
        Long id = 1L;
        UpdateTransactionRequest request = new UpdateTransactionRequest((long) 2000.0);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.update(id, request));
    }

    @Test
    public void testDeleteById_Success() {
        Long id = 1L;
        TransactionEntity entity = new TransactionEntity();
        entity.setTransationId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteById_NotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.deleteById(id));
    }
}