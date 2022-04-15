package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player = null;

    @BeforeEach
    void setUp() {
        player = new Player("Player1",false, new CoinsBag());
    }

    @AfterEach
    void tearDown() {
        player = null;
    }

    @Test
    public void useAssistant_assistant_setLastUsed(){
        // set the assistant deck
        player.setAssistantDeck(Wizard.W2);
        // use CARD_9
        player.useAssistant(Assistant.CARD_9);

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that the CARD_9 has been set as last used
        assertEquals(Assistant.CARD_9,player.getLastAssistant(), "fail due to not setting the card as last used");
        // 2) check that the CARD_9 has been removed from the deck
        assertFalse(player.getHand().contains(Assistant.CARD_9), "the CARD_9 is still in the deck");
    }

    @Test
    public void moveFromEntranceToDiningRoom_FullTableInDiningRoom_ShouldThrowReachedMaxStudentException() {

        // set the school board by filling the BLUE_UNICORN table
        for(int i=0; i<10; i=i+1){
            try {
                player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // add one BLUE_UNICORN to the entrance
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // try to move the one BLUE_UNICORN from entrance to dining room that is full
        assertThrows(ReachedMaxStudentException.class,
                () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void moveFromEntranceToDiningRoom__EntranceAndDiningRoomAreEmpty_ShouldThrowNotEnoughStudentException() {

        // try to move the BLUE_UNICORN that is not present at the entrance to the dining room
        assertThrows(NotEnoughStudentException.class, () -> player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS));

    }

    @Test public void moveFromEntranceToDiningRoom__EntranceNonEmptyAndTwoStudentsInDiningRoom_ShouldMove() {

        // set up the school board by adding one BLUE_UNICORN at the entrance and
        // 2 in the dining room (but in order not to fill the table)
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        try {
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // this is the number of BLUE_UNICORNS in the dining room before the calling of the method
        int oldNumberOfStudents = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // this is the number of BLUE_UNICORNS in the entrance before the calling of the method
        int oldNumberOfStudentsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // move the BLUE_UNICORN from the entrance to the dining room
        try {
            player.moveFromEntranceToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotEnoughStudentException | ReachedMaxStudentException e) {
            fail();
        } catch (NotEnoughCoinsException e) {
            e.printStackTrace();
        }

        // this is the number of BLUE_UNICORNS in the dining room after the calling of the method
        int numberOfStudents = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // this is the number of BLUE_UNICORNS at the entrance after the calling of the method
        int numberOfStudentsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // check that after the calling of the method
        // - the number of the BLUE_UNICORNS in the dining room has been increased of 1
        // - the number of BLUE_UNICORNS at the entrance has been decreased by 1
        assertEquals( oldNumberOfStudents + 1, numberOfStudents,
                " fail due to condition on dining room student's number");
        assertEquals(numberOfStudentsInEntrance + 1 , oldNumberOfStudentsInEntrance ,
                "fail due to condition on number of students in entrance");
    }

    @Test
    public void setTowerType_BLACK_towerTypeIsNull_ShouldSetBLACK(){
        // set the tower to BLACK
        player.setTowerType(TowerType.BLACK);
        // check that the set is done
        assertEquals(TowerType.BLACK,player.getTowerType());
    }

    @Test
    public void setTowerType_BLACK_towerTypeIsWHITE_ShouldRemainBLACK(){
        // set the tower as BLACK
        player.setTowerType(TowerType.BLACK);
        // try to set the tower to WHITE
        player.setTowerType(TowerType.WHITE);
        // check that the tower type is still BLACK
        assertEquals(TowerType.BLACK,player.getTowerType());
    }

    @Test
    public void setAssistantDeck_W1_DeckIsNull_ShouldSet(){
        // set the assistant deck with wizard W1
        player.setAssistantDeck(Wizard.W1);
        // check that the set has been done
        assertEquals(new AssistantDeck(Wizard.W1).getCards(),player.getHand());
    }

    @Test
    public void setAssistantDeck_W1_DeckIsNotNull_ShouldNotChange(){
        // set the assistant deck as W1
        player.setAssistantDeck(Wizard.W1);
        // try to set the assistant deck to W2
        player.setAssistantDeck(Wizard.W2);
        // check that the deck is still the same
        assertEquals(new AssistantDeck(Wizard.W1).getCards(),player.getHand());
    }

    @Test
    public void addStudentToEntrance_BLUEUNICORN_EntranceIsFull_ShouldThrow(){
        // fill the entrance
        try{
            for (int i = 0; i < 7; i++) {
                player.addStudentToEntrance(PawnType.GREEN_FROGS);
            }
        } catch (ReachedMaxStudentException e){
            fail();
        }
        // try to add one student to the full entrance
        assertThrows(ReachedMaxStudentException.class,
                ()-> player.addStudentToEntrance(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void addStudentToEntrance_BLUEUNICORN_EntranceIsNotFull_ShouldAdd(){
        // add a BLUE unicorn to entrance
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }
        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that after the calling of the method the BLUE unicorn is at the entrance
        assertEquals(1,player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS));
        // 1) check that after the calling of the method the all the other types of students in entrance are still 0
        assertEquals(0,player.getStudentsInEntrance().getNumOf(PawnType.RED_DRAGONS));
        assertEquals(0,player.getStudentsInEntrance().getNumOf(PawnType.PINK_FAIRIES));
        assertEquals(0,player.getStudentsInEntrance().getNumOf(PawnType.GREEN_FROGS));
        assertEquals(0,player.getStudentsInEntrance().getNumOf(PawnType.YELLOW_GNOMES));
    }

    @Test
    public void removeStudentFromEntrance_BLUEUNICORN_EntranceIsEmpty_ShouldThrow(){
        // try to remove a student that is not present from the entrance
        assertThrows(NotEnoughStudentException.class, ()->player.removeStudentFromEntrance(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void removeStudentFromEntrance_BLUEUNICORN_EntranceIsNotEmpty_ShouldRemove(){
        // add a BLUE_UNICORN to entrance
        try {
            player.addStudentToEntrance(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // add a RED_DRAGON to entrance
        try {
            player.addStudentToEntrance(PawnType.RED_DRAGONS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // this is the number of BLUE_UNICORNS at the entrance before the calling of the method
        int oldNumOfBlueUnicornsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // remove the BLUE unicorn from entrance
        try {
            player.removeStudentFromEntrance(PawnType.BLUE_UNICORNS);
        } catch (NotEnoughStudentException e) {
            fail();
        }

        // this is the number of BLUE_UNICORNS at the entrance after the calling of the method
        int numOfBlueUnicornsInEntrance = player.getStudentsInEntrance().getNumOf(PawnType.BLUE_UNICORNS);

        // CHECKS AFTER THE CALLING OF THE METHOD

        // 1) check that after the calling of the method the number of BLUE_UNICORNS at the entrance has been decreased by 1
        assertEquals(oldNumOfBlueUnicornsInEntrance - 1, numOfBlueUnicornsInEntrance,
                "fail due to BLUE UNICORNS number");

        // 2) check that after the calling of the method the number of RED_DRAGONS at the entrance is still 1
        assertEquals(1, player.getStudentsInEntrance().getNumOf(PawnType.RED_DRAGONS)
        ,"fail due to wrong RED DRAGONS number");
    }

    @Test
    public void addProfessors_BLUEUNICORN_ProfessorTableIsEmpty_ShouldAdd(){

        // add a BLUE UNICORN professor
        player.addProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that after the calling of the method the professor has been added
        assertTrue(player.getProfessors().contains(PawnType.BLUE_UNICORNS),
                "fail due to BLUE UNICORN has not been inserted in the collection ");

        // 2) check that only that professor has been added
        assertEquals(1, player.getProfessors().size(), "fail due to wrong size of final collection");
    }

    @Test
    public void addProfessors_BLUEUNICORN_ProfessorIsAlreadyThere_ShouldNotAdd(){

        // add the BLUE UNICORN professor
        player.addProfessor(PawnType.BLUE_UNICORNS);

        // this is the size of the collection of professor before the calling of the method
        int oldSizeOfCollection = player.getProfessors().size();

        // try to add another time the BLUE UNICORN professor
        player.addProfessor(PawnType.BLUE_UNICORNS);

        assertTrue(player.getProfessors().contains(PawnType.BLUE_UNICORNS),
                "fail due to BLUE unicorn is not there any more");
        assertEquals(oldSizeOfCollection, player.getProfessors().size(),
                "fail due to wrong size of final collection");
    }

    @Test
    public void removeProfessors_ProfessorIsPresent_ShouldRemove(){

        // add the BLUE UNICORN professor
        player.addProfessor(PawnType.BLUE_UNICORNS);
        // add the RED DRAGON professor
        player.addProfessor(PawnType.RED_DRAGONS);

        // this is the size of the collection before the calling of the method
        int oldSizeCollection = player.getProfessors().size();

        // remove the BLUE UNICORN PROFESSOR
        player.removeProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that after the calling of the method the RED DRAGON professor is still there
        assertTrue(player.getProfessors().contains(PawnType.RED_DRAGONS),
                "fail due to the fact that the RED DRAGON professor is missing");

        // 2) check that after the calling of the method the BLUE UNICORN professor is not there anymore
        assertFalse(player.getProfessors().contains(PawnType.BLUE_UNICORNS),
                "fail due to the fact that the BLUE UNICORN professor is there");

        // 3) check that the size of the collection has decreased by 1
        assertEquals(oldSizeCollection - 1, player.getProfessors().size(),
                "fail due to the wrong size of the collection ");

    }

    @Test
    public void removeProfessors_ProfessorIsNotPresent_ShouldNotRemove(){

        // add the RED DRAGON professor
        player.addProfessor(PawnType.RED_DRAGONS);

        // this is the size of the collection before the calling of the method
        int oldSizeCollection = player.getProfessors().size();

        // remove the BLUE UNICORN PROFESSOR that is not present
        player.removeProfessor(PawnType.BLUE_UNICORNS);

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that after the calling of the method the RED DRAGON professor is still there
        assertTrue(player.getProfessors().contains(PawnType.RED_DRAGONS),
                "fail due to the fact that the RED DRAGON professor is missing");

        // 2) check that the size of the collection is the same
        assertEquals(oldSizeCollection, player.getProfessors().size(),
                "fail due to the wrong size of the collection");
    }

    @Test
    public void changeTowerNumber_WithPositiveValueAndEnoughFreeSpace_ShouldAdd(){

        // remove 2 towers
        player.changeTowerNumber(-2);

        // this is the number of towers before the calling of the method
        int oldTowerNumber = player.getTowerNumbers();

        // add one tower
        player.changeTowerNumber(1);

        // check that the number of tower has been increased by 1
        assertEquals(oldTowerNumber + 1, player.getTowerNumbers());
    }

    @Test
    public void changeTowerNumber_WithNegativeValueAndEnoughTowers_ShouldRemove(){

        // this is the number of towers before the calling of the method
        int oldTowerNumber = player.getTowerNumbers();

        //add one tower
        player.changeTowerNumber(-2);

        // check that after the calling of the method the number of towers has been decreased by 2
        assertEquals(oldTowerNumber - 2, player.getTowerNumbers());

    }

    @Test
    public void changeTowerNumber_WithPositiveValueAndNotEnoughSpace_ShouldThrow(){
        // try to add a tower when there is no space for adding it
        assertThrows(AssertionError.class,
                () -> player.changeTowerNumber(1));
    }

    @Test
    public void addStudentToDiningRoom_BLUEUNICORN_EnoughSpace_ShouldAdd(){

        // add one BLUE UNICORN to dining room
        try {
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that the number of BLUE UNICORNS in the dining room is equal to 1
        assertEquals(1,player.getNumStudentOf(PawnType.BLUE_UNICORNS));

        // 2) check that the number of all the other students after the calling of the method is still zero
        assertEquals(0, player.getNumStudentOf(PawnType.GREEN_FROGS), "fail due to modify of number of FROGS");
        assertEquals(0, player.getNumStudentOf(PawnType.PINK_FAIRIES), "fail due to modify of  number of FAIRIES");
        assertEquals(0, player.getNumStudentOf(PawnType.YELLOW_GNOMES), "fail due to modify of number of GNOMES");
        assertEquals(0, player.getNumStudentOf(PawnType.RED_DRAGONS), "fail due to wrong modify of DRAGONS");
    }

    @Test
    public void addStudentToDiningRoom_BLUEUNICORN_NotEnoughSpace_ShouldThrow(){

        // fill the BLUE_UNICORN table
        for (int i=0 ; i<10 ; i=i+1) {
            try {
                player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            } catch (ReachedMaxStudentException e) {
                fail();
            }
        }

        // try to add another BLUE UNICORN
        assertThrows(ReachedMaxStudentException.class,
                ()->player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void removeStudentFromDiningRoom_BLUEUNICORN_StudentIsPresent_ShouldRemove(){

        // add 2 BLUE UNICORN students
        try {
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
            player.addStudentToDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // add 1 student for all the other types
        try {
            player.addStudentToDiningRoom(PawnType.GREEN_FROGS);
            player.addStudentToDiningRoom(PawnType.RED_DRAGONS);
            player.addStudentToDiningRoom(PawnType.YELLOW_GNOMES);
            player.addStudentToDiningRoom(PawnType.PINK_FAIRIES);
        } catch (ReachedMaxStudentException e) {
            fail();
        }

        // number of BLUE unicorns before the calling of the method
        int oldNumberOfBLUEUNICORNS = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // remove a BLUE UNICORN student
        try {
            player.removeStudentFromDiningRoom(PawnType.BLUE_UNICORNS);
        } catch (NotEnoughStudentException e) {
            fail();
        }

        // number of BLUE unicorns after the calling of the method
        int numberOfBLUEUNICORNS = player.getNumStudentOf(PawnType.BLUE_UNICORNS);

        // CHECKS AFTER THE CALLING OF THE METHOD
        // 1) check that the number of BLUE UNICORN after the calling of the method is decreased by one
        assertEquals(oldNumberOfBLUEUNICORNS - 1, numberOfBLUEUNICORNS, "fail due to wrong number of unicorns ");

        // 2) check that the number of all the other students after the calling of the method is the same
        assertEquals(1, player.getNumStudentOf(PawnType.GREEN_FROGS),"fail due to modify of number of FROGS");
        assertEquals(1, player.getNumStudentOf(PawnType.PINK_FAIRIES),"fail due to modify of  number of FAIRIES");
        assertEquals(1, player.getNumStudentOf(PawnType.YELLOW_GNOMES), "fail due to modify of number of GNOMES");
        assertEquals(1, player.getNumStudentOf(PawnType.RED_DRAGONS),"fail due to wrong modify of DRAGONS");
    }

    @Test
    public void removeStudentFromDiningRoom_BLUEUNICORN_StudentIsNotPresent_ShouldThrow(){
        // try to remove a BLUE UNICORN student that is not present
        assertThrows(NotEnoughStudentException.class,
                ()-> player.removeStudentFromDiningRoom(PawnType.BLUE_UNICORNS));
    }

    @Test
    public void removeCoins_Remove1_1CoinIsPresent_ShouldRemove(){
        // remove the coin that is present in the school board of the player
        try {
            player.removeCoins(1);
        } catch (NotEnoughCoinsException e) {
            fail();
        }

        // check that after the calling of the method there is not any coin in the school board
        assertEquals(0, player.getCoins());

    }

    @Test
    public void removeCoins_Remove1_NoCoinIsPresent_ShouldThrow(){
        // remove the coin that is present in the school board of the player
        try {
            player.removeCoins(1);
        } catch (NotEnoughCoinsException e) {
            fail();
        }
        // try to remove another coin
        assertThrows(NotEnoughCoinsException.class, ()->player.removeCoins(1));
    }
}