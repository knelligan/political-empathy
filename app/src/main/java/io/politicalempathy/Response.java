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


    /**
     * This method calculates what the value of each user response should be.
     * It takes in a responseNum which is the user-selection/response to the quote
     * * 1 maps to "strongly agree"
     * * 2 maps to "somewhat agree"
     * * 3 maps to "somewhat disagree"
     * * 4 maps to "strongly disagree"
     *
     * The other param is the quotenum: this allows us to map the user response
     * to the specific survey quote.
     *
     * The quotenum helps us find the quote value associated with each quote.
     * Quote values can either be -10 or +10 to make calculations easier.  The
     * intensity of user agreement/disagreement is reflected by percent of agreement
     * mulipliers.
     *
     * With the response/quote information, it then calculates a user response value.
     * A negative value multiplier is incorporated for values that are more "left" or
     * "libertarian," for "right" and "authoritarian" there is no multiplier since these
     * move on the political compass as positive values.
     *
     * Theres is a multiplier of "50%" (.5) mapped to "somewhat" responses (both agree/disagree).
     * When a user only somewhat agrees, the original quote value of (-10/+10) is
     * multiplied by .5.
     *
     * 
     * @param responseNum
     * @param quoteNum
     * @return
     */
    public double createResponseValue(int responseNum, int quoteNum) {
        double responseVal = 0.0;
        Quote currentQ;
        switch (responseNum) {
            case 1:
                //respondent strongly agrees, response value is equal to quote val
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
