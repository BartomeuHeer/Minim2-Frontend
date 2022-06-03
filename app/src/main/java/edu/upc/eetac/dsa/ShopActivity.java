package edu.upc.eetac.dsa;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.models.Item;
import edu.upc.eetac.dsa.models.LogInParams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import edu.upc.eetac.dsa.models.User;

public class ShopActivity extends AppCompatActivity {
    private List<Item> itemList;
    private RecyclerView recyclerView;
    ApiInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        recyclerView = findViewById(R.id.recyclerViewAct);
        itemList = new ArrayList<>();
        apiInterface = Api.getClient();

        setItemInfo();


    }

    private void setAdapter(List<Item> list) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    public void setItemInfo(){
        apiInterface.getItems().enqueue(new Callback<List<Item>>(){

            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    Log.d("catalogobien", response.body().get(0).getName());
                    setAdapter(response.body());
                }
                else
                {
                    Log.d("catalogomal", Integer.toString(response.code()));
                }

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();


            }
        });

    }


}
