package com.example.booksapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.booksapp.Book;
import com.example.booksapp.BookAdapter;
import com.example.booksapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private Button searchButton;
    private ListView bookListView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookList;
    private static final String API_KEY = "AIzaSyDt5kmLoaQqaaArhgBw-N1566p21f3Kt5k";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        bookListView = findViewById(R.id.book_list_view);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        bookListView.setAdapter(bookAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString();
                if (!query.isEmpty()) {
                    searchBooks(query);
                }
            }
        });
    }

    private void searchBooks(String query) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bookList.clear();
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                String title = volumeInfo.getString("title");
                                String author = volumeInfo.has("authors") ?
                                        volumeInfo.getJSONArray("authors").getString(0) : "Unknown";
                                String description = volumeInfo.has("description") ?
                                        volumeInfo.getString("description") : "No description available";

                                String thumbnailUrl = "";
                                if (volumeInfo.has("imageLinks")) {
                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    thumbnailUrl = imageLinks.has("thumbnail") ?
                                            imageLinks.getString("thumbnail") : "";
                                }

                                Book book = new Book(title, author, description, thumbnailUrl);
                                bookList.add(book);
                            }

                            bookAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

