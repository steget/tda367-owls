package storagesystem.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Team implements IHasImageAndName{
    private String name;
    private final List<Item> inventory = new ArrayList<>(); //todo itemIDs instead
    private final List<Integer> memberIDs = new ArrayList<>();
    private String termsAndConditions;
    private int teamID;
    private static int nextID;
    private Image image;
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

    public void addItemToInventory(Item itemToAdd) {
        inventory.add(itemToAdd);
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

    public int getTeamID() {
        return teamID;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}