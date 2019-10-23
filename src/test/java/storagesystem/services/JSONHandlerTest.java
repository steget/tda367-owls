package storagesystem.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import javafx.embed.swing.JFXPanel;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.JSONHandler.*;

public class JSONHandlerTest {


    @Test
    public void addToJson() throws IOException {
        JSONHandler.clearAllJsonFiles();
        Organisation mockOrg = new Organisation("Mock Organisation");

        Organisation mockOrg2 = new Organisation("Mock Organisation 2");
        String imageUrl = "/pictures/art.png";
        Location location = new Location("Test Location", "Exists in mockorg2");
        Team testTeam = new Team("testTeam");
        mockOrg2.addTeam(testTeam);

        mockOrg2.addItem(new Item("testItem", "exists in mockOrg2", "userRequirements", 1, Condition.GREAT, false, location.getID(), imageUrl), testTeam);

        JSONHandler.addToJson(mockOrg);
        JSONHandler.addToJson(mockOrg2);

        List<Organisation> organisationList = getOrganisationList();

        Assert.assertEquals(organisationList.get(0).getName(), mockOrg.getName());
        Assert.assertNotEquals(organisationList.get(1).getName(), mockOrg.getName());
        Assert.assertEquals(organisationList.get(1).getName(), mockOrg2.getName());
        Assert.assertEquals(organisationList.get(1).getAllItems(), mockOrg2.getAllItems());
        Assert.assertEquals(organisationList.get(1).getImageUrl(), mockOrg2.getImageUrl());
    }

    @Test
    public void addListToJson() throws IOException {
        JSONHandler.clearAllJsonFiles();
        Organisation mockOrg = new Organisation("Mock Organisation");

        Organisation mockOrg2 = new Organisation("Mock Organisation 2");
        String imageUrl = "/pictures/art.png";
        Location location = new Location("Test Location", "Exists in mockorg2");
        Team testTeam = new Team("testTeam");
        mockOrg2.addTeam(testTeam);

        mockOrg2.addItem(new Item("testItem", "exists in mockOrg2", "userRequirements", 1, Condition.GREAT, false, location.getID(), imageUrl), testTeam);

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(mockOrg);
        organisations.add(mockOrg2);

        JSONHandler.addListToJson(organisations);

        List<Organisation> organisationList = getOrganisationList();

        Assert.assertEquals(organisationList.get(0).getName(), mockOrg.getName());
        Assert.assertNotEquals(organisationList.get(1).getName(), mockOrg.getName());
        Assert.assertEquals(organisationList.get(1).getName(), mockOrg2.getName());
        Assert.assertEquals(organisationList.get(1).getAllItems(), mockOrg2.getAllItems());
        Assert.assertEquals(organisationList.get(1).getImageUrl(), mockOrg2.getImageUrl());

    }
}