package taxcalculator.taxcalculator;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class HelloController {

    //student loan input
    @FXML
    public ComboBox studentLoanChooser;
    @FXML

    //country input - determines tax and national insurance rates
    public ComboBox countryChooser;
    @FXML
    public ComboBox frequencyChooser;

    //income input
    @FXML
    private TextField grossIncome;



    //the labels  below display information when wage button is clicked
    @FXML
    private Label GrossIncomeDisplay1;
    @FXML
    private Label GrossIncomeDisplay2;
    @FXML
    private Label netWageDisplay;
    @FXML
    private Label countryChoiceDisplay;
    @FXML
    private Label taxDisplay;
    @FXML
    private Label ninsDisplay;

    @FXML
    public Label studentLoanChoiceDisplay;
    //the  labels  above display information when wage button is clicked

    //necessary variables declared now, used in various methods


    //===============non scot tax ===========================

    //this is the highest rate tax band
    @FXML
    int taxThree = 150001;

    //this is the 2nd highest tax band
    @FXML
    int taxTwo = 50270;

    //this is the 3rd tax band

    @FXML
    int taxOne= 12570;


    //=============== scot tax ===========================

    @FXML
    int scotTaxFive = 150000;
    @FXML
    int scotTaxFour = 43662;
    @FXML
    int scotTaxThree = 25296;
    @FXML
    int scotTaxTwo = 14667;
    @FXML
    int scotTaxOne= taxOne;

    //this represents amount of tax paid
    @FXML
    int taxPaid;

//=============== Student Loan Variables ===========================

    @FXML
    int studentLoanOne = 19895;
    @FXML
    int studentLoanTwo = 27295;
    @FXML
    int studentLoanFour = 25000;
    @FXML
    int studentLoanPaid;
    @FXML
    int natIns;
    @FXML
    int natInsBase = 12570;

    @FXML
    int maxNormalNatIns = (int) ((taxTwo - natInsBase) * 0.1325);

    //===========general variable=====================

    /*
    annualIncome is initialised here,
    defined in getGrossWage,
    which then returns the suitable value,
    for use in the other methods
    */
    @FXML
    double annualIncome;



    //Methods below:
    @FXML
    protected void wageButtonClick() {
/*for future, could all of these methods be combined into one method here,
to save redefining grossIncomeInt & annualIncome

 */
        getGrossWage();

        getNatIns();

        chooseTax();

        getStudentLoan();

        getNetWage();
    }


//this gets the gross wage, once it has been input by the user
    private double getGrossWage() {
      String grossIncomeNumber = grossIncome.getText();

      if (grossIncome.getText().isEmpty()){
          GrossIncomeDisplay1.setText("You must enter a number and choose a frequency above");
      }

        double grossIncomeDouble = Double.parseDouble(grossIncome.getText());

        String frequency = (String) frequencyChooser.getValue();

        if(frequency.equals("Per Week")){
            annualIncome=grossIncomeDouble*52;
        } else if (frequency.equals("Per Month")){
            annualIncome=grossIncomeDouble*12;
        } else {
            annualIncome = grossIncomeDouble*1;
        }



        GrossIncomeDisplay1.setText("You earn £" + " " + grossIncomeNumber + " " + frequency);
        GrossIncomeDisplay2.setText("For a total of £" + annualIncome + " Per Year");
        return annualIncome;

        //it says return value is never used, yet this is what gets used in the poursuivant methods
    }

    //this gets the national insurance contribution
    private void getNatIns() {

        if (annualIncome >= 50270) {
            natIns = (int) (((annualIncome - 50270) * 0.01325) + (maxNormalNatIns));
        } else if (annualIncome > natInsBase && annualIncome < 50270) {
            natIns = (int) ((annualIncome - natInsBase) * 0.1325);
        } else {
            natIns = 0;
        }

        ninsDisplay.setText("National Insurance Paid " + natIns );

    }

    //this chooses whether to use scottish or non scottish tax rates
    private void chooseTax() {

       //strange, the below does not work as a global variable...?

        String country= (String) countryChooser.getValue();

        if (country.equals("Scotland")
        ) {
            getScotTax();
        } else {
            getTax();
        }
    }

    //this works out non scot tax
    private void getTax() {

        double maxTaxOne = ((taxTwo - taxOne) * 0.2);
        double maxTaxTwo = ((taxThree - taxTwo) * 0.4);


        if (annualIncome > taxThree) {
            taxPaid = (int) (((annualIncome - taxThree) * 0.45) + maxTaxOne + maxTaxTwo);
        } else if (annualIncome >= taxTwo && annualIncome < taxThree) {
            taxPaid = (int) (((annualIncome - taxTwo) * 0.4) + maxTaxOne);
        } else if (annualIncome > taxOne && annualIncome < taxTwo) {
            taxPaid = (int) ((annualIncome - taxOne) * 0.2);
        } else {
            taxPaid = 0;
        }

        String country= (String) countryChooser.getValue();

        countryChoiceDisplay.setText("You chose " + country );
        taxDisplay.setText("Tax Paid " + taxPaid );
    }
    //this works out scot tax
    private void getScotTax() {

        //next variables represent the maximum amount of tax payable

        double maxScotOne = ((scotTaxTwo - scotTaxOne) * 0.19);
        double maxScotTwo = ((scotTaxThree - scotTaxTwo) * 0.2);
        double maxScotThree = ((scotTaxFour - scotTaxThree) * 0.21);
        double maxScotFour = ((scotTaxFive - scotTaxFour) * 0.41);



        if (annualIncome > scotTaxFive ) {
            taxPaid = (int) (((annualIncome - scotTaxFive ) * 0.46) + maxScotOne + maxScotTwo + maxScotThree + maxScotFour);
        } else if (annualIncome >= scotTaxFour && annualIncome < scotTaxFive ) {
            taxPaid = (int) (((annualIncome - scotTaxFour) * 0.41) + maxScotOne + maxScotTwo + maxScotThree);
        } else if (annualIncome >= scotTaxThree && annualIncome < scotTaxFour) {
            taxPaid = (int) (((annualIncome - scotTaxThree) * 0.21) + maxScotOne + maxScotTwo);
        } else if (annualIncome >= scotTaxTwo && annualIncome < scotTaxThree) {
            taxPaid = (int) (((annualIncome - scotTaxTwo) * 0.2) + maxScotOne);
        } else if (annualIncome >= scotTaxOne && annualIncome < scotTaxTwo) {
            taxPaid = (int) ((annualIncome - scotTaxOne) * 0.19);
        } else {
            taxPaid = 0;
        }


       String country = (String) countryChooser.getValue();
        countryChoiceDisplay.setText("You chose " + country);
        taxDisplay.setText("Tax Paid " + taxPaid );
    }

    private void getStudentLoan() {

        String studentLoanChoice = (String) studentLoanChooser.getValue();


        if (studentLoanChoice.equals("No Student Loan"))
        {
            studentLoanPaid = 0;

        } else  if (studentLoanChoice.equals("Loan received in 2012 or before") && annualIncome> studentLoanOne)
        {
            studentLoanPaid = (int) ((annualIncome- studentLoanOne) * 0.09);

        } else if(studentLoanChoice.equals("Loan received after 2012") && annualIncome> studentLoanTwo){
            studentLoanPaid = (int) ((annualIncome - studentLoanTwo) * 0.09);

        } else if (studentLoanChoice.equals("Scottish Student Loan") && annualIncome > studentLoanFour) {
            studentLoanPaid = (int) ((annualIncome - studentLoanFour) * 0.09);

        } else {
            studentLoanPaid = 0;

    }
        studentLoanChoiceDisplay.setText("Loan Paid: " + studentLoanPaid);
}

    private void getNetWage() {

        String net = String.valueOf(annualIncome - taxPaid - natIns - studentLoanPaid);
        netWageDisplay.setText("Annual Income: " + net);
    }

}