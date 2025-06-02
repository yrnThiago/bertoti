package mvc;

import java.util.ArrayList;
import java.util.List;

class UserModel {
    private String userName;
    private List<UserView> observers = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyObservers();
    }

    public void addObserver(UserView observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (UserView observer : observers) {
            observer.updateView(this);
        }
    }
}


interface ViewComponent {
    void display();
}

class UserView implements ViewComponent {
    private String id;

    public UserView(String id){
        this.id = id;
    }

    public void updateView(UserModel model) {
        System.out.println("View ["+ id +"]: Nome do usuário atualizado para: " + model.getUserName());
    }

    @Override
    public void display() {
        System.out.println("Exibindo View: " + id);
    }

    public void printUserDetails(String userName){
        System.out.println("View ["+ id +"] Detalhes do Usuário: " + userName);
    }
}

interface UserActionStrategy {
    void execute(UserModel model, String data);
}

class UpdateUserNameStrategy implements UserActionStrategy {
    @Override
    public void execute(UserModel model, String newName) {
        System.out.println("Controller (Strategy): Atualizando nome do usuário para " + newName);
        model.setUserName(newName);
    }
}

class DisplayUserStrategy implements UserActionStrategy {
    private UserView view;

    public DisplayUserStrategy(UserView view) {
        this.view = view;
    }
    @Override
    public void execute(UserModel model, String ignoredData) {
        System.out.println("Controller (Strategy): Exibindo detalhes do usuário.");
        view.printUserDetails(model.getUserName());
    }
}


class UserController {
    private UserModel model;

    public UserController(UserModel model) {
        this.model = model;
    }

    public void performAction(UserActionStrategy strategy, String data) {
        strategy.execute(model, data);
    }
}


class ViewGroup implements ViewComponent {
    private String id;
    private List<ViewComponent> children = new ArrayList<>();

    public ViewGroup(String id) {
        this.id = id;
    }

    public void addComponent(ViewComponent component) {
        children.add(component);
    }

    public void removeComponent(ViewComponent component) {
        children.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Exibindo ViewGroup: " + id);
        for (ViewComponent component : children) {
            component.display();
        }
    }
}

public class MVCDemo {
    public static void main(String[] args) {
        UserModel userModel = new UserModel();
        userModel.setUserName("Usuário Inicial");

        UserView view1 = new UserView("ViewPrincipal");
        UserView view2 = new UserView("ViewSecundaria");

        userModel.addObserver(view1);
        userModel.addObserver(view2);

        ViewGroup mainScreen = new ViewGroup("Tela Principal");
        UserView profileViewComponent = new UserView("ComponentePerfil");
        mainScreen.addComponent(profileViewComponent);
        mainScreen.addComponent(new UserView("ComponenteNotificacoes"));

        System.out.println("--- Exibindo estrutura de Views (Composite) ---");
        mainScreen.display();
        System.out.println("--- Fim da exibição de Views ---");

        UserController userController = new UserController(userModel);

        System.out.println("\n--- Ação: Atualizar Nome ---");
        UserActionStrategy updateStrategy = new UpdateUserNameStrategy();
        userController.performAction(updateStrategy, "Novo Nome via Controller");

        System.out.println("\n--- Ação: Exibir Usuário na View1 ---");
        UserActionStrategy displayStrategy = new DisplayUserStrategy(view1);
        userController.performAction(displayStrategy, null);

        System.out.println("\n--- Mudança direta no Model (Views devem atualizar) ---");
        userModel.setUserName("Outro Nome");
    }
}