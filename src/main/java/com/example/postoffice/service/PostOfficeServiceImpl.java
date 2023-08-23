package com.example.postoffice.service;

import com.example.postoffice.PostOfficeRepository;
import com.example.postoffice.entity.PostOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostOfficeServiceImpl implements PostOfficeSerivce {
    private final PostOfficeRepository postOfficeRepository;

    @Override
    public PostOffice createPostOffice(PostOffice postOffice) {
        log.info("Create Post Office {}", postOffice);
        return postOfficeRepository.save(postOffice);
    }
}
