package karl.vargas.whatsappclone.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import karl.vargas.whatsappclone.Fragments.CallsFragment;
import karl.vargas.whatsappclone.Fragments.ChatsFragment;
import karl.vargas.whatsappclone.Fragments.StatusFragment;


// TABS CHATS / STATUS / CALLS FRAGMENT
public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        // Navigating through tabs
        switch (position) {
            case 0: return  new ChatsFragment();
            case 1: return  new StatusFragment();
            case 2: return  new CallsFragment();
            default: return  new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;

    };


    // Default tab == Chats
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if(position == 0) {

            title = "CHATS";
        }
        if(position == 1) {
            title = "STATUS";
        }
        if(position == 2) {
            title = "CALLS";

        }
        return title;
    }
}
