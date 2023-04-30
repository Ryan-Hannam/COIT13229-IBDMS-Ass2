package Server;

//imports
import javax.swing.*;
import Domain.Drone;
import Domain.Fire;
import java.awt.*;
import java.util.LinkedList;
//class start
public class GUI extends JFrame { //unsure why this throws error
    //global variables
    private JButton bDeleteFire;
    private JButton bMoveDrone;
    private JButton bRecallAll;
    private JButton bShutdown;
    private JLabel lFunctions;
    private JLabel lInformation;
    private JPanel pFunctions;
    private JPanel pInformation;
    private DisplayDronesAndFires pMap;
    private JPanel pWestPane;
    public JTextArea tInformation;

    //null constructor
    public GUI() {
        initComponents();
    }
    
    //code generated via netbeans
    public void initComponents() {

        //create components
        pWestPane = new JPanel();
        pFunctions = new JPanel();
        lFunctions = new JLabel();
        bDeleteFire = new JButton();
        bMoveDrone = new JButton();
        bRecallAll = new JButton();
        bShutdown = new JButton();
        pInformation = new JPanel();
        lInformation = new JLabel();
        
        tInformation = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(tInformation);
        tInformation.setEditable(false);

        pMap = new DisplayDronesAndFires(Server.getDroneList(), Server.getFireList());

        //set the x button to close the applications
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //change this to call the shutdown() function?

        //set text for labels and buttons
        lInformation.setText("Info and Messages");
        lFunctions.setText("Functional Controls");
        bDeleteFire.setText("Delete Fire");
        bMoveDrone.setText("Move Drone");
        bRecallAll.setText("Recall All");
        bShutdown.setText("Shutdown");

        //set borders for the panes
        pMap.setBorder(BorderFactory.createLineBorder(null));
        pWestPane.setBorder(BorderFactory.createLineBorder(null));

        //layout setters and component adding for each pane
        GroupLayout pFunctionsLayout = new GroupLayout(pFunctions);
        pFunctions.setLayout(pFunctionsLayout);
        pFunctionsLayout.setHorizontalGroup(
            pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pFunctionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lFunctions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pFunctionsLayout.createSequentialGroup()
                        .addGroup(pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(bDeleteFire, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bMoveDrone, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(pFunctionsLayout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bShutdown))
                            .addGroup(pFunctionsLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(bRecallAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        pFunctionsLayout.setVerticalGroup(
            pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pFunctionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lFunctions)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bDeleteFire)
                    .addComponent(bRecallAll))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pFunctionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bMoveDrone)
                    .addComponent(bShutdown))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout pInformationLayout = new GroupLayout(pInformation);
        pInformation.setLayout(pInformationLayout);
        pInformationLayout.setHorizontalGroup(
            pInformationLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformationLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(tInformation)
                    .addGroup(pInformationLayout.createSequentialGroup()
                        .addComponent(lInformation)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pInformationLayout.setVerticalGroup(
            pInformationLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lInformation)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tInformation, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        GroupLayout pWestPaneLayout = new GroupLayout(pWestPane);
        pWestPane.setLayout(pWestPaneLayout);
        pWestPaneLayout.setHorizontalGroup(
            pWestPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pWestPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pWestPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(pInformation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pFunctions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pWestPaneLayout.setVerticalGroup(
            pWestPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pWestPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pFunctions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pInformation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        GroupLayout pMapLayout = new GroupLayout(pMap);
        pMap.setLayout(pMapLayout);
        pMapLayout.setHorizontalGroup(
            pMapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
        );
        pMapLayout.setVerticalGroup(
            pMapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pWestPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pMap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(pMap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pWestPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        //end of layout setting and component adding

        //button action listeners
        bDeleteFire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteFireActionPerformed(evt);
            }
        });
        
        bRecallAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRecallAllActionPerformed(evt);
            }
        });

        bMoveDrone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMoveDroneActionPerformed(evt);
            }
        });
        
        bShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bShutdownActionPerformed(evt);
            }
        });
    
        pack();
    }                      
    
    //button listeners
    private void bDeleteFireActionPerformed(java.awt.event.ActionEvent evt) {                                            
        Server.deleteFire();
    }                                           

    private void bMoveDroneActionPerformed(java.awt.event.ActionEvent evt) {                                             
        Server.moveDrone();
    } 

    private void bRecallAllActionPerformed(java.awt.event.ActionEvent evt) {                                             
        Server.recallAll();
    }                                            

    private void bShutdownActionPerformed(java.awt.event.ActionEvent evt) {                                          
        Server.shutdown();
    }                     
    
    //print String to console in GUI
    public void addMessageToConsole(String message) {
        System.out.println(message);
        String textFieldContent = tInformation.getText();
        tInformation.setText(textFieldContent + "\n" + message);
    }

    //redraws the map on call
    public void redrawMap() {
        this.pMap.repaint();
        this.pMap.revalidate();
    }

    //paints map in pMap
    class DisplayDronesAndFires extends JPanel {

        //global variables
        private LinkedList<Drone> droneList;
        private LinkedList<Fire> fireList;
        private double horizontalScale;
        private double verticalScale;

        //gets list of drones and fires
        public DisplayDronesAndFires(LinkedList<Drone> droneList, LinkedList<Fire> fireList) {
            this.droneList = droneList;
            this.fireList = fireList;
        }

        //painted area configuration
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.horizontalScale = 1;
            this.verticalScale = 1;

            //tint background color
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            //iterate and draw drones
            for (Drone drone : droneList) {   
                int xPos = (int) (drone.getDroneXPos() * horizontalScale);
                int yPos = (int) (drone.getDroneYPos() * verticalScale);
                //draw drone icon
                g.setColor(Color.blue);
                g.fillRect(xPos, yPos, 50, 50);  
                //draw drone text
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Drone - ID: " + drone.getDroneID(), xPos, yPos + 65);   
            }

            //iterate and draw fires
            for (Fire fire : fireList) {      
                int xPos = (int) (fire.getfireXPos() * horizontalScale);
                int yPos = (int) (fire.getfireYPos() * verticalScale);
                //draw fire icon
                g.setColor(Color.RED);
                g.fillOval(xPos, yPos, 50, 50);           
                //draw fire text
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Fire - ID: " + fire.getfireID(), xPos, yPos + 65);   
            }
        }
    }
}
