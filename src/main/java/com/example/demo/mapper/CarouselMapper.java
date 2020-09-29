package com.example.demo.mapper;

import com.example.demo.pojo.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarouselMapper {
    List<Carousel> queryCarouselList(@Param("carousel") Carousel carousel);
    int addCarousel(Carousel carousel);
    int  editCarousel(Carousel carousel);
    int delCarousel(int carouselId);

    Carousel queryCarouselOne(int carouselId);

    /*真正展示在前台上，可以设置轮播图的数量*/
    List<Carousel> showCarouselToIndex(@Param("size") int size,@Param("carousel") Carousel carousel);
}
