package storeit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */

public class Team implements IBorrower {
    private static int nextID;
    private final List<Integer> itemIDs = new ArrayList<>();
    private final List<Integer> memberIDs = new ArrayList<>();
    private String name;
    private String termsAndConditions;
    private int ID;

    public Team(String teamName) {
        this.name = teamName;
        termsAndConditions = "";
        ID = nextID;
        nextID++;
    }

    public static void setNextID(int nextID) {
        Team.nextID = nextID;
    }

    public boolean isItemOwner(int itemID) {
        return itemIDs.contains(itemID);
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

    void addItemIDToInventory(int itemID) {
        itemIDs.add(itemID);
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public List<Integer> getAllItemIDs() {
        return itemIDs;
    }

    public int getID() {
        return ID;
    }
}