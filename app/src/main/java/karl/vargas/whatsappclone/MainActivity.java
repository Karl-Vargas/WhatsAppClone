package karl.vargas.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import karl.vargas.whatsappclone.Adapter.FragmentAdapter;
import karl.vargas.whatsappclone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//    Binding Objects
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FB binding
        mAuth = FirebaseAuth.getInstance();

        // Setting up the binding of the tabs fragments
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    // Displaying menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    Methods to Settings menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
//                Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.groupChat:
//                Toast.makeText(this, "Group Chat is started", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, GroupChatActivity.class);
                startActivity(intent1);
                break;

            case R.id.logout:
                //Logged out in the backend
                mAuth.signOut();
                //Logging out from display
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Logged out\nSee you soon", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}