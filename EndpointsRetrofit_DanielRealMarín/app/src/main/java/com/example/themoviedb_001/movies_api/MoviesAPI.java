package com.example.themoviedb_001.movies_api;

import com.example.themoviedb_001.json_mapper.MovieResponse;
import com.example.themoviedb_001.json_mapper.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {
    // Obtener películas populares
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    // Buscar películas
    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("page") int page);

    // Obtener detalles de una película
    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language);
}
