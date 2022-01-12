package karl.vargas.whatsappclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import karl.vargas.whatsappclone.Adapter.UsersAdapter;
import karl.vargas.whatsappclone.Models.Users;
import karl.vargas.whatsappclone.R;
import karl.vargas.whatsappclone.databinding.FragmentChatsBinding;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor

    }

    // Binding
    FragmentChatsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();

        // User Adapter binding
        UsersAdapter adapter = new UsersAdapter(list, getContext());
        binding.chatsRecyclerView.setAdapter(adapter);

        // Passing the adapter to the layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatsRecyclerView.setLayoutManager(layoutManager);

        //getting ref from the first root data in firebase and displaying the users on chats fragment
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());


                    // Showing only users and not myself in chats
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(users);
                    };
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}