/*    */ package partsgenerator;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.ResourceBundle;
/*    */ import javafx.fxml.FXML;
/*    */ import javafx.fxml.Initializable;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.Label;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.image.ImageView;
/*    */ 
/*    */ 
/*    */ public class PreviewWindowController
/*    */   implements Initializable
/*    */ {
/*    */   @FXML
/*    */   private Label stlPreviewLabel;
/*    */   
/*    */   public void initialize(URL url, ResourceBundle rb) {}
/*    */   
/*    */   public void setSTLPreview(String projectPath) {
/*    */     try {
/* 26 */       FileInputStream image = new FileInputStream(projectPath + "/preview.png");
/* 27 */       this.stlPreviewLabel.setGraphic((Node)new ImageView(new Image(image)));
/* 28 */       image.close();
/* 29 */       (new File(projectPath + "/preview.png")).delete();
/* 30 */     } catch (IOException iOException) {}
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/partsgenerator/PreviewWindowController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */