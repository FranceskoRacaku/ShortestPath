import javax.swing.*;
import java.awt.*;

public class SearchField {
    // Source and City will use this class for obj. to contain: CityLabel, CityInput, StateLabel, StateInput (input or selection based),
    // AddBtn, Result/ErrorField

    public JTextField cityInput;
    public JTextField stateInput;
    public JButton btn;
    public JLabel resultsLabel;
    public JComboBox<String> states;
    private int cols;
    private Font font;
    private String[] STATES = {"", "AL", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI",
                                "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
                                    "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "DC"}; // no AL, HI, but DC added

    SearchField(){
        this.font = new Font("Georgia", Font.PLAIN, 12);
        this.cityInput = new JTextField(6);
        this.stateInput = new JTextField(1);
        btn = new JButton("ADD");
    }

    SearchField(int cityCols, int stateCols, int cityMaxChars, int stateMaxChars, Font font, String btnTxt, String btnColor){
        this.cols = cols;
        this.font = font;

        this.cityInput = new JTextField(cityCols);
        this.cityInput.setFont(font);
        this.cityInput.setDocument(new JTextFieldLimit(cityMaxChars));

        this.stateInput = new JTextField(stateCols);
        this.stateInput.setFont(font);
        this.stateInput.setDocument(new JTextFieldLimit(stateMaxChars));

        this.btn = new JButton(btnTxt);
        this.btn.setBackground(Color.decode(btnColor));

        this.resultsLabel = new JLabel("");

        this.states = new JComboBox<String>(STATES);
        this.states.setFont(new Font("Georgia", Font.BOLD, 14));
    }

    public String getCityInput(){
        if(!cityInput.getText().isEmpty()) return cityInput.getText().trim();
        else return "";
    }

    public String getStateInput(){
        if(!stateInput.getText().isEmpty()) return stateInput.getText().trim();
        else return "";
    }

    public String getStateSelection(){
        return states.getSelectedItem().toString();
    }

    public String checkValidInput(String city, String state){
        // Letters only (a-Z, A-Z), City can contain '.', State cannot
        // state input ALREADY restricted to 2 characters, city input ALREADY restricted to 85 characters.

        city = city.replaceAll("\\s", ""); // remove spaces

        if(!city.matches("[a-zA-Z]*")){
            System.out.println("1");
            if(!city.contains(".")) return "Invalid, letters only!";
        }

        if(!state.matches("[a-zA-Z]*")) return "Invalid, letters only for state!";

        return "";
    }

    public void clearAll(){
        this.cityInput.setText("");
        this.states.setSelectedIndex(0);
        this.resultsLabel.setText("");
    }
}