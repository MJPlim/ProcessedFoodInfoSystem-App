package com.plim.kati_app.domain.food.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.food.search.dto.FoodSearchListItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

public class FoodSearchActivity extends AppCompatActivity {

    //static attribute
    private static final String IP = "http://13.124.55.59:8080/api/v1/food/findFood/";
    private static final int Connection_Respond_Success = 200;
    private static final String PARAMETER_NAME_FOOD_Name = "foodName";
    private static final String PARAMETER_NAME_PAGE_NUMBER = "pageNo";

    private static final String JSON_NAME_PRODUCT_LICENSE_NUMBER = "lcnsNo";
    private static final String JSON_Name_Product_Company_Name = "bsshName";
    private static final String JSON_NAME_PRODUCT_REPORT_NUMBER = "prdlstReportNo";

    private static final String JSON_Name_Product_CONFIRM_DATE = "prmsDate";
    private static final String JSON_Name_Product_Name = "prdlstName";
    private static final String JSON_Name_Product_Category_Name = "prdlstDCName";

    private static final String JSON_Name_Product_RawMaterial_Name = "rawMaterialName";

    //working variable
    private int index = 1;

    //associate
    //view
    private EditText searchEditText;
    private Spinner searchModeSpinner;
    private ImageButton textSearchButton, cameraSearchButton;
    private RecyclerView searchResultListRecyclerView;


    private Vector<FoodSearchListItem> items;
    private Map<String, String> parameterMap;
    private RecyclerAdapter listAdapter;
    private LoadingDialog loadingDialog;


    @Getter
    public enum ESearchMode {
        제품("foodName"), 회사("bsshName");
        private String mappingName;

        ESearchMode(String string) {
            this.mappingName = string;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        //create component

        this.items = new Vector<>();
        this.parameterMap = new HashMap<>();

        this.searchResultListRecyclerView = findViewById(R.id.searchFragment_foodInfoRecyclerView);
        this.searchEditText = findViewById(R.id.foodSearchFieldFragment_searchEditText);
        this.searchModeSpinner = findViewById(R.id.foodSearchFieldFragment_searchModeSpinner);
        this.textSearchButton = findViewById(R.id.foodSearchFieldFragment_textSearchButton);
        this.cameraSearchButton = findViewById(R.id.foodSearchFieldFragment_cameraSearchButton);

        this.loadingDialog = new LoadingDialog(this);
        this.listAdapter = new RecyclerAdapter();
        this.textSearchButton.setOnClickListener(v -> {
            try {
                search();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }


    public void search() throws NoSuchFieldException {
        if (this.searchEditText.length() > 0) {

            SearchThread thread;
            this.items.clear();
            this.parameterMap.clear();


            if (searchModeSpinner.getSelectedItem().toString().equals(ESearchMode.제품.toString())) {
                thread = new SearchThread(ESearchMode.제품);
                this.parameterMap.put(ESearchMode.제품.getMappingName(), searchEditText.getText().toString().replaceAll("[ ]", "_"));

            } else if (searchModeSpinner.getSelectedItem().toString().equals(ESearchMode.회사.toString())) {
                thread = new SearchThread(ESearchMode.회사);
                this.parameterMap.put(ESearchMode.회사.getMappingName(), searchEditText.getText().toString().replaceAll("[ ]", "_"));

            } else {
                throw new NoSuchFieldException();
            }


            this.parameterMap.put(PARAMETER_NAME_PAGE_NUMBER, index + "");


            thread.start();

            NavHostFragment navHostFragment = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_search_fragment);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.action_foodSearchRecommendationFragment_to_foodSearchResultListFragment);
        }
    }

    /**
     * Inner class: 서버에서 목록을 받아올 스레드.
     */
    private class SearchThread extends Thread {

        private ESearchMode searchMode;

        public SearchThread(ESearchMode searchMode) {
            this.searchMode = searchMode;
        }

        @Override
        public void run() {
            super.run();
            runOnUiThread(() -> {
                loadingDialog.show();
            });
            StringBuilder parameter = new StringBuilder();
            try {
                for (Map.Entry<String, String> param : parameterMap.entrySet()) {
                    if (parameter.length() != 0) parameter.append('&');
                    else parameter.append(searchMode.getMappingName() + '?');
                    parameter.append(param.getKey());
                    parameter.append("=");
                    parameter.append(param.getValue());
                }
                Log.d("파라미터", parameter.toString());
                URL url = new URL(IP + parameter.toString());
                Log.d("디버그", url.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() != Connection_Respond_Success) {
                    runOnUiThread(() -> {
                        loadingDialog.hide();
                    });
                    throw new Exception("연결 실패 exception::" + connection.getResponseCode());
                } else { //연결 성공
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
                    String jsonString = streamOfString.collect(Collectors.joining());

                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoodSearchListItem item = new FoodSearchListItem();
                        JSONObject itemInfo = jsonArray.getJSONObject(i);
                        item.setProductLicenseNumber(itemInfo.getLong(JSON_NAME_PRODUCT_LICENSE_NUMBER));
                        item.setCompanyName(itemInfo.getString(JSON_Name_Product_Company_Name));
                        item.setProductReportNumber(itemInfo.getLong(JSON_NAME_PRODUCT_REPORT_NUMBER));
                        item.setProductConfirmDate(itemInfo.getLong(JSON_Name_Product_CONFIRM_DATE));
                        item.setProductName(itemInfo.getString(JSON_Name_Product_Name));
                        item.setProductCategoryName(itemInfo.getString(JSON_Name_Product_Category_Name));
                        item.setRawMaterialName(itemInfo.getString(JSON_Name_Product_RawMaterial_Name));
                        items.add(item);
                    }
                    Log.d("디버그", "파싱 완료");

                    runOnUiThread(() -> {

                        listAdapter.clearItems();
                        listAdapter.addItems(items);
                        searchResultListRecyclerView.setAdapter(listAdapter);
//                        moreButton.setVisibility(View.VISIBLE);
                        loadingDialog.hide();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

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
            this.productName.setText(item.getProductName());
            this.companyName.setText(item.getCompanyName());
        }
    }

}