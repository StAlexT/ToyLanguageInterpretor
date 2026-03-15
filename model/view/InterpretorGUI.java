package model.view;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.controller.Controller;
import model.expression.*;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.*;
import model.state.files.FileTable;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BooleanNumber;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;

import java.util.List;
import java.util.Map;

public class InterpretorGUI extends Application{

    private List<Statement> programList;
    private Controller controller;

    private TextField nrPrgState;
    private TableView<Map.Entry<Integer, Value>> heapTable;
    private ListView<String> outList;
    private ListView<String> fileTableList;
    private ListView<Integer> prgStateIdentifiersList;
    private TableView<Map.Entry<String, Value>> symTable;
    private ListView<String> exeStackList;
    private Button runOneStepButton;

    private ProgramState selectedPrg;
    private List<String> programNames;

    @Override
    public void start(Stage stage) throws Exception {
        Statement simpleFork =
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement(
                                        "v",
                                        new ValueExpression(new IntegerValue(1))
                                ),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new AssignmentStatement(
                                                                "v",
                                                                new ValueExpression(new IntegerValue(2))
                                                        ),
                                                        new PrintStatement(
                                                                new VariableExpression("v")
                                                        )
                                                )
                                        ),
                                        new PrintStatement(
                                                new VariableExpression("v")
                                        )
                                )
                        )
                );
        Statement ref4 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new IntType())),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntegerValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WritingHeapStatement("a", new ValueExpression(new IntegerValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadingHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new BoolType()),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("b", new BoolType()),
                                        new CompoundStatement(
                                                new AssignmentStatement("a", new ValueExpression(new BooleanNumber(true))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("b", new ValueExpression(new BooleanNumber(true))),
                                                        new CompoundStatement(
                                                                new IfStatement(
                                                                        new LogicalExpression(
                                                                                new VariableExpression("a"),
                                                                                new VariableExpression("b"),
                                                                                1
                                                                        ),
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3))),
                                                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2)))
                                                                ),
                                                                new PrintStatement(new VariableExpression("v"))
                                                        )

                                                )
                                        )
                                )
                        )
                )
        );
        Statement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("a",
                                new ArithmeticExpression(
                                        new ValueExpression(new IntegerValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntegerValue(3)),
                                                new ValueExpression(new IntegerValue(5)),
                                                3
                                        ),
                                        1
                                )
                        ),
                        new CompoundStatement(
                                new VariableDeclarationStatement("b", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ArithmeticExpression(
                                                                new ArithmeticExpression(
                                                                        new ValueExpression(new IntegerValue(4)),
                                                                        new ValueExpression(new IntegerValue(0)),
                                                                        4
                                                                ),
                                                                new ValueExpression(new IntegerValue(7)),
                                                                1
                                                        ),
                                                        2
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        Statement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new AssignmentStatement("a", new ValueExpression(new BooleanNumber(false))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        Statement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.txt"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        //(case (b*c) : print(a);print(b))
        //(case (10) : print(100);print(200))
        //(default : print(300)));
        //print(300)
        //The final Out should be {1,2,300}
        Statement exSwitch = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement("a", new ValueExpression(new IntegerValue(1))),
                                        new CompoundStatement(
                                                new AssignmentStatement("b", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("c", new ValueExpression(new IntegerValue(5))),
                                                        new CompoundStatement(
                                                                new SwitchStatement(
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("a"),
                                                                                new ValueExpression(new IntegerValue(10)),
                                                                                3
                                                                        ),
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("b"),
                                                                                new VariableExpression("c"),
                                                                                3
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("a")),
                                                                                new PrintStatement(new VariableExpression("b"))
                                                                        ),
                                                                        new ValueExpression(new IntegerValue(10)),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ValueExpression(new IntegerValue(100))),
                                                                                new PrintStatement(new ValueExpression(new IntegerValue(200)))
                                                                        ),
                                                                        new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );


        //v=20;
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
        //print(v*10)
        //The final Out should be {0,1,2,30}

        Statement exFor = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new ForStatement("v",
                                        new ValueExpression(new IntegerValue(0)),
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(3)),
                                                "<"
                                        ),
                                        new ArithmeticExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(1)),
                                                1
                                        ),
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new AssignmentStatement("v",
                                                                new ArithmeticExpression(
                                                                        new VariableExpression("v"),
                                                                        new ValueExpression(new IntegerValue(1)),
                                                                        1
                                                                )
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new ArithmeticExpression(
                                        new VariableExpression("v"),
                                        new ValueExpression(new IntegerValue(10)),
                                        3)
                                )
                        )
                )
        );

        //v=0;
        //( while(v<3) (fork(print(v);v=v+1);v=v+1   );
        //sleep(5);
        //print(v*10)
        //The final Out should be {0,1,2,30}

        Statement exSleep = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(0))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(3)), "<"),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")),
                                                                new AssignmentStatement( "v",
                                                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)),1)
                                                                )
                                                        )
                                                ),
                                                new AssignmentStatement( "v",
                                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)),1)
                                                )
                                        )
                                ),
                                new CompoundStatement(
                                        new SleepStatement(new ValueExpression(new IntegerValue(5))),
                                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3))
                                )
                        )
                )

        );


        //v=20; wait(10);print(v*10)
        //The final Out should be {20,10,9,8,7,6,5,4,3,2,1,200}

        Statement exWait = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(20))),
                        new CompoundStatement(
                                new WaitStatement(new ValueExpression(new IntegerValue(10))),
                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3))
                        )
                )
        );

        //v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3);
        //x=1;y=2;z=3;w=4;
        //print(v*10)
        //The final Out should be {0,1,2,30}

        Statement exRepeatUntil = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(0))),
                        new CompoundStatement(
                                new RepeatUntilStatement(
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")),
                                                                new AssignmentStatement( "v",
                                                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)),2)
                                                                )
                                                        )
                                                ),
                                                new AssignmentStatement( "v",
                                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(1)),1)
                                                )
                                        ),
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntegerValue(3)),
                                                "==")
                                ),
                                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntegerValue(10)), 3))
                        )
                )

        );

        //bool b;
        //int c;
        //b=true;
        //c=b?100:200;
        //print(c);
        //c= (false)?100:200;
        //print(c);
        //The final Out should be {100,200}
        Statement exCondAssign =
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new BoolType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement(
                                                "b",
                                                new ValueExpression(new BooleanNumber(true))
                                        ),
                                        new CompoundStatement(
                                                new ConditionalAssignmentStatement(
                                                        "c",
                                                        new VariableExpression("b"),
                                                        new ValueExpression(new IntegerValue(100)),
                                                        new ValueExpression(new IntegerValue(200))
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("c")),
                                                        new CompoundStatement(
                                                                new ConditionalAssignmentStatement(
                                                                        "c",
                                                                        new ValueExpression(new BooleanNumber(false)),
                                                                        new ValueExpression(new IntegerValue(100)),
                                                                        new ValueExpression(new IntegerValue(200))
                                                                ),
                                                                new PrintStatement(new VariableExpression("c"))
                                                        )
                                                )
                                        )
                                )
                        )
                );

        //programList = List.of( ex1, ex2, ex3, ex4, simpleFork, ref4);
        //programNames = List.of("ex1", "ex2", "ex3", "ex4", "fork1", "fork2");
        programList = List.of(exSwitch, exFor, exSleep, exWait, exRepeatUntil, exCondAssign); // add all programs here
        programNames = List.of("switch", "for", "sleep", "wait", "repeat...until", "conditional assignment"); //add all programs names here
        ListView<String> programListView = new ListView<>(); //inside the first window that opens is a listView
        programListView.setItems(FXCollections.observableArrayList(programNames));//puts the names in

        programListView.getSelectionModel().selectedIndexProperty().addListener(//this is a: what happens when a user selects an item (its more like a rule for the listView when you select something)
                    ((obs, oldVal, newVal)->{//obs: observable index oldVal: previous selection index newVal: newly selected index
                        if(newVal.intValue() >= 0){ //new index must be >= 0 so we dont go negative
                            Statement program = programList.get(newVal.intValue()); //take the selected program (statement) to run
                            setupProgramController(program);//run it
                        }
                    }));
        VBox programSelection = new VBox(10, new Label("Select Program"), programListView); //the box in the window opened, the box contains programListView
        programSelection.setPadding(new Insets(10)); //add a bit of space
        Scene scene = new Scene(programSelection, 400, 200);//400×200 window size
        stage.setScene(scene);//attaches this inner window to the main one
        stage.setTitle("Program Selection"); //the name of the window
        stage.show();//show it
    }

    private void setupProgramController(Statement program) {//just the things done before executing an example
        ProgramState state = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                program
        );
        RepositoryInterface repo = new Repository("guiRepo.txt");
        repo.addProgramState(state);
        controller = new Controller(repo);

        showExecutionWindow(); //we go to-->
        heapTable.getItems().clear();
        symTable.getItems().clear();
        exeStackList.getItems().clear();
        outList.getItems().clear();
        fileTableList.getItems().clear();
        prgStateIdentifiersList.getItems().clear();

        selectedPrg = null;

    }

    private void showExecutionWindow(){
        Stage stage = new Stage();

        nrPrgState = new TextField();
        nrPrgState.setEditable(false);


        heapTable = new TableView<>();

        TableColumn<Map.Entry<Integer, Value>, Integer> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getKey())
        );


        //addrCol.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Map.Entry<Integer, Value>, Value> valCol = new TableColumn<>("Value");
        //valCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        valCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );
        heapTable.getColumns().addAll(addrCol, valCol);

        outList = new ListView<>();

        fileTableList = new ListView<>();

        prgStateIdentifiersList = new ListView<>();

        prgStateIdentifiersList.getSelectionModel().selectedItemProperty().addListener( //used at fork to change between parent and child
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedPrg = controller.getRepo().getListProgramState().stream()
                                .filter(p -> p.getId()== newVal)
                                .findFirst()
                                .orElse(null);
                        updateSelectedPrgViews();
                    }
                }
        );

        symTable = new TableView<>();
        TableColumn<Map.Entry<String, Value>, String> varCol = new TableColumn<>("Variable");
        varCol.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getKey().toString())
        );
        //varCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<Map.Entry<String, Value>, Value> varValCol = new TableColumn<>("Value");
        //varValCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        varValCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getValue())
        );
        symTable.getColumns().addAll(varCol, varValCol);

        exeStackList = new ListView<>();
        runOneStepButton = new Button("Run one step");
        runOneStepButton.setOnAction(e -> runOneStep());

        heapTable.setPrefWidth(700); // overall table width
        symTable.setPrefWidth(700);
        outList.setPrefWidth(700);
        nrPrgState.setPrefWidth(700);
        fileTableList.setPrefWidth(700);
        prgStateIdentifiersList.setPrefWidth(700);
        exeStackList.setPrefWidth(700);
        heapTable.setPrefHeight(100); // overall table width
        symTable.setPrefHeight(100);
        outList.setPrefHeight(100);
        fileTableList.setPrefHeight(100);
        prgStateIdentifiersList.setPrefHeight(100);
        exeStackList.setPrefHeight(100);
        VBox layout = new VBox(10,
                new Label("Number of Program States:"), nrPrgState,
                new Label("Heap:"), heapTable,
                new Label("Output:"), outList,
                new Label("File Table:"), fileTableList,
                new Label("Program State IDs:"), prgStateIdentifiersList,
                new Label("Symbol Table:"), symTable,
                new Label("Execution Stack:"), exeStackList,
                runOneStepButton
        );
        layout.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(layout);
        Scene scene = new Scene(scrollPane, 900, 700);
        stage.setScene(scene);
        stage.setTitle("Program Execution");
        stage.show();

        updateViews(controller.getRepo().getListProgramState());
    }

    private void runOneStep(){
        try {
            List<ProgramState> prgStates = controller.removeCompletedProgram(controller.getRepo().getListProgramState()); //get the list of program states that dont have the stack empty (remove the completed ones)
            if (!prgStates.isEmpty()) {
                controller.oneStepForAllPrg(prgStates); //if we still have one prog thats still running
                if (!prgStates.isEmpty()) {
                    prgStates.get(0).getHeap().setContent(controller.conservativeGarbageCollector(prgStates, prgStates.get(0).getHeap().getAll()));// conservative garbage collection
                }
            }
            updateViews(prgStates);//update view
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateViews(List<ProgramState> prgStates) {
        nrPrgState.setText(String.valueOf(prgStates.size())); //updates the nr of programs running

        prgStateIdentifiersList.setItems(
                FXCollections.observableArrayList(
                        prgStates.stream()
                                .map(ProgramState::getId)//updates the id
                                .toList()
                )
        );

        if (selectedPrg == null && !prgStates.isEmpty()) {//if no program selected and there are still progrmas in the list
            selectedPrg = prgStates.get(0); //execute the first one
        }

        updateSelectedPrgViews();
    }

    private void updateSelectedPrgViews() {
        if (selectedPrg != null) {
            heapTable.setItems(FXCollections.observableArrayList(selectedPrg.getHeap().getAll().entrySet()));
            outList.setItems(FXCollections.observableArrayList(selectedPrg.getOutput().getAll().stream().map(Object::toString).toList()));
            fileTableList.setItems(FXCollections.observableArrayList(selectedPrg.getFileTable().getAll().keySet().toString()));
            symTable.setItems(FXCollections.observableArrayList(selectedPrg.getSymbolTable().getAll().entrySet()));
            exeStackList.setItems(FXCollections.observableArrayList(selectedPrg.getStack().getAll().stream().map(Object::toString).toList()));
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
