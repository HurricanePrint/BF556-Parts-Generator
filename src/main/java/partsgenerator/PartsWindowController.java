package partsgenerator;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.IOException;

public class PartsWindowController implements Initializable {
  private final LinkedHashSet<Parts> PARTS = new LinkedHashSet<>();

  private Process stlProcess;
  private boolean isCancelled;
  private String tempOpenSCADPath;
  @FXML
  private ToggleGroup motorToggleGroup;
  @FXML
  private ToggleGroup mountToggleGroup;
  @FXML
  private ToggleGroup electronicsToggleGroup;
  @FXML
  private ToggleGroup sensorToggleGroup;
  @FXML
  private ToggleGroup shaftToggleGroup;
  @FXML
  private ToggleGroup orientationToggleGroup;
  @FXML
  private ToggleGroup caliberToggleGroup;
  @FXML
  private RadioButton etzgmp38RadioButton;
  @FXML
  private RadioButton fc555RadioButton;
  @FXML
  private RadioButton jgy370RadioButton;
  @FXML
  private RadioButton m634jsRadioButton;
  @FXML
  private RadioButton dillonRadioButton;
  @FXML
  private RadioButton smallElectronicsRadioButton;
  @FXML
  private RadioButton largeElectronicsRadioButton;
  @FXML
  private RadioButton switchRadioButton;
  @FXML
  private RadioButton photosensorRadioButton;
  @FXML
  private RadioButton proximityRadioButton;
  @FXML
  private RadioButton pinnedShaftRadioButton;
  @FXML
  private RadioButton hexCouplerRadioButton;
  @FXML
  private RadioButton caliber9RadioButton;
  @FXML
  private RadioButton caliber40RadioButton;
  @FXML
  private RadioButton caliber45RadioButton;
  @FXML
  private RadioButton caliber50RadioButton;
  @FXML
  private RadioButton caliber300RadioButton;
  @FXML
  private RadioButton caliber4570RadioButton;
  @FXML
  private RadioButton caliberSmallPistolRadioButton;
  @FXML
  private RadioButton caliberLargePistolRadioButton;
  @FXML
  private RadioButton caliber223RadioButton;
  @FXML
  private RadioButton caliber65RadioButton;
  @FXML
  private RadioButton caliber308RadioButton;
  @FXML
  private RadioButton caliberSmallRifleRadioButton;
  @FXML
  private RadioButton caliberLargeRifleRadioButton;
  @FXML
  private RadioButton caliberLongRifleRadioButton;
  @FXML
  private RadioButton baseUpRadioButton;
  @FXML
  private RadioButton baseDownRadioButton;
  @FXML
  private RadioButton noseUpDownRadioButton;
  @FXML
  private CheckBox templateCheckBox;
  @FXML
  private CheckBox wallExtenderCheckBox;
  @FXML
  private CheckBox slidePlateLatchCheckBox;
  @FXML
  private CheckBox noseUp9CheckBox;
  @FXML
  private CheckBox noseUp40CheckBox;
  @FXML
  private CheckBox noseUp45CheckBox;
  @FXML
  private CheckBox noseUp223CheckBox;
  @FXML
  private CheckBox noseUp300CheckBox;
  @FXML
  private CheckBox noseUp308CheckBox;
  @FXML
  private CheckBox noseDown9CheckBox;
  @FXML
  private CheckBox noseDown40CheckBox;
  @FXML
  private CheckBox noseDown45CheckBox;
  @FXML
  private CheckBox noseDown223CheckBox;
  @FXML
  private CheckBox noseDown300CheckBox;
  @FXML
  private CheckBox noseDown308CheckBox;
  @FXML
  private CheckBox baseUp9CheckBox;
  @FXML
  private CheckBox baseUp40CheckBox;
  @FXML
  private CheckBox baseUp45CheckBox;
  @FXML
  private CheckBox baseDown9CheckBox;
  @FXML
  private CheckBox baseDown40CheckBox;
  @FXML
  private CheckBox baseDown45CheckBox;
  @FXML
  private CheckBox baseDown223CheckBox;
  @FXML
  private CheckBox baseDown300CheckBox;
  @FXML
  private CheckBox baseDown308CheckBox;
  @FXML
  private CheckBox clutchCoverCheckBox;
  @FXML
  private CheckBox collatorHandleCheckBox;
  @FXML
  private CheckBox adapter7;
  @FXML
  private CheckBox adapter8;
  @FXML
  private CheckBox adapter9;
  @FXML
  private CheckBox adapter10;
  @FXML
  private CheckBox adapter11;
  @FXML
  private CheckBox adapter12;
  @FXML
  private CheckBox adapter13;
  @FXML
  private CheckBox adapter14;
  @FXML
  private CheckBox bulletFeedDie7CheckBox;
  @FXML
  private CheckBox bulletFeedDie9CheckBox;
  @FXML
  private CheckBox bulletFeedDie32CheckBox;
  @FXML
  private CheckBox bulletFeedDie40CheckBox;
  @FXML
  private CheckBox bulletFeedDie44CheckBox;
  @FXML
  private CheckBox bulletFeedDie45CheckBox;
  @FXML
  private CheckBox bulletFeedDie223CheckBox;
  @FXML
  private CheckBox bulletFeedDie300CheckBox;
  @FXML
  private CheckBox bulletFeedDie308CheckBox;
  @FXML
  private CheckBox bulletFeedDie4570CheckBox;
  @FXML
  private CheckBox app9BulletCheckBox;
  @FXML
  private CheckBox app45BulletCheckBox;
  @FXML
  private CheckBox app32BulletCheckBox;
  @FXML
  private CheckBox app223BulletCheckBox;
  @FXML
  private CheckBox app40BulletCheckBox;
  @FXML
  private CheckBox app308BulletCheckBox;
  @FXML
  private CheckBox app9BrassCheckBox;
  @FXML
  private CheckBox app32BrassCheckBox;
  @FXML
  private CheckBox app40BrassCheckBox;
  @FXML
  private CheckBox app45BrassCheckBox;
  @FXML
  private CheckBox app223BrassCheckBox;
  @FXML
  private CheckBox app308BrassCheckBox;
  @FXML
  private CheckBox collatorSensorCheckBox;
  @FXML
  private CheckBox isLongRifleBulletCheckBox;
  @FXML
  private CheckBox addRampsCheckBox;
  @FXML
  private CheckBox isRifleBrassCheckBox;
  @FXML
  private CheckBox addPivotsCheckBox;
  @FXML
  private CheckBox addSlidesCheckBox;
  @FXML
  private CheckBox addRidgesCheckBox;
  @FXML
  private CheckBox ridgeCenterCheckBox;
  @FXML
  private CheckBox ridgeAlternateCheckBox;
  @FXML
  private CheckBox addBevelCheckBox;
  @FXML
  private CheckBox useClutchCheckBox;
  @FXML
  private CheckBox useHexCheckBox;
  @FXML
  private CheckBox addHexHandleCheckBox;
  @FXML
  private Label progressLabel;
  @FXML
  private Label generateLabel1;
  @FXML
  private Label generateLabel2;
  @FXML
  private TextField projectPathTextField;
  @FXML
  private TextField openSCADPathTextField;
  @FXML
  private TextField descriptionTextField;
  @FXML
  private Spinner<Double> caliberSpinner;
  @FXML
  private Spinner<Double> rifleHoleWidthSpinner;
  @FXML
  private Spinner<Double> ridgeHeightSpinner;
  @FXML
  private Spinner<Double> ridgeLengthSpinner;
  @FXML
  private Spinner<Double> bevelSizeSpinner;
  @FXML
  private Spinner<Double> holeMultiplierSpinner;
  @FXML
  private Spinner<Double> hexHandleHeightSpinner;
  @FXML
  private Spinner<Double> hxwSpinner;
  @FXML
  private Spinner<Double> shaftHoleSpinner;
  @FXML
  private Spinner<Double> shaftSlotLengthSpinner;
  @FXML
  private Spinner<Double> shaftSlotWidthSpinner;
  @FXML
  private Spinner<Integer> fnSpinner;
  @FXML
  private Spinner<Double> plateHeightSpinner;
  @FXML
  private Spinner<Double> plateDiameterSpinner;
  @FXML
  private Spinner<Double> bulletCaliberSpinner;
  @FXML
  private Button previewButton;
  @FXML
  private Button stlStartButton;
  @FXML
  private Button projectPathChangeButton;
  @FXML
  private Button zipStartButton;
  @FXML
  private ProgressBar partsProgressBar;
  @FXML
  private ProgressIndicator platesProgressBar;
  @FXML
  private CheckBox mongoCheckBox;
  @FXML
  private CheckBox baseDown57CheckBox;
  @FXML
  private CheckBox app44BrassCheckBox;
  @FXML
  private CheckBox app357BrassCheckBox;
  @FXML
  private CheckBox app57BrassCheckBox;
  @FXML
  private CheckBox app65BrassCheckBox;
  @FXML
  private CheckBox app270BrassCheckBox;
  @FXML
  private CheckBox app762BrassCheckBox;
  @FXML
  private RadioButton verticalCircularPostRadioButton;
  @FXML
  private RadioButton verticalSquarePostRadioButton;
  @FXML
  private RadioButton horizontalSquarePostRadioButton;
  @FXML
  private CheckBox app300BrassCheckBox;

