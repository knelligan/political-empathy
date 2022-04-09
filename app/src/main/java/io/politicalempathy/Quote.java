package io.politicalempathy;

public class Quote {
    String quoteID;
    String quoteText;
    String type;
    String author;
    String bias;
    double quoteValue;

    //testing this, need toinclude with quote
    int response;

    //creates a quote object used to display on each quote activity screen
    public Quote(String quoteID, String quoteText, String type, String author, String bias, double quoteValue) {

        //the id in firebase associated with the quote"
        this.quoteID = quoteID;

        //the actual quote text
        this.quoteText = quoteText;

        //economic or social
        this.type = type;

        //who wrote the quote
        this.author = author;

        //left, right, up, down
        this.bias = bias;

        //value -10 - +10
        this.quoteValue = quoteValue;
        
        //set response value to -1 before user responds
        response = -1;
    }

    public String getQuoteID() {
        return quoteID;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getType() {
        return type;
    }


    public String getAuthor() {
        return author;
    }

    public String getBias() {
        return bias;
    }

    public double getQuoteValue() {
        return quoteValue;
    }

    public int getResponseValue() {
        return response;
    }

    public void setResponseValue(int responseVal){
        //for strongly agree: 1
        //for somewhat agree: 2
        //for somewhat disagree: 3
        //for strongly disagree: 4
        response = responseVal;

    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteID='" + quoteID + '\'' +
                ", quoteText='" + quoteText + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", bias='" + bias + '\'' +
                ", quoteValue=" + quoteValue +
                ", response=" + response +
                '}';
    }

}
