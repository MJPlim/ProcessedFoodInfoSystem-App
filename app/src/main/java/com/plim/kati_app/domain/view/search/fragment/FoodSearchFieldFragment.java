package com.plim.kati_app.domain.view.search.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.api.ApiService;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.FoodSearchListItem;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.FoodSearchActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import kotlin.Pair;
import okhttp3.Headers;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * 음식 검색하는 검색필드가 있는 프래그먼트
 * !이슈: 검색할 때 입력중이면 카메라 버튼 대신 "x"버튼을 보여주고, x버튼을 누르면 입력한 데이터를 날릴 수 있으면 좋겠다.
 */
public class FoodSearchFieldFragment extends Fragment {

    //static attribute
    private static final String IP = "http://13.124.55.59:8080/api/v1/food/findFood/";

    private static final String HEADER_NAME = "Authorization";
    private static final int CONNECTION_RESPOND_SUCCESS = 200;

    //working variable
    private int index = 1;

    //associate
    //view
    private EditText searchEditText;
    private ImageView cameraSearchButton, textSearchButton;

    //component
    private Vector<FoodSearchListItem> items;
    private Map<String, String> parameterMap;
    private RecyclerAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_food_search_field, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //associate view
        this.searchEditText=view.findViewById(R.id.foodSearchFieldFragment_searchEditText);
        this.cameraSearchButton =view.findViewById(R.id.foodSearchFieldFragment_cameraSearchButton);
        this.textSearchButton =view.findViewById(R.id.foodSearchFieldFragment_textSearchButton);

        //set view


        //create component
        this.items = new Vector<>();
        this.parameterMap = new HashMap<>();
        this.listAdapter = new RecyclerAdapter();


    }


    private class ProductSearch implements Runnable{
        @Override
        public void run() {
            items.clear();
            parameterMap.clear();

            KatiDatabase database = KatiDatabase.getAppDatabase(FoodSearchActivity.this);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://13.124.55.59:8080/")
                    .build();
            ApiService service = retrofit.create(ApiService.class);
            Call<List<FoodSearchListItem>> items = service.getFoodListByProductName(
                    database.katiDataDao().getValue(HEADER_NAME),
                    searchEditText.getText().toString().replaceAll("[ ]", "_"),
                    1+"");

            Call<List<FoodSearchListItem>> it = service.getFoodListByProductName(database.katiDataDao().getValue("Authorization"),searchEditText.getText().toString(),index+"");
            it.enqueue(new Callback<List<FoodSearchListItem>>() {
                @Override
                public void onResponse(Call<List<FoodSearchListItem>> call, Response<List<FoodSearchListItem>> response) {
                    List<FoodSearchListItem> list= response.body();
                    Vector<FoodSearchListItem> ve = new Vector<>(list);
                    Headers headers =response.headers();
                    for(Pair<? extends String, ? extends String> pair:headers){
                        if(pair.getFirst().equals("Authorization")){
                            database.katiDataDao().insert(new KatiData("Authorization",pair.getSecond()));
                            break;
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<FoodSearchListItem>> call, Throwable t) {
                    call.hashCode()
                    throw t;
                }

            });



        }
    }

    private class CompanySearch implements Runnable{
        @Override
        public void run() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://13.124.55.59:8080/")
                    .build();
            ApiService service = retrofit.create(ApiService.class);
            List<FoodSearchListItem> items = service.getFoodListByCompanyName("",searchEditText.getText().toString().replaceAll("[ ]", "_"));

        }
    }


    /**
     * 어댑터 클래스.
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        private Vector<FoodSearchListItem> items;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_food, parent, false);
            RecyclerViewViewHolder rankRecyclerViewViewHolder = new RecyclerViewViewHolder(view);

            return rankRecyclerViewViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            FoodSearchListItem item = items.get(position);
            ((RecyclerViewViewHolder) holder).setValue(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void clearItems() {
            this.items = new Vector<>();
        }

        public void addItems(Vector<FoodSearchListItem> items) {
            this.items.addAll(items);
        }

        public void setItems(Vector<FoodSearchListItem> items) {
            this.clearItems(); this.addItems(items);
        }

        /**
         * 뷰 홀더.
         */
        private class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private TextView productName, companyName;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.foodItem_productName);
                this.companyName = itemView.findViewById(R.id.foodItem_companyName);
            }

            public void setValue(FoodSearchListItem item) {
                this.productName.setText(item.getPrdlstName());
                this.companyName.setText(item.getBsshName());
            }
        }
    }

}