package com.example.bloodbank.data.repository;

import com.example.bloodbank.data.network.IRetrofitApi;
import com.example.bloodbank.data.network.RetrofitClient;

public class GlobalRepo {
    private final IRetrofitApi iRetrofitApi = RetrofitClient.getInstance().getApi();

    public IRetrofitApi getIRetrofitApi() {
        return iRetrofitApi;
    }
}
