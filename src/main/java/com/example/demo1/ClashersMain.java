package com.example.demo1;


import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ClashersMain extends Application {
    GUI gui = new GUI();
    static Map map = new Map("Map", 0, null);
    Gameplay game = new Gameplay();
    private Fighter player1 = new Blank(),
            player2 = new Blank();
    Pane fighterRoot = new Pane();
    private boolean currentRound = false, paused = false,
             singleplayer = false, training = false, multiplayer = false,
    isReady1 = false, isReady2 = false, fSet1 = false, fSet2 = false;
    Scene curScene = new Scene(initMainMenu());
    Stage primaryStage = new Stage();
    static boolean forward = false, isMax = false, backward = false, jump = false;
    static final double  fps = 60;

    public void resetReady(){
        isReady2 = false;
        isReady1 = false;
        fSet2 = false;
        fSet1 = false;
    }
    private Parent initMainMenu(){ //Sets up the beginning screen for the game

        Pane mainMenuRoot = new Pane();

        VBox leftVBox = new VBox(50);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);


        Button btnPlay = new Button("PLAY"); //These buttons kinda explain themselves
        btnPlay.setOnAction(event -> curScene.setRoot(modeSelect()));

        btnPlay.setPrefSize(200,100);

        Button btnSet = new Button("SETTINGS");
        btnSet.setOnAction(event -> curScene.setRoot(settings()));
        btnSet.setPrefSize(200,100);

        Button btnQuit = new Button("QUIT");
        btnQuit.setCancelButton(true);
        btnQuit.setOnAction(event -> quit());
        btnQuit.setPrefSize(200,100);

        VBox buttonsVbox = new VBox(50);
        buttonsVbox.setTranslateX(gui.getWidth()/2-100);
        buttonsVbox.setTranslateY(200);
        buttonsVbox.setAlignment(Pos.CENTER);

        buttonsVbox.getChildren().addAll(btnPlay, btnSet, btnQuit);
        mainMenuRoot.getChildren().addAll(gui.setBaseScreen(mainMenuRoot), buttonsVbox);


        return mainMenuRoot;
    }
    private Parent initCharSelect(){ //Sets up the Character Selection Menu
        Pane charRoot = new Pane();
        Rectangle iconFlash = new Rectangle(200*gui.getScalar(), 200,600,500); //Sets up the little icon flash that appears when selecting a character
        Rectangle icon = new Rectangle(0, gui.getHeight()-400,400,300);
        Rectangle icon2 = new Rectangle(600*gui.getScalar(), gui.getHeight()-400,400,300);



        Button btnBack = new Button("BACK");
        btnBack.setPrefSize(150*gui.getScalar(),100);
        btnBack.setCancelButton(true);
        btnBack.setOnAction(event -> {
            curScene.setRoot(modeSelect());
            singleplayer = false;
            multiplayer = false;
            training = false;
            player2 = new Blank();
            player1 = new Blank();
            resetReady();
        });
        HBox back = new HBox();
        back.setAlignment(Pos.BOTTOM_LEFT);
        ArrayList<Button> Fighters = new ArrayList<>();


        Fighters.add(new Button("WYLL")); //BALDURS
        Fighters.get(0).setOnAction(actionEvent -> {

            if (!fSet1) {

                player1 = new Wyll("Player 1",350,2,5,175,100,fighterRoot,"left"); //Switches the player1 Fighter from "Blank" to a real fighter
                gui.flashSetUp(charRoot,iconFlash);
                try {
                    gui.flashStop(); //Overrides if another flash is currently in motion
                    FileInputStream input = new FileInputStream("src/Images/Character Images/Wyll Icon.png");
                    gui.iconSetUp(charRoot,iconFlash,icon,input);
                    gui.flashStart();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);

                }
                fSet1 = true;
            } else {

                player2 = new Wyll("Wyll",350,2,5,175,100,fighterRoot,"right"); //Switches the player1 Fighter from "Blank" to a real fighter
                gui.flashSetUp(charRoot,iconFlash);
                try {
                    gui.flashStop();
                    FileInputStream input = new FileInputStream("src/Images/Character Images/Wyll Icon.png");
                    gui.iconSetUp(charRoot,iconFlash,icon2,input);
                    gui.flashStart();
                } catch (FileNotFoundException q) {
                    throw new RuntimeException(q);
                }
                fSet2 = true;
            }
        });

        Fighters.add(new Button("MONKE")); //BlOONS
        Fighters.get(1).setOnAction(actionEvent -> {
            if (!fSet1) {

                player1 = new Monke("Monke",220,3,3,135,90,fighterRoot,"left");
                gui.flashSetUp(charRoot,iconFlash);
                try {
                    gui.flashStop();
                    FileInputStream input = new FileInputStream("src/Images/Character Images/Monke Icon.png");
                    gui.iconSetUp(charRoot,iconFlash,icon,input);
                    gui.flashStart();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                fSet1 = true;
            } else {
                Fighter fighter2 = new Monke("Monke",220,3,3,135,90,fighterRoot,"left");
                gui.flashSetUp(charRoot,iconFlash);
                try {
                    gui.flashStop();
                    FileInputStream input = new FileInputStream("src/Images/Character Images/Monke Icon.png");
                    gui.iconSetUp(charRoot,iconFlash,icon2,input);
                    gui.flashStart();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                fSet2 = true;
            }
        });

        Fighters.add(new Button("SKELLINGTON")); //GUNGEON
        Fighters.get(2).setOnAction(actionEvent -> {
            player1 = new Skellington("Skellington",440,1,7,140,105,fighterRoot,"left");
            gui.flashSetUp(charRoot,iconFlash);
            if (!fSet1) {
                fSet1 = true;
            } else {
                fSet2 = true;
            }
        });

        Fighters.add(new Button("MIMI")); //MIMIC
        Fighters.get(3).setOnAction(actionEvent -> {
            player1 = new Mimi("Mimi",240,1,2,260,120,fighterRoot,"left");
            gui.flashSetUp(charRoot,iconFlash);
            try {
                gui.flashStop();
                FileInputStream input = new FileInputStream("src/Images/Character Images/Mimi Icon.png");
                gui.iconSetUp(charRoot,iconFlash,icon,input);
                gui.flashStart();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (!fSet1) {
                fSet1 = true;
            } else {
                fSet2 = true;
            }
        });

        Fighters.add(new Button("TODD")); //THACHER
        Fighters.get(4).setOnAction(actionEvent -> {
            player1 = new Todd("Todd",200,3,3,260,95,fighterRoot,"left");
            gui.flashSetUp(charRoot,iconFlash);
            if (!fSet1) {
                fSet1 = true;
            } else {
                fSet2 = true;
            }
        });

        for (Button fighter : Fighters) {
            fighter.setPrefSize(200 * gui.getScalar(), 100);
        }



        HBox fButtonsHbox = new HBox();
        fButtonsHbox.setAlignment(Pos.TOP_LEFT);
        fButtonsHbox.setTranslateY(100);
        fButtonsHbox.getChildren().addAll(Fighters); //Sets up the button Layout for Fighters

        Button startButton = new Button("START");
        startButton.setOnAction(actionEvent -> curScene.setRoot(initMapSelect()));
        startButton.setDefaultButton(true);
        startButton.setPrefSize(200*gui.getScalar(),75*gui.getScalar());
        startButton.setDisable(true);

        ToggleButton p1Ready = new ToggleButton("READY1"); //The button that tells the game that player1 is ready
        p1Ready.setPrefSize(400,100);
        p1Ready.setOnAction(actionEvent -> {
            isReady1 = !isReady1;
            if (singleplayer && isReady1 && fSet1){
                startButton.setDisable(false); //Start button will only be enabled if all present players are ready
            } else startButton.setDisable(!multiplayer || !isReady1 || !isReady2 || !fSet2 || !fSet1);
        });

        HBox ready1 = new HBox(0);
        ready1.setAlignment(Pos.BOTTOM_LEFT);
        ready1.setTranslateY(gui.getHeight()-100);
        startButton.setTranslateY(-100);
        startButton.setTranslateX(100*gui.getScalar());
        back.getChildren().add(btnBack);
        ready1.getChildren().addAll(p1Ready, startButton);
        if (multiplayer) {
            ToggleButton p2Ready = new ToggleButton("READY2"); //The button that tells the game that player2 is ready
            p2Ready.setPrefSize(400, 100);
            p2Ready.setOnAction(actionEvent -> {
                isReady2 = !isReady2;
                startButton.setDisable(!multiplayer || !isReady1 || !isReady2 || !fSet2 || !fSet1);
            });
            HBox ready2 = new HBox();
            ready2.setAlignment(Pos.BOTTOM_RIGHT);
            ready2.setTranslateY(gui.getHeight()-100);
            ready2.setTranslateX(gui.getWidth()-400);
            ready2.getChildren().add(p2Ready);
            charRoot.getChildren().addAll(gui.setBaseScreen(charRoot), fButtonsHbox,ready1,ready2,back);
        }
        if (singleplayer) {
            charRoot.getChildren().addAll(gui.setBaseScreen(charRoot), fButtonsHbox,ready1,back);
        }




        return charRoot;
    }
    private Parent initMapSelect(){ //Creates the Map Selection Screen
        Pane mapSelRoot = new Pane();

        Rectangle mapIcon = new Rectangle(gui.getWidth()/2-225*gui.getScalar(), gui.getHeight()-500, 500*gui.getScalar(), 400);


        Button btnBack = new Button("BACK"); //Button Set Up
        btnBack.setPrefSize(150*gui.getScalar(),100);
        btnBack.setCancelButton(true);
        btnBack.setOnAction(event -> { // Goes back to Character Select and Resets the "Ready" buttons
            curScene.setRoot(initCharSelect());
            resetReady();
        });
        HBox back = new HBox();
        back.getChildren().add(btnBack);

        ArrayList<Button> Maps = new ArrayList<Button>(); //Creates the Map buttons
        Maps.add(new Button("Thunderdome"));
        Maps.get(0).setOnAction(actionEvent -> {});
        Maps.add(new Button("Gymkhana Field"));
        Maps.add(new Button("Taki Bag"));
        Maps.add(new Button("Battle of the Bands"));
        Maps.add(new Button("P.A.C"));
        Maps.get(4).setOnAction(actionEvent -> { //Sets up Arena Background
           try {
               map.mapSetup(Maps.get(4).getText(),gui.getWidth()*2,mapIcon,new FileInputStream("src/Images/Map Icons/PAC.jpg"),new FileInputStream("src/Images/Map Images/PAC.jpg"));
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }

        });
        Maps.add(new Button("Boxing Ring"));
        for (Button button : Maps) {
            button.setPrefSize(200 * gui.getScalar(), 100);
        }
       // Maps.


        HBox fButtonsHbox = new HBox();
        fButtonsHbox.setAlignment(Pos.TOP_LEFT);
        fButtonsHbox.setTranslateY(100);
        fButtonsHbox.getChildren().addAll(Maps);


        Button startButton = new Button("START");
        startButton.setOnAction(actionEvent -> {
            curScene.setRoot(initLoading());
            //curScene.setRoot(Map());
        });
        startButton.setDefaultButton(true);
        startButton.setPrefSize(200,75);
        startButton.setAlignment(Pos.BOTTOM_CENTER);
        startButton.setTranslateY(gui.getHeight()-100);
        startButton.setTranslateX(gui.getWidth()/2-50*gui.getScalar());



        mapSelRoot.getChildren().addAll(gui.setBaseScreen(mapSelRoot), fButtonsHbox, back, startButton, mapIcon);
        return mapSelRoot;
    }
    private Parent initLoading(){ //Creates the Loading Screen... For some Reason
        Pane loadingRoot = new Pane();

        Text mapName = new Text(500*gui.getScalar(),200,"The\n"+map.getName());
        mapName.setStyle("-fx-text-fill: rgb(220,255,180)");
        mapName.setScaleX(10*gui.getScalar());
        mapName.setScaleY(10*gui.getScalar());
        Rectangle loading = new Rectangle(gui.getWidth()-150, gui.getHeight()-150,100*gui.getScalar(),100*gui.getScalar());

        player1.setFighterY(player1.getGroundHeight()); //Places players at their locations
        player1.setFighterX(700);
        player2.setFighterY(player2.getGroundHeight());
        player2.setFighterX(300);
        player1.setState(charState.Neutral);
        player2.setState(charState.Neutral);
        player1.pickOnBoundsProperty().set(true);
        player2.pickOnBoundsProperty().set(true);
        player1.getFighterHealthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Player 1 Health: " + newValue);
        });
        player2.getFighterHealthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Player 2 Health: " + newValue);
        });

        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(360f);
        rotate.setDuration(Duration.seconds(1));
        rotate.setNode(loading);
        rotate.setCycleCount(2);
        rotate.setOnFinished(event -> curScene.setRoot(Map()));
        rotate.playFromStart();
        try {
            FileInputStream input = new FileInputStream("src/Images/Map Images/Loading.png");
            Image image = new javafx.scene.image.Image(input);
            ImagePattern image_pattern = new ImagePattern(image);
            loading.setFill(image_pattern);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        loadingRoot.getChildren().addAll(gui.setBaseScreen(loadingRoot), loading, mapName);
        return loadingRoot;
    }
    private List<Sprite> sprites(){
        return curScene.getRoot().getChildrenUnmodifiable().stream().filter(node -> node.getClass().equals(Sprite.class)).map(n -> (Sprite)n).collect(Collectors.toList());
    } //Creates a List of ALL Sprites on Screen
    private List<Fighter> fighters(){
        return curScene.getRoot().getChildrenUnmodifiable().stream().filter(node -> Fighter.class.isAssignableFrom(node.getClass())).map(n -> (Fighter)n).collect(Collectors.toList());
    } //Creates a List of ALL Fighters on Screen
    private List<Attack> attacks(){
        return curScene.getRoot().getChildrenUnmodifiable().stream().filter(node -> Attack.class.isAssignableFrom(node.getClass())).map(n -> (Attack)n).collect(Collectors.toList());
    }
    private Parent Map(){ //Generates the Map and starts the Timer
        Pane mapRoot = new Pane();
        mapRoot.setPrefSize(gui.getWidth(), gui.getHeight());
        Pane cinBars = new Pane(); //Adds Black Bars on Top and Bottom of Screen for Cinematic Effect
        Rectangle cinBarTop = new Rectangle(0, 0, gui.getWidth(), 100);
        Rectangle cinBarBottom = new Rectangle(0, gui.getHeight()-100, gui.getWidth(), 100);
        cinBarBottom.setFill(Color.BLACK);
        cinBarTop.setFill(Color.BLACK);
        cinBars.getChildren().addAll(cinBarTop,cinBarBottom);
        Rectangle arenaBG = new Rectangle(-map.getWidth()/4-50, 0, map.getWidth(), gui.getHeight()); //Creates the actual Arena that will be battled on
        map.genMap(arenaBG);



        mapRoot.getChildren().addAll(arenaBG,cinBars, player1,player2);
        currentRound = true;
        gui.setGameScreen(mapRoot);
        AnimationTimer timer = new AnimationTimer() { //Starts the Game Timer
            @Override
            public void handle(long now) {
                if (!paused) {
                    game.update(sprites(),fighters(),attacks(),(Pane)curScene.getRoot(),gui);
                    map.update(arenaBG,fighters());
                    gui.updateGameScreen(mapRoot);
                    //gui.damage(player2,1);
                }
                else {
                    if (!new HashSet<>(mapRoot.getChildren()).containsAll(pauseMenu())) {
                        mapRoot.getChildren().addAll(pauseMenu());
                    }
                }
            }
        };
        timer.start();
        return mapRoot;
    }
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        primaryStage.setTitle("Classic Clashers");
            primaryStage.setScene(curScene);
            primaryStage.show();


        curScene.setOnKeyPressed(new EventHandler<KeyEvent>() { //All my button pressed doohickeys
            @Override
            public void handle(KeyEvent event) {
                if (currentRound) {
                    switch (event.getCode()) {
                        case LEFT:
                            if (player1.isNotBusy())
                                if (player1.getOrient().equals("right"))
                                    player1.setState(charState.Backing);
                                else if (player1.getOrient().equals("left"))
                                    player1.setState(charState.Walking);
                            break;
                        case RIGHT:
                            if (player1.isNotBusy())
                                if (player1.getOrient().equals("left"))
                                    player1.setState(charState.Backing);
                                else if (player1.getOrient().equals("right"))
                                    player1.setState(charState.Walking);
                            break;
                        case DOWN:
                            if (player1.isNotBusy())
                                player1.setState(charState.Crouching);
                            break;
                        case ESCAPE:
                            paused = !paused;
                            System.out.println("paused = " + paused);
                            break;
                        case SPACE:
                            if (player1.isNotBusy())
                                player1.jump();
                            break;
                        case J:
                            if (player1.isNotBusy()) {
                                player1.attack();
                                game.renderAttacks(player1,(Pane)curScene.getRoot());
                            }
                            break;
                        case K:
                            if (player1.isNotBusy())
                                player1.special();
                            break;
                    }

                }
            }
        });
        curScene.setOnKeyReleased(new EventHandler<KeyEvent>() { //This is important as a backup, in case the game doesn't know a character should be in neutral

            @Override
            public void handle(KeyEvent event) {
                if (currentRound) {
                    if (player1.isNotBusy())
                        player1.setState(charState.Neutral);
                }
            }
        });





    }
    private List<Node> pauseMenu() {
        Pane pauseRoot = new Pane();

        //gui.setBaseScreen(pauseRoot).setStyle("-fx-background-color: rgb(0,0,0,10%)");
        gui.setBaseScreen(pauseRoot).setStyle("-fx-background-color: rgb(255,255,255,)");
        Text pausedText = new Text(500*gui.getScalar(),200,"PAUSED");
        pausedText.setStyle("-fx-text-fill: rgb(0,0,0)");
        pausedText.setScaleX(10*gui.getScalar());
        pausedText.setScaleY(10*gui.getScalar());

        Button mList = new Button("Move List");
        mList.setPrefSize(200,100);
        mList.setOnAction(event -> {

        });


        Button resume = new Button("Resume");
        resume.setPrefSize(200,100);
        resume.setOnAction(event -> {
        });

        Button quit = new Button("Quit");
        quit.setPrefSize(200,100);
        quit.setOnAction(event -> {
            resetReady();
            curScene.setRoot(initCharSelect());

        });

        VBox buttons = new VBox();
        buttons.setSpacing(75);
        pauseRoot.setBackground(new Background(new BackgroundFill(new Color(0,0,0,0.3), null, null)));
        buttons.getChildren().addAll(mList,resume,quit);
        pauseRoot.getChildren().addAll(buttons,pausedText);
        paused = false;
        return pauseRoot.getChildren();
    }
    private void Victory() throws IOException {}
    private Parent settings() {
        Stage stage = (Stage) curScene.getWindow();
        Pane SetRoot = new Pane();

        Button btnSound = new Button("SOUND");
        btnSound.setOnAction(event -> {});
        btnSound.setPrefSize(200,100);
        ToggleGroup Full = new ToggleGroup();
        ToggleButton btnFullP = new ToggleButton("ON");
        btnFullP.setToggleGroup(Full);
        ToggleButton btnFullN = new ToggleButton("OFF");
        btnFullN.setToggleGroup(Full);
        btnFullN.setSelected(true);
        Full.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, final Toggle toggle, final Toggle new_toggle) {
                isMax = Full.getSelectedToggle() == btnFullP;
                stage.setFullScreenExitHint("");
                stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                stage.setFullScreen(isMax);
                gui = new GUI();
                gui.setBaseScreen(SetRoot);
                curScene.setRoot(settings());
            }
        });

        btnFullP.setPrefSize(200,100);
        btnFullN.setPrefSize(200,100);

        HBox fullbtns = new HBox(20);
        fullbtns.setAlignment(Pos.CENTER);
        Text textFull = new Text("Fullscreen");
