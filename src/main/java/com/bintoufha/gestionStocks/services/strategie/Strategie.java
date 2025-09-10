package com.bintoufha.gestionStocks.services.strategie;

import java.io.InputStream;
import java.util.UUID;

public interface Strategie<T> {
    T savePhoto(UUID uuid, InputStream photo, String titre);
}
