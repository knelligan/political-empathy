package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Map;

public class DbQuery {

    /* Global variable for the database queries */
    public static FirebaseFirestore globalFirestore;

    /* Global variable for the quotes list */
    public static List<Quote> globalFirestore;

    public static void createUserData(String email, String name, CompleteListener cl) {
        //researched here: https://firebase.google.com/docs/firestore/manage-data/transactions

        //create a map to store user info
        Map<String, Object> userData = new ArrayMap<>();

        //Put the email id into the map
        userData.put("EMAIL_ID", email);

        //put the user name into the app
        userData.put("NAME", name);

        //put the score into the map
        userData.put("FINAL_SCORE", 0);

        //researched here: https://stackoverflow.com/questions/53129967/how-to-pass-a-firestore-document-reference-for-a-collection-made-in-mainactivity
        //Create a document reference for the user data
        DocumentReference userDocument = globalFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //researched here: https://www.tabnine.com/code/java/methods/com.google.cloud.firestore.WriteBatch/set
        //documentation: https://firebase.google.com/docs/reference/js/v8/firebase.firestore.WriteBatch
        //batch will be used to perform multiple writes as a single atomic unit
        WriteBatch batch = globalFirestore.batch();

        //set the value for user
        batch.set(userDocument, userData);

        //update count
        DocumentReference countDocument = globalFirestore.collection("USERS").document("TOTAL_USERS");
        batch.update(countDocument, "COUNT", FieldValue.increment(1));

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //checks if the user registered correctly
                cl.onSuccess();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //checks if the user registered incorrectly
                cl.onFailure();
            }
        });
    }

    public static void loadQuestions(){}

}
