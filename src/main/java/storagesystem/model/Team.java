package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Team {
    private static int nextID;
    private final List<IReservable> inventory = new ArrayList<>(); //todo itemIDs instead
    private final List<Integer> memberIDs = new ArrayList<>();
    private String name;
    private String termsAndConditions;
    private int teamID;

    public Team(String teamName) {
        this.name = teamName;
        //todo fill stuff from db
        termsAndConditions = "";
        teamID = nextID;
        nextID++;
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
    public void removeMember(int memberToBeRemoved) {
        memberIDs.remove((Object) memberToBeRemoved); //needs to use object to make sure index is not chosen
    }

    public List<Integer> getAllMemberIDs() {
        return memberIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItemToInventory(IReservable itemToAdd) {
        inventory.add(itemToAdd);
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public List<IReservable> getAllItems() {
        return inventory;
    }

    public int getTeamID() {
        return teamID;
    }
}