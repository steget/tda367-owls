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
    private int teamID;
    private static int nextID;


    //deep copy
    public Team(Team teamToCopy) {
        this.name = teamToCopy.name;
        this.inventory.addAll(teamToCopy.inventory);
        this.memberIDs.addAll(teamToCopy.memberIDs);
        this.termsAndConditions = teamToCopy.termsAndConditions;
    }

    public int getTeamID() {
        return teamID;
    }

    public Team(String teamName) {
        this.name = teamName;
        //todo fill stuff from db
        termsAndConditions = "";
        //fill termsAndConditions from db
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

    void addItemToInventory(Item item) { //todo maybe just an ID instead and get the item from DB?
        inventory.add(item);
    }

    public List<Integer> getAllMemberIDs() {
        return memberIDs;
    }

    /**
     * @param ID from the user you want to check if it exists in the team
     * @return true or false depending if the user exists in the team.
     */
    public boolean doesMemberIDexist(int ID){
        for(int i: this.getAllMemberIDs()){
            if(i == ID){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
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
}