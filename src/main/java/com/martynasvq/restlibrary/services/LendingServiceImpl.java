package com.martynasvq.restlibrary.services;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@Service
public class LendingServiceImpl implements LendingService {

    private static Map<String, List<UUID>> borrowers = new HashMap<>();

    @Override
    public boolean canBorrowMore(String name) {
        if(borrowers.get(name) == null)
            return true;
        else if(borrowers.get(name).size() < 3)
            return true;
        else
            return false;
    }

    @Override
    public void borrow(UUID uuid, String name) {
        if(borrowers.get(name) == null) {
            borrowers.put(name, new ArrayList<>(List.of(uuid)));
        } else {
            borrowers.get(name).add(uuid);
        }
    }
}
