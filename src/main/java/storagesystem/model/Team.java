package storagesystem.model;

import javafx.scene.image.Image;
import storagesystem.services.GSONHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A team has a list of users that belong to the same team.
 * A team has an inventory of items which it can browse.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Team implements IHasImageAndName, IBorrower {
    private String name;
    private final List<Integer> itemIDs = new ArrayList<>();
    private final List<Integer> memberIDs = new ArrayList<>();
    private String termsAndConditions;
    private int teamID;
    private static int nextID;
    private Image image;

    public Team(String teamName) throws IOException {
        this.name = teamName;
        List<Item> itemList = GSONHandler.getListFromJson(Item.class);
        for (int i = 0; i < itemList.size(); i++) {
            itemIDs.add(itemList.get(i).getID());
        }
        List<User> userList = GSONHandler.getListFromJson(User.class);
        for (int i = 0; i < userList.size(); i++) {
            memberIDs.add(userList.get(i).getID());
        }
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}