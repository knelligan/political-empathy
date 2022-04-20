package io.politicalempathy;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static List<Response> globalResponseList = new ArrayList<>();

    /* User ID to be used for response list */
    public static String globalUserID;

    /* User ID to be used for response list */
    public static int globalQuoteCounter;

    /* User ID to be used for response list */
    public static int globalEconScore;

    /* User ID to be used for response list */
    public static int globalSocScore;

    /* User ID to be used for response list */
    public static ArrayList<Integer> globalPreviousResponses;

    /* Default user response value */
    public static final double DEFAULT_VALUE = -100.0;


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
        //clear and load the default response list
        globalResponseList.clear();
        //loadDefaultResponses();

        //clear the existing quote list
        globalQuoteList.clear();

        //create global user id to be used in response objects
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        globalUserID = currentFirebaseUser.getUid();
        System.out.println("user id is: " + globalUserID);

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

                            //get quote type
                            String job = quoteDocument.getString("JOB");

                            //add a new quote with these fields to the quotes list
                            globalQuoteList.add(new Quote("Quote " + i, quoteText, type, author, bias, value, job));
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

    public static void addResponse(int quoteNum, int responseNum, CompleteListener completeListener) {
        //path to response:


        //get user id
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();

        //need quoteID, quoteValue, responseNum, responseValue

        //create a map to store user info both user id and responses
        //this will be used to enter this data into firestore
        Map<String, Object> userResponses = new ArrayMap<>();

        //Put the email id into the firestore map
        userResponses.put("USER_ID", userID);

        //put the quoteid into the firestore map
        Quote quote = globalQuoteList.get(quoteNum);
        String quoteID = quote.getQuoteID();
        System.out.println("quote id: " + quoteID);
        //userResponses.put("QUOTE_ID", quoteID);

        //put the score into the firestore map
        double qVal = quote.getQuoteValue();
        System.out.println("quote val: " + qVal);
        //userResponses.put("QUOTE_VALUE", qVal);

        //put the response number selection (strongly agree - strongly disagree) into firestore map
        //userResponses.put("RESPONSE_NUM", responseNum);

        //create new Response and add to global response arraylist
        Response resp = new Response(userID, quoteID, responseNum);

        //put the responseValue into the map
        double userResponse = resp.createResponseValue(responseNum, quoteNum);
        System.out.println("user response value: " + userResponse);

        //set response value (
        resp.setResponseValue(userResponse);

        //set type or response (either economic or social)
        resp.setType(quote.getType());
        globalResponseList.add(resp);


        //add user selected responses
        if (globalResponseList.size() > 0 && globalResponseList.get(0) != null) {
            userResponses.put("Q1_RESP", globalResponseList.get(0).getResponseValue());
        } else {
            userResponses.put("Q1_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 1 && globalResponseList.get(1) != null) {
            userResponses.put("Q2_RESP", globalResponseList.get(1).getResponseValue());
        } else {
            userResponses.put("Q2_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 2 && globalResponseList.get(2) != null) {
            userResponses.put("Q3_RESP", globalResponseList.get(2).getResponseValue());
        } else {
            userResponses.put("Q3_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 3 && globalResponseList.get(3) != null) {
            userResponses.put("Q4_RESP", globalResponseList.get(3).getResponseValue());
        } else {
            userResponses.put("Q4_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 4 && globalResponseList.get(4) != null) {
            userResponses.put("Q5_RESP", globalResponseList.get(4).getResponseValue());
        } else {
            userResponses.put("Q5_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 5 && globalResponseList.get(5) != null) {
            userResponses.put("Q6_RESP", globalResponseList.get(5).getResponseValue());
        } else {
            userResponses.put("Q6_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 6 && globalResponseList.get(6) != null) {
            userResponses.put("Q7_RESP", globalResponseList.get(6).getResponseValue());
        } else {
            userResponses.put("Q7_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 7 && globalResponseList.get(7) != null) {
            userResponses.put("Q8_RESP", globalResponseList.get(7).getResponseValue());
        } else {
            userResponses.put("Q8_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 8 && globalResponseList.get(8) != null) {
            userResponses.put("Q9_RESP", globalResponseList.get(8).getResponseValue());
        } else {
            userResponses.put("Q9_RESP", DEFAULT_VALUE);
        }
        if (globalResponseList.size() > 9 && globalResponseList.get(9) != null) {
            userResponses.put("Q10_RESP", globalResponseList.get(9).getResponseValue());
        } else {
            userResponses.put("Q10_RESP", DEFAULT_VALUE);
        }


        //researched here: https://stackoverflow.com/questions/53129967/how-to-pass-a-firestore-document-reference-for-a-collection-made-in-mainactivity
        //Create a document reference for the user response data in firestore
        DocumentReference responseDocument = globalFirestore.collection("RESPONSES").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //researched here: https://www.tabnine.com/code/java/methods/com.google.cloud.firestore.WriteBatch/set
        //documentation: https://firebase.google.com/docs/reference/js/v8/firebase.firestore.WriteBatch
        //batch will be used to perform multiple writes as a single atomic unit
        WriteBatch batch = globalFirestore.batch();

        //set the value for response
        batch.set(responseDocument, userResponses);

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //checks if the user response was registered correctly
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //checks if the user response registered incorrectly
                        completeListener.onFailure();
                    }
                });
    }


    public static void loadPreviousData(CompleteListener completeListener) {

        globalPreviousResponses = new ArrayList<>();

        //create global user id to be used in response objects
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String tempUserID = currentFirebaseUser.getUid();
        System.out.println("user id for fetch data is: " + globalUserID);

        //load responses from db
        globalFirestore.collection("RESPONSES").get()
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
                        //check if document exists

                        if(documentList.containsKey(tempUserID)) {
                            //get the previous user response
                            QueryDocumentSnapshot userResponses = documentList.get(tempUserID);

                            //add response 1
                            globalPreviousResponses.add(userResponses.getLong("Q1_RESP").intValue());

                            //add response 2
                            globalPreviousResponses.add(userResponses.getLong("Q2_RESP").intValue());

                            //add response 3
                            globalPreviousResponses.add(userResponses.getLong("Q3_RESP").intValue());

                            //add response 4
                            globalPreviousResponses.add(userResponses.getLong("Q4_RESP").intValue());

                            //add response 5
                            globalPreviousResponses.add(userResponses.getLong("Q5_RESP").intValue());

                            //add response 6
                            globalPreviousResponses.add(userResponses.getLong("Q6_RESP").intValue());

                            //add response 7
                            globalPreviousResponses.add(userResponses.getLong("Q7_RESP").intValue());

                            //add response 8
                            globalPreviousResponses.add(userResponses.getLong("Q8_RESP").intValue());

                            //add response 9
                            globalPreviousResponses.add(userResponses.getLong("Q9_RESP").intValue());

                            //add response 10
                            globalPreviousResponses.add(userResponses.getLong("Q10_RESP").intValue());
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