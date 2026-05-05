package com.example.tp4moviles.ui.salir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tp4moviles.R;
import com.example.tp4moviles.databinding.FragmentExitBinding;

public class SalirFragment extends Fragment {

    private FragmentExitBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_message)
                .setPositiveButton(R.string.exit_confirm, (dialog, which) -> requireActivity().finish())
                .setNegativeButton(R.string.exit_cancel, (dialog, which) ->
                        Navigation.findNavController(requireView()).popBackStack())
                .setOnCancelListener(dialog ->
                        Navigation.findNavController(requireView()).popBackStack())
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
