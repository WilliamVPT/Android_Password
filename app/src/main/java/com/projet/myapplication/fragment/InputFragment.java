package com.projet.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projet.myapplication.databinding.InputFragmentBinding;
import com.projet.myapplication.viewmodel.InputViewModel;


public class InputFragment extends Fragment {
    private InputFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = InputFragmentBinding.inflate(inflater, container, false);
        binding.setViewModel(new InputViewModel(this));
        binding.setLifecycleOwner(this);
        // Find the TextView in the inflated view
        return binding.getRoot();
    }
}
