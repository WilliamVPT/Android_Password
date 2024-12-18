package com.projet.myapplication.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.projet.myapplication.fragment.OutputFragment;

public class OutputViewModel extends ViewModel {
    public ObservableField<String> generatedPassword = new ObservableField<>();

    private OutputFragment outputFragment;

    public OutputViewModel(OutputFragment outputFragment) {
        this.outputFragment = outputFragment;

        outputFragment.getParentFragmentManager().setFragmentResultListener("request_key", outputFragment,
                (requestKey, result) -> {
            generatedPassword.set(result.getString("key_message"));
            Log.d("", result.getString("key_message"));
        });
    }
}
