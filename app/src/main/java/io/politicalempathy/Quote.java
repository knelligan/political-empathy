package io.politicalempathy;

public class Quote {
    String quoteID;
    String quoteText;
    String type;
    String author;
    String bias;
    double quoteValue;
    String job;




    /**
     * Creates a quote object used to display on each quote activity screen
     */
    public Quote(String quoteID, String quoteText, String type, String author, String bias, double quoteValue, String job) {

        //the id in firebase associated with the quote"
        this.quoteID = quoteID;

        //the actual quote text
        this.quoteText = quoteText;

        //economic or social
        this.type = type;

        //who wrote the quote
        this.author = author;

        //political party
        this.bias = bias;

        //value -10 - +10
        this.quoteValue = quoteValue;

        //quote author's job
        this.job = job;


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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
                '}';
    }

}
