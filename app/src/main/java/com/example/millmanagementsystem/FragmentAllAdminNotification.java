package com.example.millmanagementsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAllAdminNotification extends Fragment {


    public FragmentAllAdminNotification() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    AdapterAllNotification adapter;
    ArrayList<MessageInformation> arrayList;
    FirebaseFirestore mFirestore;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_alladmin_notification, container, false);
        mFirestore=FirebaseFirestore.getInstance();
        try{
            recyclerView=view.findViewById(R.id.id_rv_frAllAdmin_notification);
            recyclerView.setHasFixedSize(true);
            arrayList=new ArrayList<>();
            adapter=new AdapterAllNotification(arrayList,container.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(),"oncreateView "+e,Toast.LENGTH_SHORT).show();
        }
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        arrayList.clear();
        String currentUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        try{
            mFirestore.collection("users").document(currentUserId).collection("Notifications")
                    .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e!=null){return;}
                            for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                                MessageInformation messageInformation=doc.getDocument().toObject(MessageInformation.class);
                                if(messageInformation.getType().equals("Admin")){
                                    //Toast.makeText(getContext(),messageInformation.getSendingDate(),Toast.LENGTH_LONG).show();
                                    arrayList.add(messageInformation);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
        }catch (Exception e){

        }


    }
}
