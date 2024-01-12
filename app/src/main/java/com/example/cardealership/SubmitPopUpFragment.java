package com.example.cardealership;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitPopUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitPopUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubmitPopUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitPopUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitPopUpFragment newInstance(String param1, String param2) {
        SubmitPopUpFragment fragment = new SubmitPopUpFragment();
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
        return inflater.inflate(R.layout.fragment_submit_pop_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        TextView textViewName = (TextView) getActivity().findViewById(R.id.textViewName1);
        TextView textViewPrice = (TextView) getActivity().findViewById(R.id.textViewPrice1);
        TextView textViewType = (TextView) getActivity().findViewById(R.id.textViewType1);

        PopUpCommunicator communicator = (PopUpCommunicator)getActivity();

        Button buttonExit = (Button) getActivity().findViewById(R.id.buttonClose1);

        Button buttonSubmit = (Button) getActivity().findViewById(R.id.buttonSubmit1);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), User.dbName, null, 1);

        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(getActivity());


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.respondSubmitPopUpExit();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.addToReserves(getActivity(), sharedPrefManager.readString("loggedInEmail", "Default"), CarMenuActivity.currentCar.getId(), new Date());
                communicator.respondSubmitPopUpExit();
                dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default"), new Date(), String.format("Removed Car %d from reserves",CarMenuActivity.currentCar.getId())));
            }
        });

        textViewName.setText(String.valueOf(CarMenuActivity.currentCar.getId()));
        textViewPrice.setText(String.valueOf(CarMenuActivity.currentCar.getPrice()));
        textViewType.setText(CarMenuActivity.currentCar.getType());
    }
}