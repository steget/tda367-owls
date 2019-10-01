package storagesystem.services;

import javafx.scene.image.Image;
import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.model.Location;
import storagesystem.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {


    private Location hasen = new Location("Hasen", "A storagespace with 6 shelfs and one ladder. To get here, go to the Hubben2.1 and open the door to the right", new Image("art.png"));
    private Item shoes = new Item("Shoes", "You have these on your feet, used for walking", "Two feet and an abillity to walk", 1, 2, Condition.BAD, true, hasen, new Image("cute-owl.jpg"));
    private Item tree = new Item("Tree", "A often green plant that grows to become very tall. The best tree is the oak tree", "You need a saw and a good mood to handle mother natures most lovable thing", 2, 1, Condition.GREAT, false, hasen, new Image("creepy.jpg"));
    private List<Item> itemList = new ArrayList<>();
    
    //private List<User> userList;
    private List<Team> teamList;

    @Override
    public List<Item> getAllItems() {
        return itemList;
    }

    /*@Override
    public List<User> getAllUsers() {
        return userList;
    }
    */


    @Override
    public List<Team> getAllTeams() {
        return teamList;
    }
}
