package com.example.demo.service;

import com.example.demo.pojo.Carousel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CarouselService {
    List<Carousel> queryCarouselList(Carousel carousel);
    Carousel queryCarouselOne(int carouselId);
    String addCarousel(Carousel carousel, HttpServletRequest request);
    String  editCarousel(Carousel carousel);
    String delCarousel(int carouselId);

    List<Carousel> showCarouselToIndex(int size,Carousel carousel);
}
