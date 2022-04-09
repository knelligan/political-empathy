package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {

    /* Global variable for the database queries */
    public static FirebaseFirestore globalFirestore;

    /* Global variable for the quotes list */
    public static List<Quote> globalQuoteList = new ArrayList<>();

    /* Global variable for the responses list */
    public static List<Quote> globalResponseList = new ArrayList<>();


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

        //update count of users
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

    public static void loadQuotes(CompleteListener completeListener) {
        //clear the existing quote list
        globalQuoteList.clear();

        //load quotes from db
        globalFirestore.collection("QUOTES").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //if successful, create a map to store documents retrieved from firebase
                        Map<String, QueryDocumentSnapshot> documentList = new ArrayMap<>();

                        //add each document to the map
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //put the document id and the document in the map as a key/value pair
                            documentList.put(document.getId(), document);
                        }
                        //get each listing of the quotes/id
                        QueryDocumentSnapshot quoteListDocument = documentList.get("NUM_QUOTES");

                        //store the total count of quotes in firebase
                        long quoteCount = quoteListDocument.getLong("COUNT");

                        //loop through all the documents in the firestore database
                        //update fields to include on the quote list
                        for (int i = 1; i <= quoteCount; i++) {
                            //(new Quote("Quote 1", "Quote a", "Economic", "Author", "Left", 10));

                            //get the quote id value
                            String quoteID = quoteListDocument.getString("QUOTE" + String.valueOf(i) + "_ID");

                            //get the actual quote document retrieved from firebase
                            QueryDocumentSnapshot quoteDocument = documentList.get(quoteID);

                            //now get all of the values to add to the quote class fields

                            //name of author
                            String author = quoteDocument.getString("AUTHOR");

                            //Get bias
                            String bias = quoteDocument.getString("BIAS");

                            //get quote text
                            String quoteText = quoteDocument.getString("QUOTE_TEXT");

                            //get the number value of the quote
                            int value = quoteDocument.getLong("QUOTE_VALUE").intValue();

                            //get quote type
                            String type = quoteDocument.getString("TYPE");

                            //add a new quote with these fields to the quotes list
                            globalQuoteList.add(new Quote("Quote " + i, quoteText, type, author, bias, value));
                        }
                        //invoke the complete listener interface based on a successful access of the database
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //invoke the complete listener interface based on a failure to access the database properly
                        completeListener.onFailure();
                    }
                });


    }

    public static void addResponse(CompleteListener completeListener) {

        //load quotes from db
        globalFirestore.collection("QUOTES").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //if successful, create a map to store documents retrieved from firebase
                        Map<String, QueryDocumentSnapshot> documentList = new ArrayMap<>();

                        //add each document to the map
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //put the document id and the document in the map as a key/value pair
                            documentList.put(document.getId(), document);
                        }
                        //get each listing of the quotes/id
                        QueryDocumentSnapshot quoteListDocument = documentList.get("NUM_QUOTES");

                        //store the total count of quotes in firebase
                        long quoteCount = quoteListDocument.getLong("COUNT");

                        //loop through all the documents in the firestore database
                        //update fields to include on the quote list
                        for (int i = 1; i <= quoteCount; i++) {
                            //(new Quote("Quote 1", "Quote a", "Economic", "Author", "Left", 10));

                            //get the quote id value
                            String quoteID = quoteListDocument.getString("QUOTE" + String.valueOf(i) + "_ID");

                            //get the actual quote document retrieved from firebase
                            QueryDocumentSnapshot quoteDocument = documentList.get(quoteID);

                            //now get all of the values to add to the quote class fields

                            //name of author
                            String author = quoteDocument.getString("AUTHOR");

                            //Get bias
                            String bias = quoteDocument.getString("BIAS");

                            //get quote text
                            String quoteText = quoteDocument.getString("QUOTE_TEXT");

                            //get the number value of the quote
                            int value = quoteDocument.getLong("QUOTE_VALUE").intValue();

                            //get quote type
                            String type = quoteDocument.getString("TYPE");

                            //add a new quote with these fields to the quotes list
                            globalQuoteList.add(new Quote("Quote " + i, quoteText, type, author, bias, value));
                        }
                        //invoke the complete listener interface based on a successful access of the database
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //invoke the complete listener interface based on a failure to access the database properly
                        completeListener.onFailure();
                    }
                });
    }
}
