package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 * A user may borrow items from other teams inventory for its own team .
 */
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

    /**
     * Adds a member to a team.
     * @param newMember
     */
    void addMember(User newMember){
        members.add(newMember);
    }

    /**
     * Remove member from its team.
     * @param memberToBeRemoved
     */
    void removeMember(User memberToBeRemoved){
        members.remove(memberToBeRemoved);
    }

    List<User> getAllMembers(){
        List<User> clonedMemberList = new ArrayList<>(members);
        return clonedMemberList;
    }
}
