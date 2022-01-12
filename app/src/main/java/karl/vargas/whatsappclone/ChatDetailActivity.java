package karl.vargas.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import karl.vargas.whatsappclone.Adapter.ChatAdapter;
import karl.vargas.whatsappclone.Models.MessageModel;
import karl.vargas.whatsappclone.databinding.ActivityChatDetailBinding;

public class ChatDetailActivity extends AppCompatActivity {
    //Binding
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        // Binding
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hiding Action bar in the chat
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // From UsersAdapter
        final String senderId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        // Displaying the original username
        binding.userName.setText(userName);

        //Displaying the profile pic of user. if empty - show user pic
        Picasso.get().load(profilePic).placeholder(R.drawable.userpic).into(binding.profileImage);

        // Activating the back arrow
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        Error list
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
//        Chat Adapter
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, receiveId);

        //Binding
        binding.chatsRecyclerView.setAdapter(chatAdapter);

        // Linear manager object
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Binding with recycler view
        binding.chatsRecyclerView.setLayoutManager(layoutManager);

        // Sender room and receiver room
        // Identifying who is the sender and receiver
        // Creating a unique ID for sender and receiver from db
        final String senderRoom = senderId + receiveId;
        final String receiverRoom = receiveId + senderId;


        // Displaying messages on chat screen
        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //After send - txtfield is clear
                        messageModels.clear();

                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }

                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Click button to send

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Storing what is in the txtfield to var
                String message = binding.enterMessage.getText().toString();

                final MessageModel model = new MessageModel(senderId, message);

//             Setting the time message, extracting from Date
                model.setTimestamp(new Date().getTime());

                binding.enterMessage.setText("");

//                Storing the messages in DB

//                Creating a new node/branch in FB names chats and storing there the messages
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });
    }
}