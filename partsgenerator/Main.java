/*    */ package partsgenerator;
/*    */ 
/*    */ import javafx.application.Application;
/*    */ import javafx.fxml.FXMLLoader;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.stage.Stage;
/*    */ 
/*    */ public class Main
/*    */   extends Application
/*    */ {
/*    */   public void start(Stage stage) throws Exception {
/* 13 */     stage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("PartsWindow.fxml"))));
/* 14 */     stage.setTitle("Parts Generator v3.0");
/* 15 */     stage.setResizable(false);
/* 16 */     stage.show();
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 20 */     launch(args);
/*    */   }
/*    */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/partsgenerator/Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */