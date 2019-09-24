package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private final List<Item> inventory;
    private final List<User> members;
    private String termsAndConditions;

    Team(String teamName){
        this.name = teamName;
        inventory = new ArrayList<>();
        //fill inventory from db
        members = new ArrayList<>();
        //fill members from db
        //fill termsAndConditions from db
    }

    List<Item> getAllItems() {
        return inventory;
    }

    void addMember(User newMember){
        members.add(newMember);
    }

    void removeMember(User memberToBeRemoved){
        members.remove(memberToBeRemoved);
    }

    List<User> getAllMembers(){
        List<User> clonedMemberList = new ArrayList<>(members);
        return clonedMemberList;
    }
}
