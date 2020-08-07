package org.fhi360.ddd;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Drug;
import java.util.Objects;

public class DrugSetup extends AppCompatActivity {
    private Button button;
    private EditText drugName, basicUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_setup);
        button = findViewById(R.id.register);
        drugName = findViewById(R.id.drugName);
        basicUnit = findViewById(R.id.basicUnit);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InventorySetup.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drugName1 = drugName.getText().toString();
                String basicUnit1 = basicUnit.getText().toString();
                if (validateInput(drugName1, basicUnit1)) {
                    Drug drug = DDDDb.getInstance(DrugSetup.this).drugRepository().findByDrugName(drugName1);
                    if (drug != null) {
                        FancyToast.makeText(getApplicationContext(), "Drug  with these credentials already exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        Drug drug1 = new Drug();
                        drug1.setDrugName(drugName1);
                        drug1.setBasicUnit(basicUnit1);
                        DDDDb.getInstance(DrugSetup.this).drugRepository().save(drug1);
                        FancyToast.makeText(getApplicationContext(), "Drug Save successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        drugName.setText("");
                        basicUnit.setText("");
                    }
                }
            }
        });

    }

    private boolean validateInput(String drugName1, String basicUnit1) {
        if (drugName1.isEmpty()) {
            drugName.setError("drug name can not be empty");
            return false;
        } else if (basicUnit1.isEmpty()) {
            basicUnit.setError("basic unit can not be empty");
            return false;

        }
        return true;


    }

}
