package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 */
public class Team {
    private String name;
    private final List<Item> inventory = new ArrayList<>(); //todo itemIDs instead
    private final List<Integer> memberIDs = new ArrayList<>();
    private String termsAndConditions;

    //deep copy
    public Team(Team teamToCopy) {
        this.name = teamToCopy.name;
        this.inventory.addAll(teamToCopy.inventory);
        this.memberIDs.addAll(teamToCopy.memberIDs);
        this.termsAndConditions = teamToCopy.termsAndConditions;
    }

    public Team(String teamName) {
        this.name = teamName;
        //todo fill stuff from db
        termsAndConditions = "";
    }

    /**
     * Adds ID of a member to a team.
     *
     * @param newMember ID of the new member
     */
    public void addMember(int newMember) {
        memberIDs.add(newMember);
    }

    /**
     * Remove member from its team.
     *
     * @param memberToBeRemoved ID of the member to be removed
     */
    void removeMember(int memberToBeRemoved) {
        memberIDs.remove(Integer.valueOf(memberToBeRemoved)); //needs to use Integer to make sure index is not chosen
    }

    /**
     * @return List of all the members IDs. Defensive copy
     */
    List<Integer> getAllMemberIDs() {
        return new ArrayList<>(memberIDs);
    }

    /**
     * @return A new instance of Team with the same attribute values as this
     */
    public Team copy() {
        return new Team(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    List<Item> getAllItems() {
        //todo defensive copy
        return inventory;
    }

    public String getName() {
        return name;
    }
}