//textFull.set("-fx-background-color: rgb(255)");
        fullbtns.getChildren().addAll(textFull,btnFullP,btnFullN);

        Button btnBack = new Button("BACK");
        btnBack.setPrefSize(200,100);
        btnBack.setCancelButton(true);
        btnBack.setOnAction(event -> {
            curScene.setRoot(initMainMenu());
        });

        VBox buttonsVbox = new VBox(50);
        buttonsVbox.setTranslateX(gui.getWidth()/2-275);
        buttonsVbox.setTranslateY(200);
        buttonsVbox.setAlignment(Pos.CENTER);

        buttonsVbox.getChildren().addAll(btnSound, fullbtns, btnBack);
        SetRoot.getChildren().addAll(gui.setBaseScreen(SetRoot), buttonsVbox);
        return SetRoot;

    }
    private Parent modeSelect() {

        Pane ModeSelRoot = new Pane();

        Button btn1P = new Button("STORY");
        btn1P.setPrefSize(200,100);
        btn1P.setOnAction(event -> {
            singleplayer = true;
            curScene.setRoot(initCharSelect());
        });
        Button btn1T = new Button("TUTORIAL");
        btn1T.setPrefSize(200,100);
        btn1T.setOnAction(event -> {
            singleplayer = true;
            training = true;
            curScene.setRoot(initCharSelect());
            player2 = new PBag(fighterRoot);
        });
        Button btn2P = new Button("VERSUS");
        btn2P.setPrefSize(200,100);
        btn2P.setOnAction(event -> {
            multiplayer = true;
            curScene.setRoot(initCharSelect());
        });
        Button btnBack = new Button("BACK");
        btnBack.setPrefSize(200,100);
        btnBack.setCancelButton(true);
        btnBack.setOnAction(event -> {
            curScene.setRoot(initMainMenu());
        });

        VBox buttonsVbox = new VBox(50);
        buttonsVbox.setTranslateX(gui.getWidth()/2-225);
        buttonsVbox.setTranslateY(200);
        buttonsVbox.setAlignment(Pos.CENTER);


        HBox buttonsHbox = new HBox(50);
        buttonsHbox.setAlignment(Pos.CENTER);

        buttonsHbox.getChildren().addAll(btn1P, btn1T);
        buttonsVbox.getChildren().addAll(buttonsHbox, btn2P, btnBack);

        ModeSelRoot.getChildren().addAll(gui.setBaseScreen(ModeSelRoot), buttonsVbox);
        return ModeSelRoot;
    }
    private void quit(){
        Platform.exit();
    }
    public static void main(String[] args) {
        launch();
    }
}
