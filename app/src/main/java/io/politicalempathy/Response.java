package io.politicalempathy;



public class Response {
    String userID;
    String quoteID;
    double quoteValue;
    int responseNum;
    double responseValue;


    /**
     * @param userID
     * @param quoteID
     * @param responseNum
     */
    public Response(String userID, String quoteID, int responseNum) {
        this.userID = userID;
        this.quoteID = quoteID;
        this.quoteValue = 0;
        this.responseNum = responseNum;
        this.responseValue = -1;

    }

    public String getUserID() {
        return userID;
    }

    public String getQuoteID() {
        return quoteID;
    }

    public double getQuoteValue() {
        return quoteValue;
    }

    public int getResponseNum() {
        return responseNum;
    }

    public double getResponseValue() {
        return responseValue;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setQuoteID(String quoteID) {
        this.quoteID = quoteID;
    }

    public void setQuoteValue(double quoteValue) {
        this.quoteValue = quoteValue;
    }

    public void setResponseNum(int responseNum) {
        this.responseNum = responseNum;
    }

    public void setResponseValue(double responseValue) {
        this.responseValue = responseValue;
    }

    @Override
    public String toString() {
        return "Response{" +
                "userID='" + userID + '\'' +
                ", quoteID='" + quoteID + '\'' +
                ", quoteValue=" + quoteValue +
                ", responseNum=" + responseNum +
                ", responseValue=" + responseValue +
                '}';
    }
}
