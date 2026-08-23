/*      */ package partsgenerator;
/*      */ import com.itextpdf.kernel.pdf.PdfDocument;
/*      */ import com.itextpdf.kernel.pdf.PdfWriter;
/*      */ import com.itextpdf.layout.Document;
/*      */ import com.itextpdf.layout.element.Cell;
/*      */ import com.itextpdf.layout.element.IBlockElement;
/*      */ import com.itextpdf.layout.element.Paragraph;
/*      */ import com.itextpdf.layout.element.Table;
/*      */ import com.itextpdf.layout.properties.TextAlignment;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URL;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Scanner;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.concurrent.Task;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.Event;
/*      */ import javafx.fxml.FXML;
/*      */ import javafx.fxml.FXMLLoader;
/*      */ import javafx.fxml.Initializable;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.control.Button;
/*      */ import javafx.scene.control.CheckBox;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.ProgressBar;
/*      */ import javafx.scene.control.ProgressIndicator;
/*      */ import javafx.scene.control.RadioButton;
/*      */ import javafx.scene.control.Spinner;
/*      */ import javafx.scene.control.SpinnerValueFactory;
/*      */ import javafx.scene.control.TextField;
/*      */ import javafx.scene.control.ToggleGroup;
/*      */ import javafx.scene.media.Media;
/*      */ import javafx.scene.media.MediaPlayer;
/*      */ import javafx.stage.DirectoryChooser;
/*      */ import javafx.stage.Stage;
            import java.nio.file.Files;
            import java.nio.file.Path;
            import java.nio.file.Paths;
            import java.util.stream.Stream;
            import java.io.IOException;
