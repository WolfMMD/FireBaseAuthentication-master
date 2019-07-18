package examples.android.example.com.firebaseauthentication.adapters;

import android.icu.text.RelativeDateTimeFormatter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import examples.android.example.com.firebaseauthentication.data.Message;
import examples.android.example.com.firebaseauthentication.databinding.RecieverRecyclerItemsBinding;
import examples.android.example.com.firebaseauthentication.databinding.SenderRecyclerItemBinding;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> conversation;

    public ChatAdapter(List<Message> conversation) {

        this.conversation = conversation;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int senderReceiverType) {


        if (senderReceiverType == 0) {

            SenderRecyclerItemBinding senderRecyclerItemBinding = SenderRecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            return new SenderViewHolder(senderRecyclerItemBinding);
        } else {
            RecieverRecyclerItemsBinding recieverRecyclerItemsBinding = RecieverRecyclerItemsBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
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
        return conversation.get(position).getSenderOrReceiverType();
    }


    abstract class ChatViewHolder extends RecyclerView.ViewHolder {


        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(Message conversationData);

    }


    public class SenderViewHolder extends ChatViewHolder {

        SenderRecyclerItemBinding binding;

        SenderViewHolder(SenderRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        @Override
        void bind(Message conversationData) {

            if (conversationData.getType() == 0)
                binding.message.setText(conversationData.getMessageBody());

            else
                //picasso for the image view
                binding.message.setText("image here");

            CharSequence ago = DateUtils.getRelativeTimeSpanString(conversationData.getTime().getSeconds() * 1000,
                    new Date().getTime(), DateUtils.MINUTE_IN_MILLIS);
            if (ago.equals("0 minutes ago"))
                ago = "just now";

            binding.time.setText((ago));
        }
    }


    public class ReceiverViewHolder extends ChatViewHolder {

        RecieverRecyclerItemsBinding binding;

        ReceiverViewHolder(RecieverRecyclerItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        void bind(Message conversationData) {

            if (conversationData.getType() == 0)
                binding.message.setText(conversationData.getMessageBody());

            else
                //picasso for the image view
                binding.message.setText("image here");


            CharSequence ago = DateUtils.getRelativeTimeSpanString(conversationData.getTime().getSeconds() * 1000, new Date().getTime(), DateUtils.MINUTE_IN_MILLIS);

            if (ago.equals("0 minutes ago"))
                ago = "just now";
            binding.time.setText((ago));

        }
    }


    public void setMessageList(List<Message> messageList) {

        conversation.addAll(messageList);
        notifyDataSetChanged();

    }
}
