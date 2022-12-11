package com.example.boufnichel_yassine_dictionnary;

import com.example.boufnichel_yassine_dictionnary.Models.APIResponse;

public interface onFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
