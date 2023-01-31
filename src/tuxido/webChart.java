/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuxido;

import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
 
 
/**
 *
 * @author VJ002349
 */
public final class webChart  extends Thread{
    JDesktopPane panel;
    private JInternalFrame iframe;
    private JFXPanel fxPanel;
    private Scene scene;
    private StackPane root;
    
    /**
     *
     * @param panel1
     */
    public webChart(JDesktopPane panel){
        panel = panel;
    }
    /**
     *
     */
    public void initAndShowGUI() {
               
               iframe= new JInternalFrame (); 
               fxPanel = new JFXPanel();               
               iframe.add(fxPanel);
               iframe.setClosable(true);
               iframe.setResizable(true);
               iframe.setSize(640 , 450);
               Dimension desktopSize = panel.getSize();
               Dimension jInternalFrameSize = iframe.getSize();
               panel.add(iframe);
               iframe.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
               iframe.show();
               iframe.toFront();
               
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFX(fxPanel);
                }
            });
    }
    /**
     *
     * @param fxPanel
     */
    public void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        scene = createScene();
        fxPanel.setScene(scene);
    }
    /**
     *
     * @return
     */
    public Scene createScene() {
        root  =  new  StackPane();
        scene  =  new  Scene(root);
        root.getChildren().add(myBrowser());
        return (scene);
    }
    /**
     *
     * @return
     */
    public WebView myBrowser (){
        WebView myBrowser = new WebView();      
        WebEngine myWebEngine = myBrowser.getEngine(); 
        myWebEngine.load("http://hdmdev29/charts/examples/dynamic-update/index.htm");
        return myBrowser;
    }
    @Override
    public void run() {
       initAndShowGUI();
    }
}
