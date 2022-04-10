package io.politicalempathy;


import java.util.List;

public class Response {
    String userID;
    String quoteID;
    double quoteValue;
    int responseNum;
    double responseValue;
    String type;


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
        type = "";

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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double createResponseValue(int responseNum, int quoteNum) {
        double responseVal = 0.0;
        Quote currentQ;
        switch (responseNum) {
            case 1:
                //respondent agrees, response value is equal to quote val
                currentQ  = DbQuery.globalQuoteList.get(quoteNum);
                responseVal = currentQ.getQuoteValue();
                break;
            case 2:
                //respondent somewhat agrees, multiplier applied to diminish quote val
                currentQ  = DbQuery.globalQuoteList.get(quoteNum);
                responseVal = currentQ.getQuoteValue() * .5;
                break;
            case 3:
                //somewhat disagrees: flip the negative/positive of the quoteval and apply multiplier
                currentQ  = DbQuery.globalQuoteList.get(quoteNum);
                responseVal = (currentQ.getQuoteValue() * .5) * -1;
                break;
            case 4:
                //respondent disagrees: flip the negative/positive of the quoteval
                currentQ  = DbQuery.globalQuoteList.get(quoteNum);
                responseVal = (currentQ.getQuoteValue()) * -1;
                break;

            default:
        }
        return responseVal;
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
