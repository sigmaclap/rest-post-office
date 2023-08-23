package com.example.postoffice.service;

import com.example.postoffice.PostOfficeRepository;
import com.example.postoffice.entity.PostOffice;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostOfficeServiceImplTest {

    @InjectMocks
    PostOfficeServiceImpl postOfficeService;
    @Mock
    PostOfficeRepository postOfficeRepository;
    private final EasyRandom generator = new EasyRandom();

    @Test
    void createPostOffice_whenValidData_thenReturnedSuccess() {
        PostOffice postOffice = generator.nextObject(PostOffice.class);
        when(postOfficeRepository.save(postOffice)).thenReturn(postOffice);

        PostOffice actual = postOfficeService.createPostOffice(postOffice);

        assertEquals(postOffice, actual);
        verify(postOfficeRepository, times(1)).save(postOffice);
    }
}