/*      */ 
/*      */ public class PartsWindowController implements Initializable {
/*   47 */   private final LinkedHashSet<Parts> PARTS = new LinkedHashSet<>();
/*      */   
/*      */   private Process stlProcess;
/*      */   private boolean isCancelled;
/*      */   private String tempOpenSCADPath;
/*      */   @FXML
/*      */   private ToggleGroup motorToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup mountToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup electronicsToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup sensorToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup shaftToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup orientationToggleGroup;
/*      */   @FXML
/*      */   private ToggleGroup caliberToggleGroup;
/*      */   @FXML
/*      */   private RadioButton etzgmp38RadioButton;
/*      */   @FXML
/*      */   private RadioButton fc555RadioButton;
/*      */   @FXML
/*      */   private RadioButton jgy370RadioButton;
/*      */   @FXML
/*      */   private RadioButton m634jsRadioButton;
/*      */   @FXML
/*      */   private RadioButton dillonRadioButton;
/*      */   @FXML
/*      */   private RadioButton smallElectronicsRadioButton;
/*      */   @FXML
/*      */   private RadioButton largeElectronicsRadioButton;
/*      */   @FXML
/*      */   private RadioButton switchRadioButton;
/*      */   @FXML
/*      */   private RadioButton photosensorRadioButton;
/*      */   @FXML
/*      */   private RadioButton proximityRadioButton;
/*      */   @FXML
/*      */   private RadioButton pinnedShaftRadioButton;
/*      */   @FXML
/*      */   private RadioButton hexCouplerRadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber9RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber40RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber45RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber50RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber300RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber4570RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliberSmallPistolRadioButton;
/*      */   @FXML
/*      */   private RadioButton caliberLargePistolRadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber223RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber65RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliber308RadioButton;
/*      */   @FXML
/*      */   private RadioButton caliberSmallRifleRadioButton;
/*      */   @FXML
/*      */   private RadioButton caliberLargeRifleRadioButton;
/*      */   @FXML
/*      */   private RadioButton caliberLongRifleRadioButton;
/*      */   @FXML
/*      */   private RadioButton baseUpRadioButton;
/*      */   @FXML
/*      */   private RadioButton baseDownRadioButton;
/*      */   @FXML
/*      */   private RadioButton noseUpDownRadioButton;
/*      */   @FXML
/*      */   private CheckBox templateCheckBox;
/*      */   @FXML
/*      */   private CheckBox wallExtenderCheckBox;
/*      */   @FXML
/*      */   private CheckBox slidePlateLatchCheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp9CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp40CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp45CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp223CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp300CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseUp308CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown9CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown40CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown45CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown223CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown300CheckBox;
/*      */   @FXML
/*      */   private CheckBox noseDown308CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseUp9CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseUp40CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseUp45CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown9CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown40CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown45CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown223CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown300CheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown308CheckBox;
/*      */   @FXML
/*      */   private CheckBox clutchCoverCheckBox;
/*      */   @FXML
/*      */   private CheckBox collatorHandleCheckBox;
/*      */   @FXML
/*      */   private CheckBox adapter7;
/*      */   @FXML
/*      */   private CheckBox adapter8;
/*      */   @FXML
/*      */   private CheckBox adapter9;
/*      */   @FXML
/*      */   private CheckBox adapter10;
/*      */   @FXML
/*      */   private CheckBox adapter11;
/*      */   @FXML
/*      */   private CheckBox adapter12;
/*      */   @FXML
/*      */   private CheckBox adapter13;
/*      */   @FXML
/*      */   private CheckBox adapter14;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie7CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie9CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie32CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie40CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie44CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie45CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie223CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie300CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie308CheckBox;
/*      */   @FXML
/*      */   private CheckBox bulletFeedDie4570CheckBox;
/*      */   @FXML
/*      */   private CheckBox app9BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app45BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app32BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app223BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app40BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app308BulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox app9BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app32BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app40BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app45BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app223BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app308BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox collatorSensorCheckBox;
/*      */   @FXML
/*      */   private CheckBox isLongRifleBulletCheckBox;
/*      */   @FXML
/*      */   private CheckBox addRampsCheckBox;
/*      */   @FXML
/*      */   private CheckBox isRifleBrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox addPivotsCheckBox;
/*      */   @FXML
/*      */   private CheckBox addSlidesCheckBox;
/*      */   @FXML
/*      */   private CheckBox addRidgesCheckBox;
/*      */   @FXML
/*      */   private CheckBox ridgeCenterCheckBox;
/*      */   @FXML
/*      */   private CheckBox ridgeAlternateCheckBox;
/*      */   @FXML
/*      */   private CheckBox addBevelCheckBox;
/*      */   @FXML
/*      */   private CheckBox useClutchCheckBox;
/*      */   @FXML
/*      */   private CheckBox useHexCheckBox;
/*      */   @FXML
/*      */   private CheckBox addHexHandleCheckBox;
/*      */   @FXML
/*      */   private Label progressLabel;
/*      */   @FXML
/*      */   private Label generateLabel1;
/*      */   @FXML
/*      */   private Label generateLabel2;
/*      */   @FXML
/*      */   private TextField projectPathTextField;
/*      */   @FXML
/*      */   private TextField openSCADPathTextField;
/*      */   @FXML
/*      */   private TextField descriptionTextField;
/*      */   @FXML
/*      */   private Spinner<Double> caliberSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> rifleHoleWidthSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> ridgeHeightSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> ridgeLengthSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> bevelSizeSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> holeMultiplierSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> hexHandleHeightSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> hxwSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> shaftHoleSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> shaftSlotLengthSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> shaftSlotWidthSpinner;
/*      */   @FXML
/*      */   private Spinner<Integer> fnSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> plateHeightSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> plateDiameterSpinner;
/*      */   @FXML
/*      */   private Spinner<Double> bulletCaliberSpinner;
/*      */   @FXML
/*      */   private Button previewButton;
/*      */   @FXML
/*      */   private Button stlStartButton;
/*      */   @FXML
/*      */   private Button projectPathChangeButton;
/*      */   @FXML
/*      */   private Button zipStartButton;
/*      */   @FXML
/*      */   private ProgressBar partsProgressBar;
/*      */   @FXML
/*      */   private ProgressIndicator platesProgressBar;
/*      */   @FXML
/*      */   private CheckBox mongoCheckBox;
/*      */   @FXML
/*      */   private CheckBox baseDown57CheckBox;
/*      */   @FXML
/*      */   private CheckBox app44BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app357BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app57BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app65BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app270BrassCheckBox;
/*      */   @FXML
/*      */   private CheckBox app762BrassCheckBox;
/*      */   @FXML
/*      */   private RadioButton verticalCircularPostRadioButton;
/*      */   @FXML
/*      */   private RadioButton verticalSquarePostRadioButton;
/*      */   @FXML
/*      */   private RadioButton horizontalSquarePostRadioButton;
/*      */   @FXML
/*      */   private CheckBox app300BrassCheckBox;
/*      */   
/*      */   public void initialize(URL url, ResourceBundle rb) {
/*  342 */     this.projectPathTextField.setText(getProjectPath());
/*  343 */     this.openSCADPathTextField.setText(getOpenSCADpath());
/*  344 */     this.tempOpenSCADPath = getOpenSCADfile();
/*      */ 
/*      */ 
/*      */     
/*  348 */     this.mongoCheckBox.setOnAction(event -> {
/*      */           if (this.mongoCheckBox.isSelected()) {
/*      */             this.etzgmp38RadioButton.setSelected(true);
/*      */             
/*      */             this.jgy370RadioButton.setDisable(true);
/*      */             
/*      */             this.verticalCircularPostRadioButton.setSelected(true);
/*      */             this.dillonRadioButton.setDisable(true);
/*      */             this.baseUp9CheckBox.setSelected(false);
/*      */             this.baseUp40CheckBox.setSelected(false);
/*      */             this.baseUp45CheckBox.setSelected(false);
/*      */             this.baseUp9CheckBox.setDisable(true);
/*      */             this.baseUp40CheckBox.setDisable(true);
/*      */             this.baseUp45CheckBox.setDisable(true);
/*      */             this.baseDown57CheckBox.setSelected(false);
/*      */             this.baseDown300CheckBox.setSelected(false);
/*      */             this.baseDown57CheckBox.setDisable(true);
/*      */             this.baseDown300CheckBox.setDisable(true);
/*      */             this.noseUp9CheckBox.setSelected(false);
/*      */             this.noseUp40CheckBox.setSelected(false);
/*      */             this.noseUp45CheckBox.setSelected(false);
/*      */             this.noseUp223CheckBox.setSelected(false);
/*      */             this.noseUp300CheckBox.setSelected(false);
/*      */             this.noseUp308CheckBox.setSelected(false);
/*      */             this.noseDown9CheckBox.setSelected(false);
/*      */             this.noseDown40CheckBox.setSelected(false);
/*      */             this.noseDown45CheckBox.setSelected(false);
/*      */             this.noseDown223CheckBox.setSelected(false);
/*      */             this.noseDown300CheckBox.setSelected(false);
/*      */             this.noseDown308CheckBox.setSelected(false);
/*      */             this.noseUp9CheckBox.setDisable(true);
/*      */             this.noseUp40CheckBox.setDisable(true);
/*      */             this.noseUp45CheckBox.setDisable(true);
/*      */             this.noseUp223CheckBox.setDisable(true);
/*      */             this.noseUp300CheckBox.setDisable(true);
/*      */             this.noseUp308CheckBox.setDisable(true);
/*      */             this.noseDown9CheckBox.setDisable(true);
/*      */             this.noseDown40CheckBox.setDisable(true);
/*      */             this.noseDown45CheckBox.setDisable(true);
/*      */             this.noseDown223CheckBox.setDisable(true);
/*      */             this.noseDown300CheckBox.setDisable(true);
/*      */             this.noseDown308CheckBox.setDisable(true);
/*      */           } else {
/*      */             this.jgy370RadioButton.setDisable(false);
/*      */             this.dillonRadioButton.setDisable(false);
/*      */             this.baseUp9CheckBox.setDisable(false);
/*      */             this.baseUp40CheckBox.setDisable(false);
/*      */             this.baseUp45CheckBox.setDisable(false);
/*      */             this.baseDown57CheckBox.setDisable(false);
/*      */             this.baseDown300CheckBox.setDisable(false);
/*      */             this.noseUp9CheckBox.setDisable(false);
/*      */             this.noseUp40CheckBox.setDisable(false);
/*      */             this.noseUp45CheckBox.setDisable(false);
/*      */             this.noseUp223CheckBox.setDisable(false);
/*      */             this.noseUp300CheckBox.setDisable(false);
/*      */             this.noseUp308CheckBox.setDisable(false);
/*      */             this.noseDown9CheckBox.setDisable(false);
/*      */             this.noseDown40CheckBox.setDisable(false);
/*      */             this.noseDown45CheckBox.setDisable(false);
/*      */             this.noseDown223CheckBox.setDisable(false);
/*      */             this.noseDown300CheckBox.setDisable(false);
/*      */             this.noseDown308CheckBox.setDisable(false);
/*      */           } 
/*      */         });
/*  412 */     this.descriptionTextField.setText(CollatorPlates.BASE_DOWN_BRASS_9.getDescription());
/*  413 */     this.caliberSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getCaliber(), 0.1D));
/*  414 */     this.plateHeightSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getPlateHeight(), 0.1D));
/*  415 */     this.isLongRifleBulletCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isIsLongRifleBullet());
/*  416 */     this.addRampsCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddRamps());
/*  417 */     this.isRifleBrassCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isIsRifleBrass());
/*  418 */     this.rifleHoleWidthSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getRifleHoleWidth(), 0.1D));
/*  419 */     this.addPivotsCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddPivots());
/*  420 */     this.addSlidesCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddSlides());
/*  421 */     this.addRidgesCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddRidges());
/*  422 */     this.ridgeCenterCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isRidgeCenter());
/*  423 */     this.ridgeAlternateCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isRidgeAlternate());
/*  424 */     this.ridgeHeightSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getRidgeHeight(), 0.1D));
/*  425 */     this.ridgeLengthSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getRidgeLength(), 0.1D));
/*  426 */     this.addBevelCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddBevel());
/*  427 */     this.bevelSizeSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getBevelSize(), 0.1D));
/*  428 */     this.holeMultiplierSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getHoleMultiplier(), 0.1D));
/*  429 */     this.useClutchCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isUseClutch());
/*  430 */     this.useHexCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isUseHex());
/*  431 */     this.addHexHandleCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddHexHandle());
/*  432 */     this.hexHandleHeightSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getHexHandleHeight(), 0.1D));
/*  433 */     this.hxwSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getHxw(), 0.1D));
/*  434 */     this.shaftHoleSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getShaftHole(), 0.1D));
/*  435 */     this.shaftSlotLengthSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getShaftSlotLength(), 0.1D));
/*  436 */     this.shaftSlotWidthSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getShaftSlotWidth(), 0.1D));
/*  437 */     this.fnSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, CollatorPlates.BASE_DOWN_BRASS_9.getFn(), 1));
/*  438 */     this.plateDiameterSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 200.0D, CollatorPlates.BASE_DOWN_BRASS_9.getPlateDiameter(), 0.1D));
/*  439 */     this.bulletCaliberSpinner.setValueFactory((SpinnerValueFactory)new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getBulletCaliber(), 0.1D));
/*      */     
/*  441 */     this.useHexCheckBox.setOnAction(event -> {
/*      */           if (this.useHexCheckBox.isSelected()) {
/*      */             this.addHexHandleCheckBox.setDisable(false);
/*      */             this.hexHandleHeightSpinner.setDisable(false);
/*      */             this.hxwSpinner.setDisable(false);
/*      */             this.shaftHoleSpinner.setDisable(true);
/*      */             this.shaftSlotLengthSpinner.setDisable(true);
/*      */             this.shaftSlotWidthSpinner.setDisable(true);
/*      */           } else {
/*      */             this.addHexHandleCheckBox.setDisable(true);
/*      */             this.hexHandleHeightSpinner.setDisable(true);
/*      */             this.hxwSpinner.setDisable(true);
/*      */             this.shaftHoleSpinner.setDisable(false);
/*      */             this.shaftSlotLengthSpinner.setDisable(false);
/*      */             this.shaftSlotWidthSpinner.setDisable(false);
/*      */           } 
/*      */         });
/*  458 */     this.collatorSensorCheckBox.setOnAction(event -> {
/*      */           if (this.collatorSensorCheckBox.isSelected()) {
/*      */             this.switchRadioButton.setDisable(true);
/*      */             
/*      */             this.photosensorRadioButton.setSelected(true);
/*      */           } else {
/*      */             this.switchRadioButton.setDisable(false);
/*      */             this.switchRadioButton.setSelected(true);
/*      */           } 
/*      */         });
/*  468 */     this.baseUpRadioButton.setOnAction(event -> {
/*      */           this.caliberSmallPistolRadioButton.setSelected(true);
/*      */           this.caliberSmallPistolRadioButton.fireEvent((Event)new ActionEvent());
/*      */           this.caliber9RadioButton.setDisable(true);
/*      */           this.caliber40RadioButton.setDisable(true);
/*      */           this.caliber45RadioButton.setDisable(true);
/*      */           this.caliber50RadioButton.setDisable(true);
/*      */           this.caliberSmallPistolRadioButton.setDisable(false);
/*      */           this.caliberLargePistolRadioButton.setDisable(false);
/*      */           this.caliber223RadioButton.setDisable(true);
/*      */           this.caliber65RadioButton.setDisable(true);
/*      */           this.caliber300RadioButton.setDisable(true);
/*      */           this.caliber308RadioButton.setDisable(true);
/*      */           this.caliber4570RadioButton.setDisable(true);
/*      */           this.caliberSmallRifleRadioButton.setDisable(true);
/*      */           this.caliberLargeRifleRadioButton.setDisable(true);
/*      */           this.caliberLongRifleRadioButton.setDisable(true);
/*      */         });
/*  486 */     this.baseDownRadioButton.setOnAction(event -> {
/*      */           this.caliber9RadioButton.setSelected(true);
/*      */           this.caliber9RadioButton.fireEvent((Event)new ActionEvent());
/*      */           this.caliber9RadioButton.setDisable(false);
/*      */           this.caliber40RadioButton.setDisable(false);
/*      */           this.caliber45RadioButton.setDisable(false);
/*      */           this.caliber50RadioButton.setDisable(false);
/*      */           this.caliberSmallPistolRadioButton.setDisable(false);
/*      */           this.caliberLargePistolRadioButton.setDisable(false);
/*      */           this.caliber223RadioButton.setDisable(false);
/*      */           this.caliber65RadioButton.setDisable(true);
/*      */           this.caliber300RadioButton.setDisable(false);
/*      */           this.caliber308RadioButton.setDisable(false);
/*      */           this.caliber4570RadioButton.setDisable(false);
/*      */           this.caliberSmallRifleRadioButton.setDisable(false);
/*      */           this.caliberLargeRifleRadioButton.setDisable(false);
/*      */           this.caliberLongRifleRadioButton.setDisable(true);
/*      */         });
/*  504 */     this.noseUpDownRadioButton.setOnAction(event -> {
/*      */           this.caliber9RadioButton.setSelected(true);
/*      */           this.caliber9RadioButton.fireEvent((Event)new ActionEvent());
/*      */           this.caliber9RadioButton.setDisable(false);
/*      */           this.caliber40RadioButton.setDisable(false);
/*      */           this.caliber45RadioButton.setDisable(false);
/*      */           this.caliber50RadioButton.setDisable(false);
/*      */           this.caliberSmallPistolRadioButton.setDisable(false);
/*      */           this.caliberLargePistolRadioButton.setDisable(false);
/*      */           this.caliber223RadioButton.setDisable(false);
/*      */           this.caliber65RadioButton.setDisable(false);
/*      */           this.caliber300RadioButton.setDisable(false);
/*      */           this.caliber308RadioButton.setDisable(false);
/*      */           this.caliber4570RadioButton.setDisable(true);
/*      */           this.caliberSmallRifleRadioButton.setDisable(false);
/*      */           this.caliberLargeRifleRadioButton.setDisable(false);
/*      */           this.caliberLongRifleRadioButton.setDisable(false);
/*      */         });
/*  522 */     this.noseUpDownRadioButton.fireEvent((Event)new ActionEvent());
/*  523 */     this.useHexCheckBox.fireEvent((Event)new ActionEvent());
/*  524 */     this.platesProgressBar.setVisible(false);
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void getNewFolderPath(ActionEvent event) {
/*  529 */     DirectoryChooser dc = new DirectoryChooser();
/*      */     
/*  531 */     dc.setInitialDirectory(new File(System.getProperty("user.dir")));
/*  532 */     if (event.getSource() == this.projectPathChangeButton) {
/*  533 */       this.projectPathTextField.setText(dc.showDialog(null).getAbsolutePath());
/*      */     } else {
/*  535 */       this.openSCADPathTextField.setText(dc.showDialog(null).getAbsolutePath());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void getMainBodyParts() {
/*  541 */     if (this.etzgmp38RadioButton.isSelected()) {
/*  542 */       if (this.mongoCheckBox.isSelected()) {
/*  543 */         this.PARTS.add(Parts.MAIN_BODY_ETZGMP38_MONGO);
/*  544 */         this.PARTS.add(Parts.RAMP_MONGO);
/*      */       } else {
/*  546 */         this.PARTS.add(Parts.MAIN_BODY_ETZGMP38);
/*  547 */         this.PARTS.add(Parts.RAMP);
/*      */       } 
/*  549 */       this.PARTS.add(Parts.FLIPPER);
/*  550 */       this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
/*      */     } 
/*  552 */     if (this.fc555RadioButton.isSelected()) {
/*  553 */       if (this.mongoCheckBox.isSelected()) {
/*  554 */         this.PARTS.add(Parts.MAIN_BODY_FC555_MONGO);
/*  555 */         this.PARTS.add(Parts.RAMP_MONGO);
/*      */       } else {
/*  557 */         this.PARTS.add(Parts.MAIN_BODY_FC555);
/*  558 */         this.PARTS.add(Parts.RAMP);
/*      */       } 
/*  560 */       this.PARTS.add(Parts.FLIPPER);
/*  561 */       this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
/*      */     } 
/*  563 */     if (this.jgy370RadioButton.isSelected()) {
/*  564 */       this.PARTS.add(Parts.MAIN_BODY_JGY370);
/*  565 */       this.PARTS.add(Parts.RAMP);
/*  566 */       this.PARTS.add(Parts.FLIPPER);
/*  567 */       this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
/*      */     } 
/*  569 */     if (this.m634jsRadioButton.isSelected()) {
/*  570 */       if (this.mongoCheckBox.isSelected()) {
/*  571 */         this.PARTS.add(Parts.MAIN_BODY_M634JS_MONGO);
/*  572 */         this.PARTS.add(Parts.RAMP_MONGO);
/*      */       } else {
/*  574 */         this.PARTS.add(Parts.MAIN_BODY_M634JS);
/*  575 */         this.PARTS.add(Parts.RAMP);
/*      */       } 
/*  577 */       this.PARTS.add(Parts.FLIPPER);
/*  578 */       this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
/*      */     } 
/*      */     
/*  581 */     if (this.smallElectronicsRadioButton.isSelected()) {
/*  582 */       if (this.templateCheckBox.isSelected()) {
/*  583 */         this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL_TEMPLATE);
/*      */       } else {
/*  585 */         this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL);
/*      */       } 
/*  587 */       this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL_LID);
/*      */     } 
/*  589 */     if (this.largeElectronicsRadioButton.isSelected()) {
/*  590 */       if (this.templateCheckBox.isSelected()) {
/*  591 */         this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE_TEMPLATE);
/*      */       } else {
/*  593 */         this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE);
/*      */       } 
/*  595 */       this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE_LID);
/*      */     } 
/*      */     
/*  598 */     if (this.wallExtenderCheckBox.isSelected()) {
/*  599 */       if (this.mongoCheckBox.isSelected()) {
/*  600 */         this.PARTS.add(Parts.MAIN_BODY_WALL_EXTENDER_MONGO);
/*      */       } else {
/*  602 */         this.PARTS.add(Parts.MAIN_BODY_WALL_EXTENDER);
/*      */       } 
/*      */     }
/*  605 */     if (this.slidePlateLatchCheckBox.isSelected()) {
/*  606 */       this.PARTS.add(Parts.SLIDE_PLATE_LATCH);
/*      */     }
/*      */     
/*  609 */     if (isNoseDown() || isBaseUp() || isBaseDown()) {
/*  610 */       this.PARTS.add(Parts.DROP_HOLE_PLUG);
/*      */     }
/*  612 */     if (isBaseUp()) {
/*  613 */       this.PARTS.add(Parts.RAMP_BRASS_BASE_UP);
/*      */     }
/*  615 */     if (isBaseUp() || this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected()) {
/*  616 */       this.PARTS.add(Parts.SWEEPER);
/*      */     }
/*  618 */     if (this.baseDown223CheckBox.isSelected() || this.baseDown308CheckBox.isSelected() || this.baseDown300CheckBox.isSelected()) {
/*  619 */       this.PARTS.add(Parts.SWEEPER_LONG);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void getMountParts() {
/*  625 */     if (this.dillonRadioButton.isSelected()) {
/*  626 */       this.PARTS.add(Parts.MOUNT_DILLON_HANG);
/*  627 */       this.PARTS.add(Parts.MOUNT_DILLON_HANG_BASE);
/*      */     } 
/*      */     
/*  630 */     if (this.verticalCircularPostRadioButton.isSelected() || this.verticalSquarePostRadioButton.isSelected() || this.horizontalSquarePostRadioButton.isSelected()) {
/*  631 */       if (this.mongoCheckBox.isSelected()) {
/*  632 */         this.PARTS.add(Parts.MOUNT_POST_MONGO);
/*      */       } else {
/*  634 */         this.PARTS.add(Parts.MOUNT_POST);
/*      */       } 
/*  636 */       if (this.verticalCircularPostRadioButton.isSelected()) {
/*  637 */         this.PARTS.add(Parts.MOUNT_POST_BASE);
/*  638 */       } else if (this.verticalSquarePostRadioButton.isSelected()) {
/*  639 */         this.PARTS.add(Parts.MOUNT_POST_BASE_SQUARE);
/*      */       } else {
/*  641 */         this.PARTS.add(Parts.MOUNT_SQUARE_TUBE);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void getCollatorPlateParts() {
/*  648 */     if (this.noseUp9CheckBox.isSelected() || this.noseUp40CheckBox.isSelected() || this.noseDown9CheckBox.isSelected() || this.noseDown40CheckBox.isSelected()) {
/*  649 */       this.PARTS.add(Parts.PISTOL_BULLET_COLLATOR_PLATE_SMALL_5);
/*      */     }
/*  651 */     if (this.noseUp45CheckBox.isSelected() || this.noseDown45CheckBox.isSelected()) {
/*  652 */       this.PARTS.add(Parts.PISTOL_BULLET_COLLATOR_PLATE_LARGE_7);
/*      */     }
/*  654 */     if (this.noseUp223CheckBox.isSelected() || this.noseDown223CheckBox.isSelected()) {
/*  655 */       this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_SMALL_2);
/*      */     }
/*  657 */     if (this.noseUp308CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
/*  658 */       this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_LARGE_4);
/*      */     }
/*  660 */     if (this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected()) {
/*  661 */       this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_LONG_11);
/*      */     }
/*      */     
/*  664 */     if (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected()) {
/*  665 */       this.PARTS.add(Parts.PISTOL_BRASS_BASE_UP_COLLATOR_PLATE_SMALL);
/*      */     }
/*  667 */     if (this.baseUp45CheckBox.isSelected()) {
/*  668 */       this.PARTS.add(Parts.PISTOL_BRASS_BASE_UP_COLLATOR_PLATE_LARGE);
/*      */     }
/*      */     
/*  671 */     if (this.baseDown9CheckBox.isSelected() || this.baseDown40CheckBox.isSelected()) {
/*  672 */       if (this.mongoCheckBox.isSelected()) {
/*  673 */         this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_SMALL_MONGO);
/*      */       } else {
/*  675 */         this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_SMALL);
/*      */       } 
/*      */     }
/*  678 */     if (this.baseDown45CheckBox.isSelected()) {
/*  679 */       if (this.mongoCheckBox.isSelected()) {
/*  680 */         this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_LARGE_MONGO);
/*      */       } else {
/*  682 */         this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_LARGE);
/*      */       } 
/*      */     }
/*  685 */     if (this.baseDown223CheckBox.isSelected()) {
/*  686 */       if (this.mongoCheckBox.isSelected()) {
/*  687 */         this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_SMALL_MONGO);
/*      */       } else {
/*  689 */         this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_SMALL);
/*      */       } 
/*      */     }
/*  692 */     if (this.baseDown308CheckBox.isSelected()) {
/*  693 */       if (this.mongoCheckBox.isSelected()) {
/*  694 */         this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_LARGE_MONGO);
/*      */       } else {
/*  696 */         this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_LARGE);
/*      */       } 
/*      */     }
/*  699 */     if (this.baseDown57CheckBox.isSelected()) {
/*  700 */       this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_57);
/*      */     }
/*  702 */     if (this.baseDown300CheckBox.isSelected()) {
/*  703 */       this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_300BO);
/*      */     }
/*  705 */     if (this.pinnedShaftRadioButton.isSelected()) {
/*  706 */       this.PARTS.add(Parts.SLIP_CLUTCH_PINNED);
/*  707 */       this.PARTS.add(Parts.SLIP_CLUTCH_RING);
/*      */     } 
/*  709 */     if (this.hexCouplerRadioButton.isSelected()) {
/*  710 */       this.PARTS.add(Parts.SLIP_CLUTCH_HEX);
/*  711 */       this.PARTS.add(Parts.SLIP_CLUTCH_RING);
/*      */     } 
/*  713 */     if (this.clutchCoverCheckBox.isSelected()) {
/*  714 */       this.PARTS.add(Parts.SLIP_CLUTCH_COVER);
/*      */     }
/*  716 */     if (this.collatorHandleCheckBox.isSelected()) {
/*  717 */       this.PARTS.add(Parts.COLLATOR_PLATE_HANDLE);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void getSlidePlateParts() {
/*  723 */     if (this.noseUp9CheckBox.isSelected() || this.noseUp40CheckBox.isSelected()) {
/*  724 */       this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_5);
/*      */     }
/*  726 */     if (this.noseUp45CheckBox.isSelected()) {
/*  727 */       this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_7);
/*      */     }
/*  729 */     if (this.noseUp223CheckBox.isSelected()) {
/*  730 */       this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_2);
/*      */     }
/*  732 */     if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected()) {
/*  733 */       this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_11);
/*      */     }
/*      */     
/*  736 */     if (this.noseDown9CheckBox.isSelected() || this.noseDown40CheckBox.isSelected()) {
/*  737 */       this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_5);
/*      */     }
/*  739 */     if (this.noseDown45CheckBox.isSelected()) {
/*  740 */       this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_7);
/*      */     }
/*  742 */     if (this.noseDown223CheckBox.isSelected()) {
/*  743 */       this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_2);
/*      */     }
/*  745 */     if (this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
/*  746 */       this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_11);
/*      */     }
/*      */     
/*  749 */     if (isBaseUp()) {
/*  750 */       this.PARTS.add(Parts.BRASS_BASE_UP_DROP_HOLE_ADAPTER);
/*  751 */       if (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected()) {
/*  752 */         this.PARTS.add(Parts.BRASS_BASE_UP_SLIDE_PLATE_SMALL);
/*      */       }
/*  754 */       if (this.baseUp45CheckBox.isSelected()) {
/*  755 */         this.PARTS.add(Parts.BRASS_BASE_UP_SLIDE_PLATE_LARGE);
/*      */       }
/*      */     } 
/*      */     
/*  759 */     if (isBaseDown()) {
/*  760 */       this.PARTS.add(Parts.BRASS_DROP_HOLE_ADAPTER);
/*  761 */       this.PARTS.add(Parts.BRASS_SLIDE_ADJUSTER);
/*  762 */       if (this.mongoCheckBox.isSelected()) {
/*  763 */         this.PARTS.add(Parts.BRASS_SLIDE_PLATE_MONGO);
/*      */       } else {
/*  765 */         this.PARTS.add(Parts.BRASS_SLIDE_PLATE);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void getAdapterParts() {
/*  771 */     if (this.adapter7.isSelected()) {
/*  772 */       this.PARTS.add(Parts.SPRING_ADAPTER_7);
/*  773 */       this.PARTS.add(Parts.SPRING_ADAPTER_7_DT);
/*      */     } 
/*  775 */     if (this.adapter8.isSelected()) {
/*  776 */       if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected() || this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
/*  777 */         this.PARTS.add(Parts.SPRING_ADAPTER_8_FLUSH);
/*      */       } else {
/*  779 */         this.PARTS.add(Parts.SPRING_ADAPTER_8);
/*      */       } 
/*  781 */       this.PARTS.add(Parts.SPRING_ADAPTER_8);
/*      */     } 
/*  783 */     if (this.adapter9.isSelected()) {
/*  784 */       this.PARTS.add(Parts.SPRING_ADAPTER_9);
/*  785 */       this.PARTS.add(Parts.SPRING_ADAPTER_9_DT);
/*      */     } 
/*  787 */     if (this.adapter10.isSelected()) {
/*  788 */       if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected() || this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
/*  789 */         this.PARTS.add(Parts.SPRING_ADAPTER_10_FLUSH);
/*      */       } else {
/*  791 */         this.PARTS.add(Parts.SPRING_ADAPTER_10);
/*      */       } 
/*  793 */       this.PARTS.add(Parts.SPRING_ADAPTER_10_DT);
/*      */     } 
/*  795 */     if (this.adapter11.isSelected()) {
/*  796 */       this.PARTS.add(Parts.SPRING_ADAPTER_11);
/*  797 */       this.PARTS.add(Parts.SPRING_ADAPTER_11_DT);
/*      */     } 
/*  799 */     if (this.adapter12.isSelected()) {
/*  800 */       this.PARTS.add(Parts.SPRING_ADAPTER_12);
/*  801 */       this.PARTS.add(Parts.SPRING_ADAPTER_12_DT);
/*      */     } 
/*  803 */     if (this.adapter13.isSelected()) {
/*  804 */       this.PARTS.add(Parts.SPRING_ADAPTER_13);
/*  805 */       this.PARTS.add(Parts.SPRING_ADAPTER_13_DT);
/*      */     } 
/*  807 */     if (this.adapter14.isSelected()) {
/*  808 */       this.PARTS.add(Parts.SPRING_ADAPTER_14);
/*  809 */       this.PARTS.add(Parts.SPRING_ADAPTER_14_DT);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDropTubeParts() {
/*  822 */     if (this.proximityRadioButton.isSelected()) {
/*  823 */       this.PARTS.add(Parts.DROP_TUBE_PROXIMITY_HOUSING);
/*  824 */       this.PARTS.add(Parts.DROP_TUBE_PROXIMITY_THREAD_INSERT);
/*      */     } 
/*  826 */     if (this.photosensorRadioButton.isSelected()) {
/*  827 */       this.PARTS.add(Parts.DROP_TUBE_LED_SENSOR_KNOB);
/*      */     }
/*  829 */     if (this.noseUp223CheckBox.isSelected() || this.noseDown223CheckBox.isSelected()) {
/*  830 */       if (this.switchRadioButton.isSelected()) {
/*  831 */         this.PARTS.add(Parts.SWITCH_DROP_TUBE_6);
/*  832 */       } else if (this.collatorSensorCheckBox.isSelected()) {
/*  833 */         this.PARTS.add(Parts.DROP_TUBE_ALT_6);
/*  834 */         this.PARTS.add(Parts.SPRING_DROP_TUBE_SMALL);
/*      */       } else {
/*  836 */         this.PARTS.add(Parts.DROP_TUBE_6);
/*      */       } 
/*      */     }
/*  839 */     if (this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected() || this.noseUp308CheckBox
/*  840 */       .isSelected() || this.noseDown308CheckBox.isSelected()) {
/*  841 */       if (this.switchRadioButton.isSelected()) {
/*  842 */         this.PARTS.add(Parts.SWITCH_DROP_TUBE_8);
/*  843 */       } else if (this.collatorSensorCheckBox.isSelected()) {
/*  844 */         this.PARTS.add(Parts.DROP_TUBE_ALT_8);
/*  845 */         this.PARTS.add(Parts.SPRING_DROP_TUBE_MEDIUM);
/*      */       } else {
/*  847 */         this.PARTS.add(Parts.DROP_TUBE_8);
/*      */       } 
/*      */     }
/*  850 */     if (this.noseUp9CheckBox.isSelected() || this.noseDown9CheckBox.isSelected() || this.baseUp9CheckBox.isSelected() || this.baseDown9CheckBox.isSelected() || this.noseUp40CheckBox
/*  851 */       .isSelected() || this.noseDown40CheckBox.isSelected() || this.baseDown223CheckBox
/*  852 */       .isSelected() || this.baseDown308CheckBox
/*  853 */       .isSelected()) {
/*  854 */       if (this.switchRadioButton.isSelected()) {
/*  855 */         this.PARTS.add(Parts.SWITCH_DROP_TUBE_10);
/*  856 */       } else if (this.collatorSensorCheckBox.isSelected()) {
/*  857 */         this.PARTS.add(Parts.DROP_TUBE_ALT_10);
/*  858 */         this.PARTS.add(Parts.SPRING_DROP_TUBE_MEDIUM);
/*      */       } else {
/*  860 */         this.PARTS.add(Parts.DROP_TUBE_10);
/*      */       } 
/*      */     }
/*  863 */     if (this.baseUp40CheckBox.isSelected() || this.baseDown40CheckBox.isSelected()) {
/*  864 */       if (this.switchRadioButton.isSelected()) {
/*  865 */         this.PARTS.add(Parts.SWITCH_DROP_TUBE_11);
/*  866 */       } else if (this.collatorSensorCheckBox.isSelected()) {
/*  867 */         this.PARTS.add(Parts.DROP_TUBE_ALT_11);
/*  868 */         this.PARTS.add(Parts.SPRING_DROP_TUBE_LARGE);
/*      */       } else {
/*  870 */         this.PARTS.add(Parts.DROP_TUBE_11);
/*      */       } 
/*      */     }
/*  873 */     if (this.noseUp45CheckBox.isSelected() || this.noseDown45CheckBox.isSelected() || this.baseUp45CheckBox.isSelected() || this.baseDown45CheckBox.isSelected() || this.baseDown308CheckBox
/*  874 */       .isSelected()) {
/*  875 */       if (this.switchRadioButton.isSelected()) {
/*  876 */         this.PARTS.add(Parts.SWITCH_DROP_TUBE_13);
/*  877 */       } else if (this.collatorSensorCheckBox.isSelected()) {
/*  878 */         this.PARTS.add(Parts.DROP_TUBE_ALT_13);
/*  879 */         this.PARTS.add(Parts.SPRING_DROP_TUBE_LARGE);
/*      */       } else {
/*  881 */         this.PARTS.add(Parts.DROP_TUBE_13);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void getBulletFeedDieParts() {
/*  887 */     if (this.bulletFeedDie7CheckBox.isSelected()) {
/*  888 */       this.PARTS.add(Parts.BULLET_FEED_DIE_7);
/*      */     }
/*  890 */     if (this.bulletFeedDie9CheckBox.isSelected()) {
/*  891 */       this.PARTS.add(Parts.BULLET_FEED_DIE_9);
/*      */     }
/*  893 */     if (this.bulletFeedDie32CheckBox.isSelected()) {
/*  894 */       this.PARTS.add(Parts.BULLET_FEED_DIE_32);
/*      */     }
/*  896 */     if (this.bulletFeedDie40CheckBox.isSelected()) {
/*  897 */       this.PARTS.add(Parts.BULLET_FEED_DIE_40);
/*      */     }
/*  899 */     if (this.bulletFeedDie44CheckBox.isSelected()) {
/*  900 */       this.PARTS.add(Parts.BULLET_FEED_DIE_44);
/*      */     }
/*  902 */     if (this.bulletFeedDie45CheckBox.isSelected()) {
/*  903 */       this.PARTS.add(Parts.BULLET_FEED_DIE_45);
/*      */     }
/*  905 */     if (this.bulletFeedDie223CheckBox.isSelected()) {
/*  906 */       this.PARTS.add(Parts.BULLET_FEED_DIE_223);
/*      */     }
/*  908 */     if (this.bulletFeedDie300CheckBox.isSelected()) {
/*  909 */       this.PARTS.add(Parts.BULLET_FEED_DIE_300);
/*      */     }
/*  911 */     if (this.bulletFeedDie308CheckBox.isSelected()) {
/*  912 */       this.PARTS.add(Parts.BULLET_FEED_DIE_308);
/*      */     }
/*  914 */     if (this.bulletFeedDie4570CheckBox.isSelected()) {
/*  915 */       this.PARTS.add(Parts.BULLET_FEED_DIE_4570);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void getAPPParts() {
/*  921 */     if (this.app9BrassCheckBox.isSelected()) {
/*  922 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  923 */       this.PARTS.add(Parts.APP_BRASS_INSERT_9);
/*  924 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  926 */     if (this.app32BrassCheckBox.isSelected()) {
/*  927 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  928 */       this.PARTS.add(Parts.APP_BRASS_INSERT_32);
/*  929 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  931 */     if (this.app357BrassCheckBox.isSelected()) {
/*  932 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  933 */       this.PARTS.add(Parts.APP_BRASS_INSERT_357);
/*  934 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  936 */     if (this.app40BrassCheckBox.isSelected()) {
/*  937 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  938 */       this.PARTS.add(Parts.APP_BRASS_INSERT_40);
/*  939 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  941 */     if (this.app44BrassCheckBox.isSelected()) {
/*  942 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  943 */       this.PARTS.add(Parts.APP_BRASS_INSERT_44);
/*  944 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  946 */     if (this.app45BrassCheckBox.isSelected()) {
/*  947 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  948 */       this.PARTS.add(Parts.APP_BRASS_INSERT_45);
/*  949 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  951 */     if (this.app57BrassCheckBox.isSelected()) {
/*  952 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  953 */       this.PARTS.add(Parts.APP_BRASS_INSERT_57);
/*  954 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  956 */     if (this.app65BrassCheckBox.isSelected()) {
/*  957 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  958 */       this.PARTS.add(Parts.APP_BRASS_INSERT_65);
/*  959 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  961 */     if (this.app762BrassCheckBox.isSelected()) {
/*  962 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  963 */       this.PARTS.add(Parts.APP_BRASS_INSERT_762);
/*  964 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  966 */     if (this.app223BrassCheckBox.isSelected()) {
/*  967 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  968 */       this.PARTS.add(Parts.APP_BRASS_INSERT_223);
/*  969 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  971 */     if (this.app270BrassCheckBox.isSelected()) {
/*  972 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  973 */       this.PARTS.add(Parts.APP_BRASS_INSERT_270);
/*  974 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*  976 */     if (this.app308BrassCheckBox.isSelected()) {
/*  977 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  978 */       this.PARTS.add(Parts.APP_BRASS_INSERT_308);
/*  979 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/*      */     
/*  982 */     if (this.app9BulletCheckBox.isSelected()) {
/*  983 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  984 */       this.PARTS.add(Parts.APP_BULLET_INSERT_9);
/*  985 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_9);
/*      */     } 
/*  987 */     if (this.app32BulletCheckBox.isSelected()) {
/*  988 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  989 */       this.PARTS.add(Parts.APP_BULLET_INSERT_32);
/*  990 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_32);
/*      */     } 
/*  992 */     if (this.app40BulletCheckBox.isSelected()) {
/*  993 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  994 */       this.PARTS.add(Parts.APP_BULLET_INSERT_40);
/*  995 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_40);
/*      */     } 
/*  997 */     if (this.app45BulletCheckBox.isSelected()) {
/*  998 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/*  999 */       this.PARTS.add(Parts.APP_BULLET_INSERT_45);
/* 1000 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_45);
/*      */     } 
/* 1002 */     if (this.app223BulletCheckBox.isSelected()) {
/* 1003 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/* 1004 */       this.PARTS.add(Parts.APP_BULLET_INSERT_223);
/* 1005 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_223);
/*      */     } 
/* 1007 */     if (this.app300BrassCheckBox.isSelected()) {
/* 1008 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/* 1009 */       this.PARTS.add(Parts.APP_BRASS_INSERT_300);
/* 1010 */       this.PARTS.add(Parts.BASE_SLIDE);
/*      */     } 
/* 1012 */     if (this.app308BulletCheckBox.isSelected()) {
/* 1013 */       this.PARTS.add(Parts.APP_MAIN_BRACKET);
/* 1014 */       this.PARTS.add(Parts.APP_BULLET_INSERT_308);
/* 1015 */       this.PARTS.add(Parts.APP_BULLET_SLIDE_308);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isNoseDown() {
/* 1020 */     return (this.noseDown9CheckBox.isSelected() || this.noseDown40CheckBox.isSelected() || this.noseDown45CheckBox.isSelected() || this.noseDown223CheckBox
/* 1021 */       .isSelected() || this.noseDown308CheckBox.isSelected() || this.noseDown300CheckBox.isSelected());
/*      */   }
/*      */   
/*      */   private boolean isBaseUp() {
/* 1025 */     return (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected() || this.baseUp45CheckBox.isSelected());
/*      */   }
/*      */   
/*      */   private boolean isBaseDown() {
/* 1029 */     return (this.baseDown9CheckBox.isSelected() || this.baseDown40CheckBox.isSelected() || this.baseDown45CheckBox.isSelected() || this.baseDown223CheckBox
/* 1030 */       .isSelected() || this.baseDown308CheckBox.isSelected() || this.baseDown300CheckBox.isSelected());
/*      */   }
/*      */   
/*      */   private void getPDFFile() throws Exception {
/* 1034 */     Document pdfFile = new Document(new PdfDocument(new PdfWriter(this.projectPathTextField.getText() + "/Settings.pdf")));
/* 1035 */     int partNumber = 1;
/*      */ 
/*      */     
/* 1038 */     Table table = new Table(new float[] { 50.0F, 200.0F, 100.0F, 100.0F, 100.0F, 100.0F, 100.0F });
/* 1039 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("#")).setTextAlignment(TextAlignment.CENTER));
/* 1040 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Part Name")).setTextAlignment(TextAlignment.CENTER));
/* 1041 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Layer Height")).setTextAlignment(TextAlignment.CENTER));
/* 1042 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Infill")).setTextAlignment(TextAlignment.CENTER));
/* 1043 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Supports")).setTextAlignment(TextAlignment.CENTER));
/* 1044 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Walls")).setTextAlignment(TextAlignment.CENTER));
/* 1045 */     table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph("Adhesion")).setTextAlignment(TextAlignment.CENTER));
/*      */     
/* 1047 */     for (Parts part : this.PARTS) {
/*      */       
/* 1049 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(Integer.toString(partNumber++))).setTextAlignment(TextAlignment.CENTER));
/* 1050 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(part.getFile())).setTextAlignment(TextAlignment.LEFT));
/* 1051 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(Double.toString(part.getLayerHeight()))).setTextAlignment(TextAlignment.CENTER));
/* 1052 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(part.getInfill() + "%")).setTextAlignment(TextAlignment.CENTER));
/* 1053 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(part.isSupports() ? "60° Overhang" : "None")).setTextAlignment(TextAlignment.CENTER));
/* 1054 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(Integer.toString(part.getWalls()))).setTextAlignment(TextAlignment.CENTER));
/* 1055 */       table.addCell((Cell)(new Cell()).add((IBlockElement)new Paragraph(part.getAdhesion())).setTextAlignment(TextAlignment.CENTER));
/*      */     } 
/* 1057 */     pdfFile.add((IBlockElement)table);
/* 1058 */     pdfFile.close();
/*      */   }

            private Path findPartFile(Path projectRoot, Parts part) throws IOException {
                String expectedPath = part.getPath()
                    .replace('\\', '/')
                    .replaceFirst("^/+", "");

                String expectedName = Paths.get(expectedPath).getFileName().toString();

                try (Stream<Path> files = Files.walk(projectRoot)) {
                    // Prefer the original relative path, if it exists.
                    Path directMatch = projectRoot.resolve(expectedPath);
                    if (Files.isRegularFile(directMatch)) {
                        return directMatch;
                    }

                    // Otherwise search recursively by filename.
                    return files
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().equalsIgnoreCase(expectedName))
                        .findFirst()
                        .orElse(null);
                }
            }
/*      */   
@FXML
private void getZIPFile() {
        this.PARTS.clear();

        getMainBodyParts();
        getMountParts();
        getCollatorPlateParts();
        getSlidePlateParts();
        getAdapterParts();
        getDropTubeParts();
        getBulletFeedDieParts();
        getAPPParts();

        if (this.PARTS.isEmpty()) {
            this.progressLabel.setText("No parts selected.");
            return;
        }
    Task<Void> task = new Task<>() {
        @Override
        protected Void call() throws Exception {
            Path projectRoot = Paths.get(
                PartsWindowController.this.projectPathTextField.getText()
            ).toAbsolutePath().normalize();

            Path zipPath = projectRoot.resolve("Parts.zip");

            PartsWindowController.this.getPDFFile();

            try (ZipOutputStream zipOut = new ZipOutputStream(
                    new FileOutputStream(zipPath.toFile()))) {

                Path settingsPath = projectRoot.resolve("Settings.pdf");

                try (FileInputStream input =
                         new FileInputStream(settingsPath.toFile())) {
                    zipOut.putNextEntry(new ZipEntry("Settings.pdf"));
                    input.transferTo(zipOut);
                    zipOut.closeEntry();
                }

                Files.deleteIfExists(settingsPath);

                int number = 1;
                int total = PartsWindowController.this.PARTS.size();

                for (Parts part : PartsWindowController.this.PARTS) {
                    Path partPath = findPartFile(projectRoot, part);

                    if (partPath == null) {
                        updateMessage("File Not Found: " + part.getFile());
                        number++;
                        continue;
                    }

                    zipOut.putNextEntry(new ZipEntry(
                        projectRoot.relativize(partPath)
                            .toString()
                            .replace(File.separatorChar, '/')
                    ));

                    Files.copy(partPath, zipOut);
                    zipOut.closeEntry();

                    updateProgress(number++, total);
                    updateMessage("Adding: " + part.getFile());
                }
            }

            updateMessage("Done!");
            return null;
        }
    };

    partsProgressBar.progressProperty().bind(task.progressProperty());
    progressLabel.textProperty().bind(task.messageProperty());

    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
/*      */       } @FXML private void getSTLFile(ActionEvent event) { try {
/*      */       final LinkedList<String> command; Task<Void> task;
/*      */       Thread thread;
/*      */       FXMLLoader loader;
/*      */       Parent root;
/*      */       PreviewWindowController controller;
/*      */       Stage stage;
/* 1143 */       switch (((Button)event.getSource()).getText()) {
/*      */         
/*      */         case "Generate":
/* 1146 */           command = getPlateSettings();
/* 1147 */           command.addFirst(this.openSCADPathTextField.getText() + "/openscad");
/* 1148 */           command.addLast("-o");
/* 1149 */           command.addLast(this.projectPathTextField.getText() + "/" + this.descriptionTextField.getText() + ".stl");
/* 1150 */           command.addLast(this.tempOpenSCADPath);
/*      */           
/* 1152 */           this.previewButton.setDisable(true);
/* 1153 */           this.stlStartButton.setText("Cancel");
/* 1154 */           this.platesProgressBar.setVisible(true);
/* 1155 */           this.generateLabel1.setText("Generating plate");
/* 1156 */           this.generateLabel2.setText("Press Cancel to stop generation");
/* 1157 */           task = new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception
/*      */               {
/* 1161 */                 PartsWindowController.this.isCancelled = false;
/* 1162 */                 PartsWindowController.this.stlProcess = (new ProcessBuilder(command)).start();
/* 1163 */                 PartsWindowController.this.stlProcess.waitFor();
/* 1164 */                 (new MediaPlayer(new Media(PartsWindowController.class.getResource("ding.mp3").toString()))).play();
/* 1165 */                 Platform.runLater(() -> {
/*      */                       PartsWindowController.this.previewButton.setDisable(false);
/*      */                       PartsWindowController.this.stlStartButton.setText("Generate");
/*      */                       PartsWindowController.this.platesProgressBar.setVisible(false);
/*      */                       if (PartsWindowController.this.isCancelled) {
/*      */                         PartsWindowController.this.generateLabel1.setText("Plate generation cancelled!");
/*      */                         PartsWindowController.this.generateLabel2.setText("");
/*      */                       } else {
/*      */                         PartsWindowController.this.generateLabel1.setText("Plate generation completed!");
/*      */                         PartsWindowController.this.generateLabel2.setText(PartsWindowController.this.descriptionTextField.getText() + ".stl");
/*      */                       } 
/*      */                     });
/* 1177 */                 return null;
/*      */               }
/*      */             };
/* 1180 */           thread = new Thread((Runnable)task);
/* 1181 */           thread.setDaemon(true);
/* 1182 */           thread.start();
/*      */           break;
/*      */         case "Cancel":
/* 1185 */           this.isCancelled = true;
/* 1186 */           this.stlProcess.destroy();
/*      */           break;
/*      */         
/*      */         case "Preview":
/* 1190 */           loader = new FXMLLoader(getClass().getResource("PreviewWindow.fxml"));
/* 1191 */           root = (Parent)loader.load();
/* 1192 */           controller = (PreviewWindowController)loader.getController();
/*      */           
/* 1194 */           command = getPlateSettings();
/* 1195 */           command.addFirst(this.openSCADPathTextField.getText() + "/openscad");
/* 1196 */           command.addLast("--preview");
/* 1197 */           command.addLast("--imgsize=500,500");
/* 1198 */           command.addLast("-o");
/* 1199 */           command.addLast(this.projectPathTextField.getText() + "/preview.png");
/* 1200 */           command.addLast(this.tempOpenSCADPath);
/* 1201 */           this.stlProcess = (new ProcessBuilder(command)).start();
/* 1202 */           this.stlProcess.waitFor();
/*      */           
/* 1204 */           controller.setSTLPreview(this.projectPathTextField.getText());
/* 1205 */           stage = new Stage();
/* 1206 */           stage.setScene(new Scene(root));
/* 1207 */           stage.setTitle("Collator Plate Preview");
/* 1208 */           stage.setResizable(false);
/* 1209 */           stage.show();
/*      */           break;
/*      */       } 
/* 1212 */     } catch (Exception e) {
/* 1213 */       if (this.stlProcess.isAlive()) {
/* 1214 */         this.isCancelled = true;
/* 1215 */         this.stlProcess.destroy();
/*      */       } 
/*      */     }  }
/*      */ 
/*      */ 
/*      */   
/*      */   @FXML
/*      */   private void setPlateSettings(ActionEvent event) {
/*      */     CollatorPlates plate;
/* 1224 */     switch (((RadioButton)event.getSource()).getText()) {
/*      */       case "9mm":
/* 1226 */         if (this.baseDownRadioButton.isSelected()) {
/* 1227 */           plate = CollatorPlates.BASE_DOWN_BRASS_9; break;
/*      */         } 
/* 1229 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_9;
/*      */         break;
/*      */       
/*      */       case ".40 S&W":
/* 1233 */         if (this.baseDownRadioButton.isSelected()) {
/* 1234 */           plate = CollatorPlates.BASE_DOWN_BRASS_40; break;
/*      */         } 
/* 1236 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_40;
/*      */         break;
/*      */       
/*      */       case ".45 ACP":
/* 1240 */         if (this.baseDownRadioButton.isSelected()) {
/* 1241 */           plate = CollatorPlates.BASE_DOWN_BRASS_45; break;
/*      */         } 
/* 1243 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_45;
/*      */         break;
/*      */       
/*      */       case ".50":
/* 1247 */         if (this.baseDownRadioButton.isSelected()) {
/* 1248 */           plate = CollatorPlates.BASE_DOWN_BRASS_50; break;
/*      */         } 
/* 1250 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_50;
/*      */         break;
/*      */       
/*      */       case "Large Pistol":
/* 1254 */         if (this.baseUpRadioButton.isSelected()) {
/* 1255 */           plate = CollatorPlates.BASE_UP_BRASS_LARGE_PISTOL; break;
/* 1256 */         }  if (this.baseDownRadioButton.isSelected()) {
/* 1257 */           plate = CollatorPlates.BASE_DOWN_BRASS_LARGE_PISTOL; break;
/*      */         } 
/* 1259 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LARGE_PISTOL;
/*      */         break;
/*      */       
/*      */       case ".223":
/* 1263 */         if (this.baseDownRadioButton.isSelected()) {
/* 1264 */           plate = CollatorPlates.BASE_DOWN_BRASS_223; break;
/*      */         } 
/* 1266 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_223;
/*      */         break;
/*      */       
/*      */       case "6.5mm":
/* 1270 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_65;
/*      */         break;
/*      */       case "300 BLK":
/* 1273 */         if (this.baseDownRadioButton.isSelected()) {
/* 1274 */           plate = CollatorPlates.BASE_DOWN_BRASS_300; break;
/*      */         } 
/* 1276 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_300;
/*      */         break;
/*      */       
/*      */       case ".308":
/* 1280 */         if (this.baseDownRadioButton.isSelected()) {
/* 1281 */           plate = CollatorPlates.BASE_DOWN_BRASS_308; break;
/*      */         } 
/* 1283 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_308;
/*      */         break;
/*      */       
/*      */       case ".45-70":
/* 1287 */         plate = CollatorPlates.BASE_DOWN_BRASS_4570;
/*      */         break;
/*      */       case "Small Rifle":
/* 1290 */         if (this.baseDownRadioButton.isSelected()) {
/* 1291 */           plate = CollatorPlates.BASE_DOWN_BRASS_SMALL_RIFLE; break;
/*      */         } 
/* 1293 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_SMALL_RIFLE;
/*      */         break;
/*      */       
/*      */       case "Large Rifle":
/* 1297 */         if (this.baseDownRadioButton.isSelected()) {
/* 1298 */           plate = CollatorPlates.BASE_DOWN_BRASS_LARGE_RIFLE; break;
/*      */         } 
/* 1300 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LARGE_RIFLE;
/*      */         break;
/*      */       
/*      */       case "Long Rifle":
/* 1304 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LONG_RIFLE;
/*      */         break;
/*      */       default:
/* 1307 */         if (this.baseUpRadioButton.isSelected()) {
/* 1308 */           plate = CollatorPlates.BASE_UP_BRASS_SMALL_PISTOL; break;
/* 1309 */         }  if (this.baseDownRadioButton.isSelected()) {
/* 1310 */           plate = CollatorPlates.BASE_DOWN_BRASS_SMALL_PISTOL; break;
/*      */         } 
/* 1312 */         plate = CollatorPlates.NOSE_UP_DOWN_BULLET_SMALL_PISTOL;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1317 */     this.descriptionTextField.setText(plate.getDescription());
/* 1318 */     this.caliberSpinner.getValueFactory().setValue(Double.valueOf(plate.getCaliber()));
/* 1319 */     this.plateHeightSpinner.getValueFactory().setValue(Double.valueOf(plate.getPlateHeight()));
/* 1320 */     this.isLongRifleBulletCheckBox.setSelected(plate.isIsLongRifleBullet());
/* 1321 */     this.addRampsCheckBox.setSelected(plate.isAddRamps());
/* 1322 */     this.isRifleBrassCheckBox.setSelected(plate.isIsRifleBrass());
/* 1323 */     this.rifleHoleWidthSpinner.getValueFactory().setValue(Double.valueOf(plate.getRifleHoleWidth()));
/* 1324 */     this.addPivotsCheckBox.setSelected(plate.isAddPivots());
/* 1325 */     this.addSlidesCheckBox.setSelected(plate.isAddSlides());
/* 1326 */     this.addRidgesCheckBox.setSelected(plate.isAddRidges());
/* 1327 */     this.ridgeCenterCheckBox.setSelected(plate.isRidgeCenter());
/* 1328 */     this.ridgeAlternateCheckBox.setSelected(plate.isRidgeAlternate());
/* 1329 */     this.ridgeHeightSpinner.getValueFactory().setValue(Double.valueOf(plate.getRidgeHeight()));
/* 1330 */     this.ridgeLengthSpinner.getValueFactory().setValue(Double.valueOf(plate.getRidgeLength()));
/* 1331 */     this.addBevelCheckBox.setSelected(plate.isAddBevel());
/* 1332 */     this.bevelSizeSpinner.getValueFactory().setValue(Double.valueOf(plate.getBevelSize()));
/* 1333 */     this.holeMultiplierSpinner.getValueFactory().setValue(Double.valueOf(plate.getHoleMultiplier()));
/* 1334 */     this.useClutchCheckBox.setSelected(plate.isUseClutch());
/* 1335 */     this.useHexCheckBox.setSelected(plate.isUseHex());
/* 1336 */     this.addHexHandleCheckBox.setSelected(plate.isAddHexHandle());
/* 1337 */     this.hxwSpinner.getValueFactory().setValue(Double.valueOf(plate.getHxw()));
/* 1338 */     this.shaftHoleSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftHole()));
/* 1339 */     this.shaftSlotLengthSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftSlotLength()));
/* 1340 */     this.shaftSlotWidthSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftSlotWidth()));
/* 1341 */     this.fnSpinner.getValueFactory().setValue(Integer.valueOf(plate.getFn()));
/* 1342 */     this.plateDiameterSpinner.getValueFactory().setValue(Double.valueOf(plate.getPlateDiameter()));
/* 1343 */     this.bulletCaliberSpinner.getValueFactory().setValue(Double.valueOf(plate.getBulletCaliber()));
/*      */   }
/*      */   
/*      */   private LinkedList<String> getPlateSettings() {
/* 1347 */     LinkedList<String> settings = new LinkedList<>();
/*      */ 
/*      */     
/* 1350 */     settings.add("-D");
/* 1351 */     settings.add("\"description=\\\"" + this.descriptionTextField.getText() + "\\\"\"");
/* 1352 */     settings.add("-D");
/* 1353 */     settings.add("\"caliber=" + this.caliberSpinner.getValue() + "\"");
/* 1354 */     settings.add("-D");
/* 1355 */     settings.add("\"collator_plate_h=" + this.plateHeightSpinner.getValue() + "\"");
/* 1356 */     settings.add("-D");
/* 1357 */     settings.add("\"isLongRifleBullet=" + this.isLongRifleBulletCheckBox.isSelected() + "\"");
/* 1358 */     settings.add("-D");
/* 1359 */     settings.add("\"addRamps=" + this.addRampsCheckBox.isSelected() + "\"");
/* 1360 */     settings.add("-D");
/* 1361 */     settings.add("\"isRifleBrassPlate=" + this.isRifleBrassCheckBox.isSelected() + "\"");
/* 1362 */     settings.add("-D");
/* 1363 */     settings.add("\"rifleHoleWidth=" + this.rifleHoleWidthSpinner.getValue() + "\"");
/* 1364 */     settings.add("-D");
/* 1365 */     settings.add("\"addPivots=" + this.addPivotsCheckBox.isSelected() + "\"");
/* 1366 */     settings.add("-D");
/* 1367 */     settings.add("\"addSlides=" + this.addSlidesCheckBox.isSelected() + "\"");
/* 1368 */     settings.add("-D");
/* 1369 */     settings.add("\"addRidges=" + this.addRidgesCheckBox.isSelected() + "\"");
/* 1370 */     settings.add("-D");
/* 1371 */     settings.add("\"ridgeCenter=" + this.ridgeCenterCheckBox.isSelected() + "\"");
/* 1372 */     settings.add("-D");
/* 1373 */     settings.add("\"ridgeAlternate=" + this.ridgeAlternateCheckBox.isSelected() + "\"");
/* 1374 */     settings.add("-D");
/* 1375 */     settings.add("\"ridgeHeight=" + this.ridgeHeightSpinner.getValue() + "\"");
/* 1376 */     settings.add("-D");
/* 1377 */     settings.add("\"ridgeLength=" + this.ridgeLengthSpinner.getValue() + "\"");
/* 1378 */     settings.add("-D");
/* 1379 */     settings.add("\"addBevel=" + this.addBevelCheckBox.isSelected() + "\"");
/* 1380 */     settings.add("-D");
/* 1381 */     settings.add("\"bevelSize=" + this.bevelSizeSpinner.getValue() + "\"");
/* 1382 */     settings.add("-D");
/* 1383 */     settings.add("\"hole_multiplier=" + this.holeMultiplierSpinner.getValue() + "\"");
/* 1384 */     settings.add("-D");
/* 1385 */     settings.add("\"useClutch=" + this.useClutchCheckBox.isSelected() + "\"");
/* 1386 */     settings.add("-D");
/* 1387 */     settings.add("\"useHex=" + this.useHexCheckBox.isSelected() + "\"");
/* 1388 */     settings.add("-D");
/* 1389 */     settings.add("\"addHexHandle=" + this.addHexHandleCheckBox.isSelected() + "\"");
/* 1390 */     settings.add("-D");
/* 1391 */     settings.add("\"hexHandleHeight=" + this.hexHandleHeightSpinner.getValue() + "\"");
/* 1392 */     settings.add("-D");
/* 1393 */     settings.add("\"hxw=" + this.hxwSpinner.getValue() + "\"");
/* 1394 */     settings.add("-D");
/* 1395 */     settings.add("\"shaft_hole=" + this.shaftHoleSpinner.getValue() + "\"");
/* 1396 */     settings.add("-D");
/* 1397 */     settings.add("\"shaft_slot_length=" + this.shaftSlotLengthSpinner.getValue() + "\"");
/* 1398 */     settings.add("-D");
/* 1399 */     settings.add("\"shaft_slot_width=" + this.shaftSlotWidthSpinner.getValue() + "\"");
/* 1400 */     settings.add("-D");
/* 1401 */     settings.add("\"$fn=" + this.fnSpinner.getValue() + "\"");
/* 1402 */     settings.add("-D");
/* 1403 */     settings.add("\"collator_plate_d=" + this.plateDiameterSpinner.getValue() + "\"");
/* 1404 */     settings.add("-D");
/* 1405 */     settings.add("\"bullet_caliber=" + (((Double)this.caliberSpinner.getValue()).doubleValue() + ((Double)this.bulletCaliberSpinner.getValue()).doubleValue()) + "\"");
/*      */     
/* 1407 */     return settings;
/*      */   }
/*      */   
/*      */   private String getProjectPath() {
/* 1411 */     if ((new File(System.getProperty("user.dir") + "/1. Main Body")).exists()) {
/* 1412 */       return System.getProperty("user.dir");
/*      */     }
/* 1414 */     return "The project folder was not found. Place the .jar file in the project folder or press the Change button to select the project location.";
/*      */   }
/*      */ 
/*      */   
/*      */   private String getOpenSCADpath() {
/* 1419 */     String[] locations = { System.getenv("SystemDrive") + "/Program Files/OpenSCAD", System.getenv("SystemDrive") + "/Program Files (x86)/OpenSCAD" };
/*      */     
/* 1421 */     if ((new File(locations[0])).exists())
/* 1422 */       return (new File(locations[0])).getAbsolutePath(); 
/* 1423 */     if ((new File(locations[1])).exists()) {
/* 1424 */       return (new File(locations[1])).getAbsolutePath();
/*      */     }
/* 1426 */     return "OpenSCAD was not found on your computer! Download it at http://www.openscad.org/";
/*      */   }
/*      */ 
/*      */   
/*      */   private String getOpenSCADfile() {
/* 1431 */     String tempFilePath = "";
/*      */     
/*      */     try {
/* 1434 */       tempFilePath = File.createTempFile("generator", ".scad").getAbsolutePath();
/* 1435 */       Scanner input = new Scanner(PartsWindowController.class.getResource("generator.scad").openStream());
/* 1436 */       PrintWriter output = new PrintWriter(tempFilePath);
/*      */       
/* 1438 */       while (input.hasNextLine()) {
/* 1439 */         output.println(input.nextLine());
/*      */       }
/* 1441 */       input.close();
/* 1442 */       output.close();
/* 1443 */       (new File(tempFilePath)).deleteOnExit();
/* 1444 */     } catch (Exception exception) {}
/*      */     
/* 1446 */     return tempFilePath;
/*      */   }
/*      */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/partsgenerator/PartsWindowController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */