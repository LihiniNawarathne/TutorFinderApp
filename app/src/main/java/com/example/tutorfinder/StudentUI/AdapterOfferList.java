package com.example.tutorfinder.StudentUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorfinder.StudentModels.OfferModel;
import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterOfferList extends  RecyclerView.Adapter<AdapterOfferList.HolderOffers >{

    Context context;
    List<OfferModel> offerList;

    public AdapterOfferList(Context context, ArrayList<OfferModel> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public HolderOffers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate view row_offer
        View view = LayoutInflater.from(context).inflate(R.layout.row_offer, parent, false);
        return new AdapterOfferList.HolderOffers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOffers holder, int position) {
        //get data
        OfferModel model = offerList.get(position);
        String Subject = model.getSubjectName();
        String time = model.getTime();
        String tutorName = model.getTutorName();
        double amount =model.getAmount();

        //set data to view
        holder.tvSubject1.setText(Subject);
        holder.tvtutor1.setText(tutorName);
        holder.tvtime1.setText(time);
        holder.tvofferAmount.setText(""+amount);

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    //holder class for views of offer
    class HolderOffers extends RecyclerView.ViewHolder{

        TextView tvSubject1,tvtutor1,tvtime1,tvofferAmount;

        public HolderOffers(@NonNull View itemView) {
            super(itemView);

            //initialize views
            tvSubject1= itemView.findViewById(R.id.tvSubject1);
            tvtutor1= itemView.findViewById(R.id.tvtutor1);
            tvtime1= itemView.findViewById(R.id.tvtime1);
            tvofferAmount= itemView.findViewById(R.id.tvofferAmount);
        }
    }

}
