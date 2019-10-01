package storagesystem.services;

import storagesystem.model.Item;
import storagesystem.model.Team;
//import storagesystem.model.User;

import java.util.List;

public interface IDatabase {
    List<Item> getAllItems();
    //List<User> getAllUsers();
    List<Team> getAllTeams();
}
