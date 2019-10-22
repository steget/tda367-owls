package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Team implements IBorrower {
    private String name;
    private final List<Integer> itemIDs = new ArrayList<>();
    private final List<Integer> memberIDs = new ArrayList<>();
    private String termsAndConditions;
    private int teamID;
    private static int nextID;
    private String imageUrl;

    public Team(String teamName) {
        this.name = teamName;
        termsAndConditions = "";
        teamID = nextID;
        nextID++;
    }

    public void addUsers(List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            memberIDs.add(userList.get(i).getID());
        }
    }

    public void addItems(List<IReservable> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            itemIDs.add(itemList.get(i).getID());
        }
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

    public void addItemToInventory(IReservable itemToAdd) {
        itemIDs.add(itemToAdd.getID());
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

    List<Integer> getAllItemIDs() {
        return itemIDs;
    }

    public int getID() {
        return teamID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static void setNextID(int nextID) {
        Team.nextID = nextID;
    }
}