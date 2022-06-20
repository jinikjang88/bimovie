package com.study.bimovie.repository;

import com.study.bimovie.entity.Movie;
import com.study.bimovie.entity.Poster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void testInsert() {
        Movie movie = Movie.builder().title("극한직업").build();

        movie.addPoster(Poster.builder().fname("극한직업포스터1").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터2").build());

        movieRepository.save(movie);

        System.out.println(movie.getMno());
    }

    @Test
    @Transactional
    @Commit
    void testAddPoster() {

        Movie movie = movieRepository.findById(1L).get();

        movie.addPoster(Poster.builder().fname("극한직업3").build());
        movie.addPoster(Poster.builder().fname("극한직업4").build());
        movie.addPoster(Poster.builder().fname("극한직업5").build());
        movieRepository.save(movie);
    }

    @Test
    @Transactional
    @Commit
    void testRemovePoster() {
        Movie movie = movieRepository.findById(1L).get();

        movie.removePoster(2L);

        movieRepository.save(movie);
    }

    @Test
    void insertMovies() {
        IntStream.rangeClosed(10, 100).forEach(i -> {
            Movie movie = Movie.builder().title("세계명작 " + i).build();

            movie.addPoster(Poster.builder().fname("세계명작 " + i + "포스터1").build());
            movie.addPoster(Poster.builder().fname("세계명작 " + i + "포스터2").build());

            movieRepository.save(movie);
        });
    }

    @Test
    void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Movie> result = movieRepository.findAll(pageable);

        result.getContent().forEach(m -> {
            System.out.println("m = " + m);
            System.out.println(m.getMno());
            System.out.println(m.getTitle());
            System.out.println(m.getPosterList().size());
        });

    }

    @Test
    void testPage2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        // query에 limit가 없다.
        Page<Movie> result = movieRepository.findAll2(pageable);

        result.getContent().forEach(m -> {
            System.out.println("m = " + m);
            System.out.println(m.getMno());
            System.out.println(m.getTitle());
            System.out.println(m.getPosterList());
        });
    }

    @Test
    void testPage3() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.findAll3(pageable);

        result.getContent().forEach(arr -> System.out.println("arr = " + Arrays.toString(arr)));
    }

}