  private Path getApplicationFolder() {
    List<Path> candidates = new ArrayList<>();

    candidates.add(Paths.get(System.getProperty("user.dir")));

    try {
      Path location = Paths.get(
          Main.class.getProtectionDomain()
              .getCodeSource()
              .getLocation()
              .toURI());

      candidates.add(Files.isRegularFile(location)
          ? location.getParent()
          : location);
    } catch (Exception ignored) {

    }

    try {
      Path command = Paths.get(
          ProcessHandle.current()
              .info()
              .command()
              .orElse(""));

      if (Files.isRegularFile(command)) {
        candidates.add(command.getParent());
      }
    } catch (Exception ignored) {

    }

    for (Path candidate : candidates) {
      Path current = candidate.toAbsolutePath().normalize();

      while (current != null) {
        if (Files.isDirectory(current.resolve("1. Main Body"))) {
          return current;
        }
        current = current.getParent();
      }
    }

    return Paths.get(System.getProperty("user.dir"))
        .toAbsolutePath()
        .normalize();
  }

  public void initialize(URL url, ResourceBundle rb) {
    this.projectPathTextField.setText(getApplicationFolder().toString());
    this.openSCADPathTextField.setText(getOpenSCADpath());
    this.tempOpenSCADPath = getOpenSCADfile();

    this.mongoCheckBox.setOnAction(event -> {
      if (this.mongoCheckBox.isSelected()) {
        this.etzgmp38RadioButton.setSelected(true);

        this.jgy370RadioButton.setDisable(true);

        this.verticalCircularPostRadioButton.setSelected(true);
        this.dillonRadioButton.setDisable(true);
        this.baseUp9CheckBox.setSelected(false);
        this.baseUp40CheckBox.setSelected(false);
        this.baseUp45CheckBox.setSelected(false);
        this.baseUp9CheckBox.setDisable(true);
        this.baseUp40CheckBox.setDisable(true);
        this.baseUp45CheckBox.setDisable(true);
        this.baseDown57CheckBox.setSelected(false);
        this.baseDown300CheckBox.setSelected(false);
        this.baseDown57CheckBox.setDisable(true);
        this.baseDown300CheckBox.setDisable(true);
        this.noseUp9CheckBox.setSelected(false);
        this.noseUp40CheckBox.setSelected(false);
        this.noseUp45CheckBox.setSelected(false);
        this.noseUp223CheckBox.setSelected(false);
        this.noseUp300CheckBox.setSelected(false);
        this.noseUp308CheckBox.setSelected(false);
        this.noseDown9CheckBox.setSelected(false);
        this.noseDown40CheckBox.setSelected(false);
        this.noseDown45CheckBox.setSelected(false);
        this.noseDown223CheckBox.setSelected(false);
        this.noseDown300CheckBox.setSelected(false);
        this.noseDown308CheckBox.setSelected(false);
        this.noseUp9CheckBox.setDisable(true);
        this.noseUp40CheckBox.setDisable(true);
        this.noseUp45CheckBox.setDisable(true);
        this.noseUp223CheckBox.setDisable(true);
        this.noseUp300CheckBox.setDisable(true);
        this.noseUp308CheckBox.setDisable(true);
        this.noseDown9CheckBox.setDisable(true);
        this.noseDown40CheckBox.setDisable(true);
        this.noseDown45CheckBox.setDisable(true);
        this.noseDown223CheckBox.setDisable(true);
        this.noseDown300CheckBox.setDisable(true);
        this.noseDown308CheckBox.setDisable(true);
      } else {
        this.jgy370RadioButton.setDisable(false);
        this.dillonRadioButton.setDisable(false);
        this.baseUp9CheckBox.setDisable(false);
        this.baseUp40CheckBox.setDisable(false);
        this.baseUp45CheckBox.setDisable(false);
        this.baseDown57CheckBox.setDisable(false);
        this.baseDown300CheckBox.setDisable(false);
        this.noseUp9CheckBox.setDisable(false);
        this.noseUp40CheckBox.setDisable(false);
        this.noseUp45CheckBox.setDisable(false);
        this.noseUp223CheckBox.setDisable(false);
        this.noseUp300CheckBox.setDisable(false);
        this.noseUp308CheckBox.setDisable(false);
        this.noseDown9CheckBox.setDisable(false);
        this.noseDown40CheckBox.setDisable(false);
        this.noseDown45CheckBox.setDisable(false);
        this.noseDown223CheckBox.setDisable(false);
        this.noseDown300CheckBox.setDisable(false);
        this.noseDown308CheckBox.setDisable(false);
      }
    });
    this.descriptionTextField.setText(CollatorPlates.BASE_DOWN_BRASS_9.getDescription());
    this.caliberSpinner.setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D,
        100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getCaliber(), 0.1D));
    this.plateHeightSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getPlateHeight(), 0.1D));
    this.isLongRifleBulletCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isIsLongRifleBullet());
    this.addRampsCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddRamps());
    this.isRifleBrassCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isIsRifleBrass());
    this.rifleHoleWidthSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getRifleHoleWidth(), 0.1D));
    this.addPivotsCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddPivots());
    this.addSlidesCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddSlides());
    this.addRidgesCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddRidges());
    this.ridgeCenterCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isRidgeCenter());
    this.ridgeAlternateCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isRidgeAlternate());
    this.ridgeHeightSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getRidgeHeight(), 0.1D));
    this.ridgeLengthSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getRidgeLength(), 0.1D));
    this.addBevelCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddBevel());
    this.bevelSizeSpinner.setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D,
        100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getBevelSize(), 0.1D));
    this.holeMultiplierSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getHoleMultiplier(), 0.1D));
    this.useClutchCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isUseClutch());
    this.useHexCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isUseHex());
    this.addHexHandleCheckBox.setSelected(CollatorPlates.BASE_DOWN_BRASS_9.isAddHexHandle());
    this.hexHandleHeightSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getHexHandleHeight(), 0.1D));
    this.hxwSpinner.setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D,
        100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getHxw(), 0.1D));
    this.shaftHoleSpinner.setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D,
        100.0D, CollatorPlates.BASE_DOWN_BRASS_9.getShaftHole(), 0.1D));
    this.shaftSlotLengthSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getShaftSlotLength(), 0.1D));
    this.shaftSlotWidthSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getShaftSlotWidth(), 0.1D));
    this.fnSpinner.setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
        CollatorPlates.BASE_DOWN_BRASS_9.getFn(), 1));
    this.plateDiameterSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 200.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getPlateDiameter(), 0.1D));
    this.bulletCaliberSpinner
        .setValueFactory((SpinnerValueFactory) new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0D, 100.0D,
            CollatorPlates.BASE_DOWN_BRASS_9.getBulletCaliber(), 0.1D));

    this.useHexCheckBox.setOnAction(event -> {
      if (this.useHexCheckBox.isSelected()) {
        this.addHexHandleCheckBox.setDisable(false);
        this.hexHandleHeightSpinner.setDisable(false);
        this.hxwSpinner.setDisable(false);
        this.shaftHoleSpinner.setDisable(true);
        this.shaftSlotLengthSpinner.setDisable(true);
        this.shaftSlotWidthSpinner.setDisable(true);
      } else {
        this.addHexHandleCheckBox.setDisable(true);
        this.hexHandleHeightSpinner.setDisable(true);
        this.hxwSpinner.setDisable(true);
        this.shaftHoleSpinner.setDisable(false);
        this.shaftSlotLengthSpinner.setDisable(false);
        this.shaftSlotWidthSpinner.setDisable(false);
      }
    });
    this.collatorSensorCheckBox.setOnAction(event -> {
      if (this.collatorSensorCheckBox.isSelected()) {
        this.switchRadioButton.setDisable(true);

        this.photosensorRadioButton.setSelected(true);
      } else {
        this.switchRadioButton.setDisable(false);
        this.switchRadioButton.setSelected(true);
      }
    });
    this.baseUpRadioButton.setOnAction(event -> {
      this.caliberSmallPistolRadioButton.setSelected(true);
      this.caliberSmallPistolRadioButton.fireEvent((Event) new ActionEvent());
      this.caliber9RadioButton.setDisable(true);
      this.caliber40RadioButton.setDisable(true);
      this.caliber45RadioButton.setDisable(true);
      this.caliber50RadioButton.setDisable(true);
      this.caliberSmallPistolRadioButton.setDisable(false);
      this.caliberLargePistolRadioButton.setDisable(false);
      this.caliber223RadioButton.setDisable(true);
      this.caliber65RadioButton.setDisable(true);
      this.caliber300RadioButton.setDisable(true);
      this.caliber308RadioButton.setDisable(true);
      this.caliber4570RadioButton.setDisable(true);
      this.caliberSmallRifleRadioButton.setDisable(true);
      this.caliberLargeRifleRadioButton.setDisable(true);
      this.caliberLongRifleRadioButton.setDisable(true);
    });
    this.baseDownRadioButton.setOnAction(event -> {
      this.caliber9RadioButton.setSelected(true);
      this.caliber9RadioButton.fireEvent((Event) new ActionEvent());
      this.caliber9RadioButton.setDisable(false);
      this.caliber40RadioButton.setDisable(false);
      this.caliber45RadioButton.setDisable(false);
      this.caliber50RadioButton.setDisable(false);
      this.caliberSmallPistolRadioButton.setDisable(false);
      this.caliberLargePistolRadioButton.setDisable(false);
      this.caliber223RadioButton.setDisable(false);
      this.caliber65RadioButton.setDisable(true);
      this.caliber300RadioButton.setDisable(false);
      this.caliber308RadioButton.setDisable(false);
      this.caliber4570RadioButton.setDisable(false);
      this.caliberSmallRifleRadioButton.setDisable(false);
      this.caliberLargeRifleRadioButton.setDisable(false);
      this.caliberLongRifleRadioButton.setDisable(true);
    });
    this.noseUpDownRadioButton.setOnAction(event -> {
      this.caliber9RadioButton.setSelected(true);
      this.caliber9RadioButton.fireEvent((Event) new ActionEvent());
      this.caliber9RadioButton.setDisable(false);
      this.caliber40RadioButton.setDisable(false);
      this.caliber45RadioButton.setDisable(false);
      this.caliber50RadioButton.setDisable(false);
      this.caliberSmallPistolRadioButton.setDisable(false);
      this.caliberLargePistolRadioButton.setDisable(false);
      this.caliber223RadioButton.setDisable(false);
      this.caliber65RadioButton.setDisable(false);
      this.caliber300RadioButton.setDisable(false);
      this.caliber308RadioButton.setDisable(false);
      this.caliber4570RadioButton.setDisable(true);
      this.caliberSmallRifleRadioButton.setDisable(false);
      this.caliberLargeRifleRadioButton.setDisable(false);
      this.caliberLongRifleRadioButton.setDisable(false);
    });
    this.noseUpDownRadioButton.fireEvent((Event) new ActionEvent());
    this.useHexCheckBox.fireEvent((Event) new ActionEvent());
    this.platesProgressBar.setVisible(false);
  }

  @FXML
  private void getNewFolderPath(ActionEvent event) {
    DirectoryChooser dc = new DirectoryChooser();

    dc.setInitialDirectory(new File(System.getProperty("user.dir")));
    if (event.getSource() == this.projectPathChangeButton) {
      this.projectPathTextField.setText(dc.showDialog(null).getAbsolutePath());
    } else {
      this.openSCADPathTextField.setText(dc.showDialog(null).getAbsolutePath());
    }
  }

  private void getMainBodyParts() {
    if (this.etzgmp38RadioButton.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.MAIN_BODY_ETZGMP38_MONGO);
        this.PARTS.add(Parts.RAMP_MONGO);
      } else {
        this.PARTS.add(Parts.MAIN_BODY_ETZGMP38);
        this.PARTS.add(Parts.RAMP);
      }
      this.PARTS.add(Parts.FLIPPER);
      this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
    }
    if (this.fc555RadioButton.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.MAIN_BODY_FC555_MONGO);
        this.PARTS.add(Parts.RAMP_MONGO);
      } else {
        this.PARTS.add(Parts.MAIN_BODY_FC555);
        this.PARTS.add(Parts.RAMP);
      }
      this.PARTS.add(Parts.FLIPPER);
      this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
    }
    if (this.jgy370RadioButton.isSelected()) {
      this.PARTS.add(Parts.MAIN_BODY_JGY370);
      this.PARTS.add(Parts.RAMP);
      this.PARTS.add(Parts.FLIPPER);
      this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
    }
    if (this.m634jsRadioButton.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.MAIN_BODY_M634JS_MONGO);
        this.PARTS.add(Parts.RAMP_MONGO);
      } else {
        this.PARTS.add(Parts.MAIN_BODY_M634JS);
        this.PARTS.add(Parts.RAMP);
      }
      this.PARTS.add(Parts.FLIPPER);
      this.PARTS.add(Parts.DROP_HOLE_ADAPTER);
    }

    if (this.smallElectronicsRadioButton.isSelected()) {
      if (this.templateCheckBox.isSelected()) {
        this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL_TEMPLATE);
      } else {
        this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL);
      }
      this.PARTS.add(Parts.ELECTRONICS_BOX_SMALL_LID);
    }
    if (this.largeElectronicsRadioButton.isSelected()) {
      if (this.templateCheckBox.isSelected()) {
        this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE_TEMPLATE);
      } else {
        this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE);
      }
      this.PARTS.add(Parts.ELECTRONICS_BOX_LARGE_LID);
    }

    if (this.wallExtenderCheckBox.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.MAIN_BODY_WALL_EXTENDER_MONGO);
      } else {
        this.PARTS.add(Parts.MAIN_BODY_WALL_EXTENDER);
      }
    }
    if (this.slidePlateLatchCheckBox.isSelected()) {
      this.PARTS.add(Parts.SLIDE_PLATE_LATCH);
    }

    if (isNoseDown() || isBaseUp() || isBaseDown()) {
      this.PARTS.add(Parts.DROP_HOLE_PLUG);
    }
    if (isBaseUp()) {
      this.PARTS.add(Parts.RAMP_BRASS_BASE_UP);
    }
    if (isBaseUp() || this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected()) {
      this.PARTS.add(Parts.SWEEPER);
    }
    if (this.baseDown223CheckBox.isSelected() || this.baseDown308CheckBox.isSelected()
        || this.baseDown300CheckBox.isSelected()) {
      this.PARTS.add(Parts.SWEEPER_LONG);
    }
  }

  private void getMountParts() {
    if (this.dillonRadioButton.isSelected()) {
      this.PARTS.add(Parts.MOUNT_DILLON_HANG);
      this.PARTS.add(Parts.MOUNT_DILLON_HANG_BASE);
    }

    if (this.verticalCircularPostRadioButton.isSelected() || this.verticalSquarePostRadioButton.isSelected()
        || this.horizontalSquarePostRadioButton.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.MOUNT_POST_MONGO);
      } else {
        this.PARTS.add(Parts.MOUNT_POST);
      }
      if (this.verticalCircularPostRadioButton.isSelected()) {
        this.PARTS.add(Parts.MOUNT_POST_BASE);
      } else if (this.verticalSquarePostRadioButton.isSelected()) {
        this.PARTS.add(Parts.MOUNT_POST_BASE_SQUARE);
      } else {
        this.PARTS.add(Parts.MOUNT_SQUARE_TUBE);
      }
    }
  }

  private void getCollatorPlateParts() {
    if (this.noseUp9CheckBox.isSelected() || this.noseUp40CheckBox.isSelected() || this.noseDown9CheckBox.isSelected()
        || this.noseDown40CheckBox.isSelected()) {
      this.PARTS.add(Parts.PISTOL_BULLET_COLLATOR_PLATE_SMALL_5);
    }
    if (this.noseUp45CheckBox.isSelected() || this.noseDown45CheckBox.isSelected()) {
      this.PARTS.add(Parts.PISTOL_BULLET_COLLATOR_PLATE_LARGE_7);
    }
    if (this.noseUp223CheckBox.isSelected() || this.noseDown223CheckBox.isSelected()) {
      this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_SMALL_2);
    }
    if (this.noseUp308CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
      this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_LARGE_4);
    }
    if (this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected()) {
      this.PARTS.add(Parts.RIFLE_BULLET_COLLATOR_PLATE_LONG_11);
    }

    if (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected()) {
      this.PARTS.add(Parts.PISTOL_BRASS_BASE_UP_COLLATOR_PLATE_SMALL);
    }
    if (this.baseUp45CheckBox.isSelected()) {
      this.PARTS.add(Parts.PISTOL_BRASS_BASE_UP_COLLATOR_PLATE_LARGE);
    }

    if (this.baseDown9CheckBox.isSelected() || this.baseDown40CheckBox.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_SMALL_MONGO);
      } else {
        this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_SMALL);
      }
    }
    if (this.baseDown45CheckBox.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_LARGE_MONGO);
      } else {
        this.PARTS.add(Parts.PISTOL_BRASS_COLLATOR_PLATE_LARGE);
      }
    }
    if (this.baseDown223CheckBox.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_SMALL_MONGO);
      } else {
        this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_SMALL);
      }
    }
    if (this.baseDown308CheckBox.isSelected()) {
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_LARGE_MONGO);
      } else {
        this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_LARGE);
      }
    }
    if (this.baseDown57CheckBox.isSelected()) {
      this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_57);
    }
    if (this.baseDown300CheckBox.isSelected()) {
      this.PARTS.add(Parts.RIFLE_BRASS_COLLATOR_PLATE_300BO);
    }
    if (this.pinnedShaftRadioButton.isSelected()) {
      this.PARTS.add(Parts.SLIP_CLUTCH_PINNED);
      this.PARTS.add(Parts.SLIP_CLUTCH_RING);
    }
    if (this.hexCouplerRadioButton.isSelected()) {
      this.PARTS.add(Parts.SLIP_CLUTCH_HEX);
      this.PARTS.add(Parts.SLIP_CLUTCH_RING);
    }
    if (this.clutchCoverCheckBox.isSelected()) {
      this.PARTS.add(Parts.SLIP_CLUTCH_COVER);
    }
    if (this.collatorHandleCheckBox.isSelected()) {
      this.PARTS.add(Parts.COLLATOR_PLATE_HANDLE);
    }
  }

  private void getSlidePlateParts() {
    if (this.noseUp9CheckBox.isSelected() || this.noseUp40CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_5);
    }
    if (this.noseUp45CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_7);
    }
    if (this.noseUp223CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_2);
    }
    if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_UP_SLIDE_PLATE_11);
    }

    if (this.noseDown9CheckBox.isSelected() || this.noseDown40CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_5);
    }
    if (this.noseDown45CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_7);
    }
    if (this.noseDown223CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_2);
    }
    if (this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_NOSE_DOWN_SLIDE_PLATE_11);
    }

    if (isBaseUp()) {
      this.PARTS.add(Parts.BRASS_BASE_UP_DROP_HOLE_ADAPTER);
      if (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected()) {
        this.PARTS.add(Parts.BRASS_BASE_UP_SLIDE_PLATE_SMALL);
      }
      if (this.baseUp45CheckBox.isSelected()) {
        this.PARTS.add(Parts.BRASS_BASE_UP_SLIDE_PLATE_LARGE);
      }
    }

    if (isBaseDown()) {
      this.PARTS.add(Parts.BRASS_DROP_HOLE_ADAPTER);
      this.PARTS.add(Parts.BRASS_SLIDE_ADJUSTER);
      if (this.mongoCheckBox.isSelected()) {
        this.PARTS.add(Parts.BRASS_SLIDE_PLATE_MONGO);
      } else {
        this.PARTS.add(Parts.BRASS_SLIDE_PLATE);
      }
    }
  }

  private void getAdapterParts() {
    if (this.adapter7.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_7);
      this.PARTS.add(Parts.SPRING_ADAPTER_7_DT);
    }
    if (this.adapter8.isSelected()) {
      if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected()
          || this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
        this.PARTS.add(Parts.SPRING_ADAPTER_8_FLUSH);
      } else {
        this.PARTS.add(Parts.SPRING_ADAPTER_8);
      }
      this.PARTS.add(Parts.SPRING_ADAPTER_8);
    }
    if (this.adapter9.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_9);
      this.PARTS.add(Parts.SPRING_ADAPTER_9_DT);
    }
    if (this.adapter10.isSelected()) {
      if (this.noseUp300CheckBox.isSelected() || this.noseUp308CheckBox.isSelected()
          || this.noseDown300CheckBox.isSelected() || this.noseDown308CheckBox.isSelected()) {
        this.PARTS.add(Parts.SPRING_ADAPTER_10_FLUSH);
      } else {
        this.PARTS.add(Parts.SPRING_ADAPTER_10);
      }
      this.PARTS.add(Parts.SPRING_ADAPTER_10_DT);
    }
    if (this.adapter11.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_11);
      this.PARTS.add(Parts.SPRING_ADAPTER_11_DT);
    }
    if (this.adapter12.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_12);
      this.PARTS.add(Parts.SPRING_ADAPTER_12_DT);
    }
    if (this.adapter13.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_13);
      this.PARTS.add(Parts.SPRING_ADAPTER_13_DT);
    }
    if (this.adapter14.isSelected()) {
      this.PARTS.add(Parts.SPRING_ADAPTER_14);
      this.PARTS.add(Parts.SPRING_ADAPTER_14_DT);
    }
  }

  private void getDropTubeParts() {
    if (this.proximityRadioButton.isSelected()) {
      this.PARTS.add(Parts.DROP_TUBE_PROXIMITY_HOUSING);
      this.PARTS.add(Parts.DROP_TUBE_PROXIMITY_THREAD_INSERT);
    }
    if (this.photosensorRadioButton.isSelected()) {
      this.PARTS.add(Parts.DROP_TUBE_LED_SENSOR_KNOB);
    }
    if (this.noseUp223CheckBox.isSelected() || this.noseDown223CheckBox.isSelected()) {
      if (this.switchRadioButton.isSelected()) {
        this.PARTS.add(Parts.SWITCH_DROP_TUBE_6);
      } else if (this.collatorSensorCheckBox.isSelected()) {
        this.PARTS.add(Parts.DROP_TUBE_ALT_6);
        this.PARTS.add(Parts.SPRING_DROP_TUBE_SMALL);
      } else {
        this.PARTS.add(Parts.DROP_TUBE_6);
      }
    }
    if (this.noseUp300CheckBox.isSelected() || this.noseDown300CheckBox.isSelected() || this.noseUp308CheckBox
        .isSelected() || this.noseDown308CheckBox.isSelected()) {
      if (this.switchRadioButton.isSelected()) {
        this.PARTS.add(Parts.SWITCH_DROP_TUBE_8);
      } else if (this.collatorSensorCheckBox.isSelected()) {
        this.PARTS.add(Parts.DROP_TUBE_ALT_8);
        this.PARTS.add(Parts.SPRING_DROP_TUBE_MEDIUM);
      } else {
        this.PARTS.add(Parts.DROP_TUBE_8);
      }
    }
    if (this.noseUp9CheckBox.isSelected() || this.noseDown9CheckBox.isSelected() || this.baseUp9CheckBox.isSelected()
        || this.baseDown9CheckBox.isSelected() || this.noseUp40CheckBox
            .isSelected()
        || this.noseDown40CheckBox.isSelected() || this.baseDown223CheckBox
            .isSelected()
        || this.baseDown308CheckBox
            .isSelected()) {
      if (this.switchRadioButton.isSelected()) {
        this.PARTS.add(Parts.SWITCH_DROP_TUBE_10);
      } else if (this.collatorSensorCheckBox.isSelected()) {
        this.PARTS.add(Parts.DROP_TUBE_ALT_10);
        this.PARTS.add(Parts.SPRING_DROP_TUBE_MEDIUM);
      } else {
        this.PARTS.add(Parts.DROP_TUBE_10);
      }
    }
    if (this.baseUp40CheckBox.isSelected() || this.baseDown40CheckBox.isSelected()) {
      if (this.switchRadioButton.isSelected()) {
        this.PARTS.add(Parts.SWITCH_DROP_TUBE_11);
      } else if (this.collatorSensorCheckBox.isSelected()) {
        this.PARTS.add(Parts.DROP_TUBE_ALT_11);
        this.PARTS.add(Parts.SPRING_DROP_TUBE_LARGE);
      } else {
        this.PARTS.add(Parts.DROP_TUBE_11);
      }
    }
    if (this.noseUp45CheckBox.isSelected() || this.noseDown45CheckBox.isSelected() || this.baseUp45CheckBox.isSelected()
        || this.baseDown45CheckBox.isSelected() || this.baseDown308CheckBox
            .isSelected()) {
      if (this.switchRadioButton.isSelected()) {
        this.PARTS.add(Parts.SWITCH_DROP_TUBE_13);
      } else if (this.collatorSensorCheckBox.isSelected()) {
        this.PARTS.add(Parts.DROP_TUBE_ALT_13);
        this.PARTS.add(Parts.SPRING_DROP_TUBE_LARGE);
      } else {
        this.PARTS.add(Parts.DROP_TUBE_13);
      }
    }
  }

  private void getBulletFeedDieParts() {
    if (this.bulletFeedDie7CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_7);
    }
    if (this.bulletFeedDie9CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_9);
    }
    if (this.bulletFeedDie32CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_32);
    }
    if (this.bulletFeedDie40CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_40);
    }
    if (this.bulletFeedDie44CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_44);
    }
    if (this.bulletFeedDie45CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_45);
    }
    if (this.bulletFeedDie223CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_223);
    }
    if (this.bulletFeedDie300CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_300);
    }
    if (this.bulletFeedDie308CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_308);
    }
    if (this.bulletFeedDie4570CheckBox.isSelected()) {
      this.PARTS.add(Parts.BULLET_FEED_DIE_4570);
    }
  }

  private void getAPPParts() {
    if (this.app9BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_9);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app32BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_32);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app357BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_357);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app40BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_40);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app44BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_44);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app45BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_45);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app57BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_57);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app65BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_65);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app762BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_762);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app223BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_223);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app270BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_270);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app308BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_308);
      this.PARTS.add(Parts.BASE_SLIDE);
    }

    if (this.app9BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_9);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_9);
    }
    if (this.app32BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_32);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_32);
    }
    if (this.app40BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_40);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_40);
    }
    if (this.app45BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_45);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_45);
    }
    if (this.app223BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_223);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_223);
    }
    if (this.app300BrassCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BRASS_INSERT_300);
      this.PARTS.add(Parts.BASE_SLIDE);
    }
    if (this.app308BulletCheckBox.isSelected()) {
      this.PARTS.add(Parts.APP_MAIN_BRACKET);
      this.PARTS.add(Parts.APP_BULLET_INSERT_308);
      this.PARTS.add(Parts.APP_BULLET_SLIDE_308);
    }
  }

  private boolean isNoseDown() {
    return (this.noseDown9CheckBox.isSelected() || this.noseDown40CheckBox.isSelected()
        || this.noseDown45CheckBox.isSelected() || this.noseDown223CheckBox
            .isSelected()
        || this.noseDown308CheckBox.isSelected() || this.noseDown300CheckBox.isSelected());
  }

  private boolean isBaseUp() {
    return (this.baseUp9CheckBox.isSelected() || this.baseUp40CheckBox.isSelected()
        || this.baseUp45CheckBox.isSelected());
  }

  private boolean isBaseDown() {
    return (this.baseDown9CheckBox.isSelected() || this.baseDown40CheckBox.isSelected()
        || this.baseDown45CheckBox.isSelected() || this.baseDown223CheckBox
            .isSelected()
        || this.baseDown308CheckBox.isSelected() || this.baseDown300CheckBox.isSelected());
  }

  private void getPDFFile() throws Exception {
    Document pdfFile = new Document(
        new PdfDocument(new PdfWriter(this.projectPathTextField.getText() + "/Settings.pdf")));
    int partNumber = 1;

    Table table = new Table(new float[] { 50.0F, 200.0F, 100.0F, 100.0F, 100.0F, 100.0F, 100.0F });
    table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph("#")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Part Name")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Layer Height")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Infill")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Supports")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Walls")).setTextAlignment(TextAlignment.CENTER));
    table.addCell(
        (Cell) (new Cell()).add((IBlockElement) new Paragraph("Adhesion")).setTextAlignment(TextAlignment.CENTER));

    for (Parts part : this.PARTS) {

      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(Integer.toString(partNumber++)))
          .setTextAlignment(TextAlignment.CENTER));
      table.addCell(
          (Cell) (new Cell()).add((IBlockElement) new Paragraph(part.getFile())).setTextAlignment(TextAlignment.LEFT));
      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(Double.toString(part.getLayerHeight())))
          .setTextAlignment(TextAlignment.CENTER));
      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(part.getInfill() + "%"))
          .setTextAlignment(TextAlignment.CENTER));
      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(part.isSupports() ? "60° Overhang" : "None"))
          .setTextAlignment(TextAlignment.CENTER));
      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(Integer.toString(part.getWalls())))
          .setTextAlignment(TextAlignment.CENTER));
      table.addCell((Cell) (new Cell()).add((IBlockElement) new Paragraph(part.getAdhesion()))
          .setTextAlignment(TextAlignment.CENTER));
    }
    pdfFile.add((IBlockElement) table);
    pdfFile.close();
  }

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
            PartsWindowController.this.projectPathTextField.getText()).toAbsolutePath().normalize();

        Path zipPath = projectRoot.resolve("Parts.zip");

        PartsWindowController.this.getPDFFile();

        try (ZipOutputStream zipOut = new ZipOutputStream(
            new FileOutputStream(zipPath.toFile()))) {

          Path settingsPath = projectRoot.resolve("Settings.pdf");

          try (FileInputStream input = new FileInputStream(settingsPath.toFile())) {
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
                    .replace(File.separatorChar, '/')));

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
  }

  @FXML
  private void getSTLFile(ActionEvent event) {
    try {
      final LinkedList<String> command;
      Task<Void> task;
      Thread thread;
      FXMLLoader loader;
      Parent root;
      PreviewWindowController controller;
      Stage stage;
      switch (((Button) event.getSource()).getText()) {

        case "Generate":
          command = getPlateSettings();
          command.addFirst(this.openSCADPathTextField.getText() + "/openscad");
          command.addLast("-o");
          command.addLast(this.projectPathTextField.getText() + "/" + this.descriptionTextField.getText() + ".stl");
          command.addLast(this.tempOpenSCADPath);

          this.previewButton.setDisable(true);
          this.stlStartButton.setText("Cancel");
          this.platesProgressBar.setVisible(true);
          this.generateLabel1.setText("Generating plate");
          this.generateLabel2.setText("Press Cancel to stop generation");
          task = new Task<Void>() {
            protected Void call() throws Exception {
              PartsWindowController.this.isCancelled = false;
              PartsWindowController.this.stlProcess = (new ProcessBuilder(command)).start();
              PartsWindowController.this.stlProcess.waitFor();
              (new MediaPlayer(new Media(PartsWindowController.class.getResource("ding.mp3").toString()))).play();
              Platform.runLater(() -> {
                PartsWindowController.this.previewButton.setDisable(false);
                PartsWindowController.this.stlStartButton.setText("Generate");
                PartsWindowController.this.platesProgressBar.setVisible(false);
                if (PartsWindowController.this.isCancelled) {
                  PartsWindowController.this.generateLabel1.setText("Plate generation cancelled!");
                  PartsWindowController.this.generateLabel2.setText("");
                } else {
                  PartsWindowController.this.generateLabel1.setText("Plate generation completed!");
                  PartsWindowController.this.generateLabel2
                      .setText(PartsWindowController.this.descriptionTextField.getText() + ".stl");
                }
              });
              return null;
            }
          };
          thread = new Thread((Runnable) task);
          thread.setDaemon(true);
          thread.start();
          break;
        case "Cancel":
          this.isCancelled = true;
          this.stlProcess.destroy();
          break;

        case "Preview":
          loader = new FXMLLoader(getClass().getResource("PreviewWindow.fxml"));
          root = (Parent) loader.load();
          controller = (PreviewWindowController) loader.getController();

          command = getPlateSettings();
          command.addFirst(this.openSCADPathTextField.getText() + "/openscad");
          command.addLast("--preview");
          command.addLast("--imgsize=500,500");
          command.addLast("-o");
          command.addLast(this.projectPathTextField.getText() + "/preview.png");
          command.addLast(this.tempOpenSCADPath);
          this.stlProcess = (new ProcessBuilder(command)).start();
          this.stlProcess.waitFor();

          controller.setSTLPreview(this.projectPathTextField.getText());
          stage = new Stage();
          stage.setScene(new Scene(root));
          stage.setTitle("Collator Plate Preview");
          stage.setResizable(false);
          stage.show();
          break;
      }
    } catch (Exception e) {
      if (this.stlProcess.isAlive()) {
        this.isCancelled = true;
        this.stlProcess.destroy();
      }
    }
  }

  @FXML
  private void setPlateSettings(ActionEvent event) {
    CollatorPlates plate;
    switch (((RadioButton) event.getSource()).getText()) {
      case "9mm":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_9;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_9;
        break;

      case ".40 S&W":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_40;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_40;
        break;

      case ".45 ACP":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_45;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_45;
        break;

      case ".50":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_50;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_50;
        break;

      case "Large Pistol":
        if (this.baseUpRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_UP_BRASS_LARGE_PISTOL;
          break;
        }
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_LARGE_PISTOL;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LARGE_PISTOL;
        break;

      case ".223":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_223;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_223;
        break;

      case "6.5mm":
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_65;
        break;
      case "300 BLK":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_300;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_300;
        break;

      case ".308":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_308;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_308;
        break;

      case ".45-70":
        plate = CollatorPlates.BASE_DOWN_BRASS_4570;
        break;
      case "Small Rifle":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_SMALL_RIFLE;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_SMALL_RIFLE;
        break;

      case "Large Rifle":
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_LARGE_RIFLE;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LARGE_RIFLE;
        break;

      case "Long Rifle":
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_LONG_RIFLE;
        break;
      default:
        if (this.baseUpRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_UP_BRASS_SMALL_PISTOL;
          break;
        }
        if (this.baseDownRadioButton.isSelected()) {
          plate = CollatorPlates.BASE_DOWN_BRASS_SMALL_PISTOL;
          break;
        }
        plate = CollatorPlates.NOSE_UP_DOWN_BULLET_SMALL_PISTOL;
        break;
    }

    this.descriptionTextField.setText(plate.getDescription());
    this.caliberSpinner.getValueFactory().setValue(Double.valueOf(plate.getCaliber()));
    this.plateHeightSpinner.getValueFactory().setValue(Double.valueOf(plate.getPlateHeight()));
    this.isLongRifleBulletCheckBox.setSelected(plate.isIsLongRifleBullet());
    this.addRampsCheckBox.setSelected(plate.isAddRamps());
    this.isRifleBrassCheckBox.setSelected(plate.isIsRifleBrass());
    this.rifleHoleWidthSpinner.getValueFactory().setValue(Double.valueOf(plate.getRifleHoleWidth()));
    this.addPivotsCheckBox.setSelected(plate.isAddPivots());
    this.addSlidesCheckBox.setSelected(plate.isAddSlides());
    this.addRidgesCheckBox.setSelected(plate.isAddRidges());
    this.ridgeCenterCheckBox.setSelected(plate.isRidgeCenter());
    this.ridgeAlternateCheckBox.setSelected(plate.isRidgeAlternate());
    this.ridgeHeightSpinner.getValueFactory().setValue(Double.valueOf(plate.getRidgeHeight()));
    this.ridgeLengthSpinner.getValueFactory().setValue(Double.valueOf(plate.getRidgeLength()));
    this.addBevelCheckBox.setSelected(plate.isAddBevel());
    this.bevelSizeSpinner.getValueFactory().setValue(Double.valueOf(plate.getBevelSize()));
    this.holeMultiplierSpinner.getValueFactory().setValue(Double.valueOf(plate.getHoleMultiplier()));
    this.useClutchCheckBox.setSelected(plate.isUseClutch());
    this.useHexCheckBox.setSelected(plate.isUseHex());
    this.addHexHandleCheckBox.setSelected(plate.isAddHexHandle());
    this.hxwSpinner.getValueFactory().setValue(Double.valueOf(plate.getHxw()));
    this.shaftHoleSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftHole()));
    this.shaftSlotLengthSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftSlotLength()));
    this.shaftSlotWidthSpinner.getValueFactory().setValue(Double.valueOf(plate.getShaftSlotWidth()));
    this.fnSpinner.getValueFactory().setValue(Integer.valueOf(plate.getFn()));
    this.plateDiameterSpinner.getValueFactory().setValue(Double.valueOf(plate.getPlateDiameter()));
    this.bulletCaliberSpinner.getValueFactory().setValue(Double.valueOf(plate.getBulletCaliber()));
  }

  private LinkedList<String> getPlateSettings() {
    LinkedList<String> settings = new LinkedList<>();

    settings.add("-D");
    settings.add("\"description=\\\"" + this.descriptionTextField.getText() + "\\\"\"");
    settings.add("-D");
    settings.add("\"caliber=" + this.caliberSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"collator_plate_h=" + this.plateHeightSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"isLongRifleBullet=" + this.isLongRifleBulletCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"addRamps=" + this.addRampsCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"isRifleBrassPlate=" + this.isRifleBrassCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"rifleHoleWidth=" + this.rifleHoleWidthSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"addPivots=" + this.addPivotsCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"addSlides=" + this.addSlidesCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"addRidges=" + this.addRidgesCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"ridgeCenter=" + this.ridgeCenterCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"ridgeAlternate=" + this.ridgeAlternateCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"ridgeHeight=" + this.ridgeHeightSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"ridgeLength=" + this.ridgeLengthSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"addBevel=" + this.addBevelCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"bevelSize=" + this.bevelSizeSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"hole_multiplier=" + this.holeMultiplierSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"useClutch=" + this.useClutchCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"useHex=" + this.useHexCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"addHexHandle=" + this.addHexHandleCheckBox.isSelected() + "\"");
    settings.add("-D");
    settings.add("\"hexHandleHeight=" + this.hexHandleHeightSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"hxw=" + this.hxwSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"shaft_hole=" + this.shaftHoleSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"shaft_slot_length=" + this.shaftSlotLengthSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"shaft_slot_width=" + this.shaftSlotWidthSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"$fn=" + this.fnSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"collator_plate_d=" + this.plateDiameterSpinner.getValue() + "\"");
    settings.add("-D");
    settings.add("\"bullet_caliber=" + (((Double) this.caliberSpinner.getValue()).doubleValue()
        + ((Double) this.bulletCaliberSpinner.getValue()).doubleValue()) + "\"");

    return settings;
  }

  private String getProjectPath() {
    if ((new File(System.getProperty("user.dir") + "/1. Main Body")).exists()) {
      return System.getProperty("user.dir");
    }
    return "The project folder was not found. Place the .jar file in the project folder or press the Change button to select the project location.";
  }

  private String getOpenSCADpath() {
    String[] locations = { System.getenv("SystemDrive") + "/Program Files/OpenSCAD",
        System.getenv("SystemDrive") + "/Program Files (x86)/OpenSCAD" };

    if ((new File(locations[0])).exists())
      return (new File(locations[0])).getAbsolutePath();
    if ((new File(locations[1])).exists()) {
      return (new File(locations[1])).getAbsolutePath();
    }
    return "OpenSCAD was not found on your computer! Download it at http://www.openscad.org/";
  }

  private String getOpenSCADfile() {
    String tempFilePath = "";

    try {
      tempFilePath = File.createTempFile("generator", ".scad").getAbsolutePath();
      Scanner input = new Scanner(PartsWindowController.class.getResource("generator.scad").openStream());
      PrintWriter output = new PrintWriter(tempFilePath);

      while (input.hasNextLine()) {
        output.println(input.nextLine());
      }
      input.close();
      output.close();
      (new File(tempFilePath)).deleteOnExit();
    } catch (Exception exception) {
    }

    return tempFilePath;
  }
}

/*
 * Location: /Users/shanelupton/Downloads/Feeder-main/Parts
 * Generator.jar!/partsgenerator/PartsWindowController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */