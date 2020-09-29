package com.example.demo.service.impl;

import com.example.demo.mapper.CarouselMapper;
import com.example.demo.pojo.Carousel;
import com.example.demo.service.CarouselService;
import com.example.demo.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    CarouselMapper carouselMapper;
    @Override
    public List<Carousel> queryCarouselList(Carousel carousel) {
        return carouselMapper.queryCarouselList(carousel);
    }

    @Override
    public Carousel queryCarouselOne(int carouselId) {
        return carouselMapper.queryCarouselOne(carouselId);
    }

    @Override
    public String addCarousel(Carousel carousel, HttpServletRequest request) {
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        carousel.setCreateTime(new Date());
        carousel.setCreateUser(user.getUsername());
        if(carouselMapper.addCarousel(carousel)>0){
            return "success";
        }
        return "error";
    }

    @Override
    public String editCarousel(Carousel carousel) {
        if(carouselMapper.editCarousel(carousel)>0){
            return "success";
        }
        return "error";
    }

    @Override
    public String delCarousel(int carouselId) {
        if(carouselMapper.delCarousel(carouselId)>0){
            return "success";
        }
        return "error";
    }

    @Override
    public List<Carousel> showCarouselToIndex(int size, Carousel carousel) {
        return carouselMapper.showCarouselToIndex(size,carousel);
    }
}
