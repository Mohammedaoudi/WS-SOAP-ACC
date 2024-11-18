package ma.ensa.tpksoap2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.tpksoap2.adapter.CompteAdapter;
import ma.ensa.tpksoap2.beans.Compte;
import ma.ensa.tpksoap2.beans.TypeCompte;
import ma.ensa.tpksoap2.service.CompteService;
import ma.ensa.tpksoap2.viewModels.CompteViewModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompteAdapter adapter;
    private CompteViewModel viewModel;
    private Spinner typeSpinner;
    private EditText soldeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        typeSpinner = findViewById(R.id.typeSpinner);
        soldeEditText = findViewById(R.id.soldeEditText);
        Button addButton = findViewById(R.id.addButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup TypeCompte spinner
        ArrayAdapter<TypeCompte> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, TypeCompte.values());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdapter);

        viewModel = new ViewModelProvider(this).get(CompteViewModel.class);

        adapter = new CompteAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        viewModel.getComptes().observe(this, comptes -> {
            adapter.setComptes(comptes);
            adapter.notifyDataSetChanged();
        });

        addButton.setOnClickListener(v -> {
            String type = ((TypeCompte) typeSpinner.getSelectedItem()).name();
            double solde = Double.parseDouble(soldeEditText.getText().toString());
            viewModel.addCompte(type, solde);
            soldeEditText.setText("");
        });
    }
}