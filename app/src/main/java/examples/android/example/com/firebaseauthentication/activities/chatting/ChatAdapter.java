package examples.android.example.com.firebaseauthentication.activities.chatting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.databinding.RecieverRecyclerItemsBinding;
import examples.android.example.com.firebaseauthentication.databinding.SenderRecyclerItemBinding;

class ChatAdapter extends RecyclerView.Adapter <ChatAdapter.ChatViewHolder> {

    private List<Message>  conversation;
    private RecieverRecyclerItemsBinding recieverRecyclerItemsBinding;
    private SenderRecyclerItemBinding senderRecyclerItemBinding;

    ChatAdapter(List<Message> conversation){

         this.conversation=conversation;
     }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(conversation.get(i).getType() == 0) {

            senderRecyclerItemBinding = SenderRecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
            return new SenderViewHolder(senderRecyclerItemBinding);
        }
        else {
            recieverRecyclerItemsBinding = RecieverRecyclerItemsBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
            return new ReceiverViewHolder(recieverRecyclerItemsBinding);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {

          chatViewHolder.bind(conversation.get(i));

    }

    @Override
    public int getItemCount() {

        return conversation.size();
    }

    @Override
    public int getItemViewType(int position) {
        return conversation.get(position).getType();
    }


     abstract class ChatViewHolder extends RecyclerView.ViewHolder {


        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(Message conversationData);

    }



    class SenderViewHolder extends ChatViewHolder{

         SenderViewHolder(SenderRecyclerItemBinding binding){
            super(binding.getRoot());

        }

        @Override
        void bind(Message conversationData) {

             senderRecyclerItemBinding.message.setText(conversationData.getMessageBody());
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String time=dateFormat.format(new Date(conversationData.getTime().getSeconds()*1000));
             senderRecyclerItemBinding.time.setText(time);
        }
    }


    class ReceiverViewHolder extends ChatViewHolder{

        ReceiverViewHolder(RecieverRecyclerItemsBinding binding){
            super(binding.getRoot());

        }

        @Override
        void bind(Message conversationData) {

            recieverRecyclerItemsBinding.message.setText(conversationData.getMessageBody());
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String time=dateFormat.format(new Date(conversationData.getTime().getSeconds()*1000));
            recieverRecyclerItemsBinding.time.setText(time);
        }
    }
}
