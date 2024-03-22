package com.example.catdog.care_target;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareTagetService {
    private final CareTargetRepository careTargetRepository;
}
