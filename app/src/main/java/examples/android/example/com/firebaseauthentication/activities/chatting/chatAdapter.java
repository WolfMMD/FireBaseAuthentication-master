package examples.android.example.com.firebaseauthentication.activities.chatting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import examples.android.example.com.firebaseauthentication.databinding.RecyclerItemsBinding;

class ChatAdapter extends RecyclerView.Adapter <ChatAdapter.ChatViewHolder> {

    private List<ChatData> conversation;
    private RecyclerItemsBinding recyclerItemsBinding;

      ChatAdapter(List<ChatData> conversation){

         this.conversation=conversation;
     }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        recyclerItemsBinding=RecyclerItemsBinding.inflate(LayoutInflater.from(viewGroup.getContext()));

        return new ChatViewHolder(recyclerItemsBinding.getRoot());
    }


    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {

          chatViewHolder.bind(conversation.get(i));

    }

    @Override
    public int getItemCount() {

        return conversation.size();
    }



    class ChatViewHolder extends RecyclerView.ViewHolder{



         ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

         void bind(ChatData conversationData){

             recyclerItemsBinding.userName.setText(conversationData.getName());

             recyclerItemsBinding.message.setText(conversationData.getMessage());


         }
    }
}
