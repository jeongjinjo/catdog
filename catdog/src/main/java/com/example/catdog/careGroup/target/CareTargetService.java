package com.example.catdog.careGroup.target;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CareTargetService {
    private final CareTargetRepository careTargetRepository;
}
