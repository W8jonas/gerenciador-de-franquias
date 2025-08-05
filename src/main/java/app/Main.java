package app;

import repository.JsonOwnerRepository;
import repository.OwnerRepository;

import views.MainView;

public class Main {
    public static void main(String[] args) {
        OwnerRepository ownerRepo = new JsonOwnerRepository();
        boolean existsOwner = ownerRepo.existsAnyOwner();

        MainView mainView = new MainView();
        mainView.start(existsOwner);
    }

}
