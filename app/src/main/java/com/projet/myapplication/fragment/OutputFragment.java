package com.projet.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.projet.myapplication.viewmodel.OutputViewModel;
import com.projet.myapplication.databinding.OutputFragmentBinding;

public class OutputFragment extends Fragment {
    private TextView textView;
    private OutputFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = OutputFragmentBinding.inflate(inflater, container, false);
        binding.setViewModel(new OutputViewModel(this));
        binding.setLifecycleOwner(this);
        // Find the TextView in the inflated view
        View view = binding.getRoot();

        return view;
    }
}
