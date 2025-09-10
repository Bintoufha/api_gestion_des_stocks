package com.bintoufha.gestionStocks.services;

import java.io.InputStream;

public interface UnsplashImageService  {

    String savePhoto(InputStream photo, String titre);
}
