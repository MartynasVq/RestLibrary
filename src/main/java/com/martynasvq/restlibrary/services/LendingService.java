package com.martynasvq.restlibrary.services;

import java.util.UUID;

public interface LendingService {

    boolean canBorrowMore(String name);

    void borrow(UUID uuid, String name);
}
