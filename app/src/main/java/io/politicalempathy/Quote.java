package io.politicalempathy;

public class Quote {
    String quoteID;
    String quoteText;
    String type;
    String author;
    String bias;
    double quoteValue;

    //creates a quote object used to display on each quote activity screen
    public Quote(String quoteID, String quoteText, String type, String author, String bias, double quoteValue) {

        //the name of the quote "quote 1... quote 2..."
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

}
