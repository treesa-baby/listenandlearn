package com.example.treesamary.testapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.treesamary.testapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoiceNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoiceNote extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button next;
    public VoiceNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoiceNote.
     */
    // TODO: Rename and change types and number of parameters
    public static VoiceNote newInstance(String param1, String param2) {
        VoiceNote fragment = new VoiceNote();
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

        View view =  inflater.inflate(R.layout.fragment_voicenote, container, false);
        // Inflate the layout for this fragment
        next = view.findViewById(R.id.btn_next);
        next.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                    Audio audio = new Audio();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new Audio()).commit();
                   //FragmentManager().beginTransaction().replace(R.id., new Home()).commit();
//                Intent intent = new Intent(getActivity(),Splash_Activity.class);
//                startActivity(intent);
//                Toast.makeText(getActivity(), "Profile",Toast.LENGTH_LONG).show();



//                    Intent intent = new Intent(view.getContext(), Audio.class);
//                    view.getContext().startActivity(intent);
//                    getActivity().finish();
            }
        });
        return view;

    }

}
