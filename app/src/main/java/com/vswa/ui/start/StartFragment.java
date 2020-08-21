package com.vswa.ui.start;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vswa.R;
import com.vswa.ui.main.MainActivity;

public class StartFragment extends Fragment {
    private StartPresenter startPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        startPresenter = new StartPresenter(getActivity());
        TextView nextTextView = view.findViewById(R.id.fragment_start_next_text_view);
        final EditText locationEditText = view.findViewById(R.id.fragment_welcome_edit_text);
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "You haven't entered anything", Toast.LENGTH_SHORT).show();
                } else {
                    startPresenter.saveLocationName(locationEditText.getText().toString());
                    ((MainActivity)getActivity()).openHomeFrame();
                }
            }
        });
        return view;
    }
}
