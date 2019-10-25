package storeit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team. //TODO: expand this
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

    /**
     * Creates a team with selected teamName, empty termsAndConditions, an ID equal to nextID, then increments nextID.
     *
     * @param teamName name of the team to create
     */

    public Team(String teamName) {
        this.name = teamName;
        termsAndConditions = "";
        ID = nextID;
        nextID++;
    }

    /**
     * Adds a list of users' IDs to the team's memberIDs list
     *
     * @param userList list of users to add
     */

    public void addUsers(List<User> userList) {
        for (User user : userList) {
            memberIDs.add(user.getID());
        }
    }

    /**
     * Adds a list of items' IDs to the item's itemIDs list
     *
     * @param itemList list of items to add
     */

    public void addItems(List<IReservable> itemList) {
        for (IReservable iReservable : itemList) {
            itemIDs.add(iReservable.getID());
        }
    }

    /**
     * Adds the selected itemID to the itemIDs list
     *
     * @param itemID itemID to add
     */
    void addItemIDToInventory(int itemID) {
        itemIDs.add(itemID);
    }


    /**
     * Checks if the itemIDs list has an ID equal to the selected itemID.
     *
     * @param itemID ID of an item
     * @return true if itemIDs contains itemID
     */

    public boolean isItemOwner(int itemID) {
        if (itemIDs.contains(itemID)) {
            return true;
        }
        return false;
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

    /**
     * @return A list of all memberIDs
     */
    public List<Integer> getAllMemberIDs() {
        return memberIDs;
    }

    /**
     * @return A list of all itemIDs
     */
    public List<Integer> getAllItemIDs() {
        return itemIDs;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }


    public int getID() {
        return ID;
    }

    public static void setNextID(int nextID) {
        Team.nextID = nextID;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }
}