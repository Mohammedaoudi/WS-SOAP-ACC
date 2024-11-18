package ma.ensa.tpksoap2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ma.ensa.tpksoap2.R;
import ma.ensa.tpksoap2.beans.Compte;
import ma.ensa.tpksoap2.viewModels.CompteViewModel;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.CompteViewHolder> {
    private List<Compte> comptes;
    private CompteViewModel viewModel;

    public CompteAdapter(CompteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes != null ? comptes : new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return comptes != null ? comptes.size() : 0;
    }

    @NonNull
    @Override
    public CompteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_compte, parent, false);
        return new CompteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompteViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        holder.idTextView.setText("ID: " + compte.getId());
        holder.soldeTextView.setText("Solde: " + compte.getSolde());
        holder.typeTextView.setText("Type: " + compte.getType());

        // Si la date de création n'est pas nulle
        if (compte.getDateCreation() != null) {
            // Formater la date au format "dd/MM/yyyy"
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(compte.getDateCreation());
            holder.dateTextView.setText("Date: " + formattedDate);
        } else {
            // Si la date est nulle, afficher "N/A"
            holder.dateTextView.setText("Date: N/A");
        }

        holder.deleteButton.setOnClickListener(v -> viewModel.deleteCompte(compte.getId()));
    }




    static class CompteViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, soldeTextView, typeTextView, dateTextView;
        ImageButton deleteButton;

        public CompteViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            soldeTextView = itemView.findViewById(R.id.soldeTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
