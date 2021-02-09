package com.example.millmanagementsystem;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAllAdminMoneyRecive extends Fragment {

    RecyclerView recyclerView;
    AdapterMoneyRecive adapter;
    ArrayList<ImageInformation> arrayList;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    public FragmentAllAdminMoneyRecive() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_all_admin, container, false);
        mAuth=FirebaseAuth.getInstance();

        try {
            mFirestore = FirebaseFirestore.getInstance();
            recyclerView = view.findViewById(R.id.id_rv_frAllAdmin);
            recyclerView.setHasFixedSize(true);

            arrayList = new ArrayList<>();
            adapter = new AdapterMoneyRecive(container.getContext(), arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(),"OnCreate "+e,Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        arrayList.clear();
        final String[] messId = new String[1];
        final String[] type = new String[1];
        final String[] authEmail = new String[1];
        final String currentUserId=mAuth.getCurrentUser().getUid();
        try{
            mFirestore.collection("users").document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    messId[0] =documentSnapshot.getString("messid");
                    type[0] =documentSnapshot.getString("type");
                    authEmail[0] =documentSnapshot.getString("authEmail");
                    //Toast.makeText(getContext(),messId[0],Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            //Toast.makeText(getContext(),"Exception "+e,Toast.LENGTH_SHORT).show();
        }
        try{
            Thread.sleep(1000);
            mFirestore.collection("users")
                    .addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e!=null){
                                //Toast.makeText(getContext(),"Error "+e,Toast.LENGTH_LONG).show();
                                return;
                            }
                            StringBuffer buffer=new StringBuffer();
                            for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                                if(doc.getType()== DocumentChange.Type.ADDED){
                                    String userId=doc.getDocument().getId();
                                    ImageInformation user = doc.getDocument().toObject(ImageInformation.class);
                                    user.setUserId(userId);
                                    String messIdValue=user.getMessid();
                                    //Use for not show looger information
                                    boolean adminMatch=userId.equals(currentUserId);
                                    //show all admin
                                    boolean typeMatch=user.getType().equals("Admin");
                                    //show logger messid information
                                    boolean messIdMatch=messId[0].equals(messIdValue);
                                    if(!adminMatch&&typeMatch&&messIdMatch){
                                        arrayList.add(user);
                                        adapter.notifyDataSetChanged();
                                        buffer.append(messIdValue+"\n");
                                    }
                                    // Toast.makeText(getContext(),user.getMessid(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            //Toast.makeText(getContext(),buffer.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            //Toast.makeText(getContext(),"Exception "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
