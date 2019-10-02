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
    private final List<Item> inventory = new ArrayList<>(); // maybe itemIDs instead?
    private final List<Integer> memberIDs = new ArrayList<>();
    private String termsAndConditions;

    //deep copy
    public Team(Team teamToCopy) {
        this.name = teamToCopy.getName();
        this.inventory.addAll(teamToCopy.inventory);
        this.memberIDs.addAll(teamToCopy.memberIDs);
    }

    public Team(String teamName) {
        this.name = teamName;
        //fill inventory from db
        //fill memberIDs from db
        termsAndConditions = "";
        //fill termsAndConditions from db
    }

    /**
     * Adds a member to a team.
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
        memberIDs.remove((Object) memberToBeRemoved); //needs to use object to make sure index is not chosen
    }

    List<Integer> getAllMemberIDs() {
        return memberIDs;
    }

    /**
     * @return A new instance of Team with the same attribute values as this
     */
    public Team getDeepCopy() {
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
        return inventory;
    }

    public String getName() {
        return name;
    }
}
