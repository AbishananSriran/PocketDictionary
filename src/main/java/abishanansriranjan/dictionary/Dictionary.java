package abishanansriranjan.dictionary;

import static java.lang.Character.*;
import java.net.*;
import java.util.logging.*;
import java.io.*;
import java.util.*;
import org.json.*;

/*
 * @author Abishanan
 * @version 0.1.1
 */
public class Dictionary extends javax.swing.JFrame {
    // Using a Dictionary API.
    private static final String URL_STARTER = "https://api.dictionaryapi.dev/api/v2/entries/en_US/";
    private static final HashMap<String, String> cache = new HashMap<>();
    
    public Dictionary() {
        super("Pocket Dictionary");
        initComponents();
    }
    
    // For simplicity's sake.
    private static String capitalize(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    
    private static String addPeriod(String str) {
        if (!str.endsWith(".") && !str.endsWith("?") && !str.endsWith("!")) {
            str += ".";
        }
        return str;
    }
    
    // Searches for a result from endpoint for passed String argument "query".
    private static String searchQuery(String query) {
        // Check if a response is already cached.
        if (cache.containsKey(query)) {
            return cache.get(query);
        }
        
        String formatQuery = "";
        for (int i = 0; i < query.length(); i++) {
            if (isWhitespace(query.charAt(i))) {
                formatQuery += "%20";
            } else {
                formatQuery += query.charAt(i);
            }
        }
        
        // Assume HTTP request did not go through before try-catch statement.
        String returnResult = "Failed to search query!";
        try {
            URL search = new URL(URL_STARTER + formatQuery);
            try {
                HttpURLConnection connection = (HttpURLConnection) search.openConnection();
                connection.setRequestMethod("GET");
                
                // Set a timeout for better UX (5 seconds).
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                
                if (connection.getResponseCode() == 200) {
                    String getResult = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                    // Response is formatted such that there are extra square "[]" brackets.
                    // This causes problems when parsing, so removal of these brackets is necessary.
                    getResult = getResult.substring(1, getResult.length()-1);
                    
                    JSONObject root = new JSONObject(getResult);
                    String actualWord = capitalize(root.getString("word"));
                    JSONArray meanings = root.getJSONArray("meanings");
                    
                    returnResult = "";
                    for (int index = 0; index < meanings.length(); index++) {
                        JSONObject group = meanings.getJSONObject(index);
                        returnResult += String.format("%s (%s)\n", actualWord, capitalize(group.getString("partOfSpeech")));
                        
                        JSONArray definitionArr = group.getJSONArray("definitions");
                        for (int defIndex = 0; defIndex < definitionArr.length(); defIndex++) {
                            JSONObject definition = definitionArr.getJSONObject(defIndex);
                            
                            String def = addPeriod(capitalize(definition.getString("definition")));
                            returnResult += String.format("\n%s. %s\n", defIndex+1, def);
                            
                            if (definition.has("example")) {
                                String example = addPeriod(capitalize(definition.getString("example")));
                                returnResult += String.format("\nExample: %s\n", example);
                            }
                            if (definition.has("synonyms")) {
                                JSONArray synonyms = definition.getJSONArray("synonyms");
                                returnResult += "\nSynonyms: ";
                                for (int synIndex = 0; synIndex < synonyms.length(); synIndex++) {
                                    if (synIndex < synonyms.length()-1) {
                                        returnResult += synonyms.getString(synIndex) + ", ";
                                    } else {
                                        returnResult += synonyms.getString(synIndex) + ".";
                                    }
                                }
                                returnResult += "\n";
                            }
                        }
                        for (int j = 0; j < 50; j++) {
                            returnResult += "-";
                        }
                        returnResult += "\n";
                    }
                } else if (connection.getResponseCode() == 404) {
                    returnResult = "Query " + query + " is not a recognizable dictionary term.";
                } 
                cache.put(query, returnResult);
                connection.disconnect();
            } catch (IOException  ex) {
                Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return returnResult;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTitle = new javax.swing.JLabel();
        termLbl = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        mainTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        mainTitle.setForeground(new java.awt.Color(255, 125, 0));
        mainTitle.setText("The Pocket Dictionary");

        termLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        termLbl.setForeground(new java.awt.Color(255, 125, 0));
        termLbl.setText("Enter term to define:");

        search.setBackground(new java.awt.Color(255, 207, 158));
        search.setForeground(new java.awt.Color(255, 125, 0));
        search.setText("Search Term");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        output.setEditable(false);
        output.setColumns(20);
        output.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        output.setForeground(new java.awt.Color(255, 100, 0));
        output.setLineWrap(true);
        output.setRows(5);
        output.setWrapStyleWord(true);
        scrollPane.setViewportView(output);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(termLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search))
                    .addComponent(scrollPane))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainTitle)
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(termLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // Get search query.        
        String query = input.getText().trim().toLowerCase();
        
        if (query.isEmpty()){
            output.setText("Query cannot be empty.");
        } else {
            output.setText("Searching query, please wait...");
            output.setText(searchQuery(query));
        }
    }//GEN-LAST:event_searchActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dictionary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            new Dictionary().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField input;
    private javax.swing.JLabel mainTitle;
    private javax.swing.JTextArea output;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton search;
    private javax.swing.JLabel termLbl;
    // End of variables declaration//GEN-END:variables
}
