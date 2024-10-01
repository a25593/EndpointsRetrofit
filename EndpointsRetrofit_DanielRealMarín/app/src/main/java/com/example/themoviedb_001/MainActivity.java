package com.example.themoviedb_001;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.themoviedb_001.json_mapper.Movie;
import com.example.themoviedb_001.json_mapper.MovieResponse;
import com.example.themoviedb_001.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "2525d7ec5cac954611609f6903e83605";  // Tu clave de API
    private static final String LANGUAGE = "es-ES";  // Idioma de la respuesta
    private static final int PAGE = 1;  // Página de resultados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnObtenerPeliculasPopulares = findViewById(R.id.obtenerPeliculasPopulares);
        Button btnBuscarPeliculas = findViewById(R.id.buscarPeliculas);
        Button btnDetallesPelicula = findViewById(R.id.detallesPelicula);
        EditText editTextBuscarPelicula = findViewById(R.id.editTextBuscarPelicula);
        EditText editTextDetallesPelicula = findViewById(R.id.editTextDetallesPelicula);

        // Obtener películas populares
        btnObtenerPeliculasPopulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopularMovies();
            }
        });

        // Buscar películas
        btnBuscarPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextBuscarPelicula.getText().toString();
                if (!query.isEmpty()) {
                    searchMovies(query);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa el nombre de la película", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Obtener detalles de una película
        btnDetallesPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieIdStr = editTextDetallesPelicula.getText().toString();
                if (!movieIdStr.isEmpty()) {
                    int movieId = Integer.parseInt(movieIdStr);
                    getMovieDetails(movieId);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa el ID de la película", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Obtener películas populares
    private void getPopularMovies() {
        Call<MovieResponse> call = RetrofitClient.getInstance().getPopularMovies(API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    for (Movie movie : movies) {
                        Toast.makeText(MainActivity.this, "Película: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Buscar películas
    private void searchMovies(String query) {
        Call<MovieResponse> call = RetrofitClient.getInstance().searchMovies(API_KEY, LANGUAGE, query, PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    for (Movie movie : movies) {
                        Toast.makeText(MainActivity.this, "Película encontrada: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Obtener detalles de una película
    private void getMovieDetails(int movieId) {
        Call<Movie> call = RetrofitClient.getInstance().getMovieDetails(movieId, API_KEY, LANGUAGE);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    Toast.makeText(MainActivity.this, "Detalles de " + movie.getTitle() + ": " + movie.getOverview(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "No se encontraron detalles para esta película", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
