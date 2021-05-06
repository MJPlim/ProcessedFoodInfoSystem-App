package com.plim.kati_app.domain.view.user.dataChange;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.domain.model.dto.UserInfoResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.list.list.FoodSearchResultListFragment;
import com.plim.kati_app.tech.RestAPI;
import com.plim.kati_app.tech.RestAPIClient;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserDataChangeFragment extends Fragment {

    private ImageView addImageButton;
    private RecyclerView userRecyclerView;
    private Button finalEditButton;

    public UserDataChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_data_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.addImageButton = view.findViewById(R.id.userDataChangeFragment_addImageButton);
        this.userRecyclerView = view.findViewById(R.id.userDataChangeFragment_userRecyclerView);
        this.finalEditButton = view.findViewById(R.id.userDataChangeFragment_finalEditButton);
        this.getUserData(database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION));
        this.finalEditButton.setOnClickListener(v -> this.modifyUserData());
    }

    @Override
    public void onResume() {
        super.onResume();

        //저장되어있는 사용자 토큰 존재 여부로 로그인 여부를 확인
        new Thread(()->{
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            //존재하면 사용자 토큰을 버린다.
            if(database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION)!=null) {
                this.getUserData(database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION));

            }else{

            }
        }).start();
    }

    private void getUserData(String header) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<UserInfoResponse> listCall= RestAPIClient.getApiService2(header).getUserInfo();
        listCall.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if(!response.isSuccessful()){}else{

                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });


    }

    private void modifyUserData() {

    }

    /**
     * 어댑터 클래스.
     */
    private class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserRecyclerViewViewHolder> {


        private Map<Integer, Data> valueMap;

        private UserRecyclerAdapter() {
            this.valueMap = new HashMap<>();
        }

        @NonNull
        @Override
        public UserRecyclerAdapter.UserRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_user_data_form, parent, false);
            UserRecyclerAdapter.UserRecyclerViewViewHolder rankRecyclerViewViewHolder = new FoodSearchResultListFragment.AdRecyclerAdapter.AdRecyclerViewViewHolder(view);

            return rankRecyclerViewViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserRecyclerAdapter.UserRecyclerViewViewHolder holder, int position) {
            holder.setValue(valueMap.get(position));
        }

        @Override
        public int getItemCount() {
            return valueMap.size();
        }

        public void setItems(Map<Integer, Data> valueMap) {
            this.valueMap = valueMap;
        }

        /**
         * 뷰 홀더.
         */
        private class UserRecyclerViewViewHolder extends RecyclerView.ViewHolder {
            private TextView title, value;

            public UserRecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.title=itemView.findViewById(R.id.userDataFormItem_title);
                this.value=itemView.findViewById(R.id.userDataFormItem_value);
            }


            public void setValue(@NotNull Data data) {
                this.title.setText(data.getTitle());
                this.value.setText(data.getValue());
            }
        }
    }
    /////////////////
}