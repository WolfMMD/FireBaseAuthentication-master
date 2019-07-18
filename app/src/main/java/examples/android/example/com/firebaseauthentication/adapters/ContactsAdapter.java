package examples.android.example.com.firebaseauthentication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import examples.android.example.com.firebaseauthentication.databinding.UsersRecyclerItemsBinding;
import examples.android.example.com.firebaseauthentication.interfaces.AdapterToActivityInterface;
import examples.android.example.com.firebaseauthentication.data.UserData;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private AdapterToActivityInterface toActivityInterface;

    private List<UserData> usersNames;
    private UsersRecyclerItemsBinding recyclerItemsBinding;

    public ContactsAdapter(List<UserData> usersNames, AdapterToActivityInterface toActivityInterface) {

        this.usersNames = usersNames;
        this.toActivityInterface = toActivityInterface;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        recyclerItemsBinding = UsersRecyclerItemsBinding.inflate(LayoutInflater.from(viewGroup.getContext()));
        return new ContactsViewHolder(recyclerItemsBinding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder contactsViewHolder, int i) {
        contactsViewHolder.bind(usersNames.get(i));

    }

    @Override
    public int getItemCount() {
        return usersNames.size();
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {

        ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(final UserData userData) {


            recyclerItemsBinding.userName.setText(userData.getFullName());

            recyclerItemsBinding.userName.setOnClickListener(v -> {

                toActivityInterface.setClickedUser(userData);


            });
        }


    }
}
