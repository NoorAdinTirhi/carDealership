package com.example.cardealership;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopUpFragment newInstance(String param1, String param2) {
        PopUpFragment fragment = new PopUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textViewName = (TextView) getActivity().findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) getActivity().findViewById(R.id.textViewPrice);
        TextView textViewType = (TextView) getActivity().findViewById(R.id.textViewType);

        PopUpCommunicator communicator = (PopUpCommunicator)getActivity();

        Button buttonExit = (Button) getActivity().findViewById(R.id.buttonClose);

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.respondPopUpExit();
            }
        });

        textViewName.setText(String.valueOf(Car.currentCar.getId()));
        textViewPrice.setText(String.valueOf(Car.currentCar.getPrice()));
        textViewType.setText(Car.currentCar.getType());
    }

}