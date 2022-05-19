package it.polimi.ingsw.server.model.strategies.check_professor;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.Collection;

/**
 * This class implements the {@code checkProfessor(studentColor)} method for the expert version of the game.
 */
public class CheckProfessorCharacter implements CheckProfessorStrategy{

    /**
     * the GameModel class
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * The constructor of the class requires the GameModel to be used to compute the
     * {@code checkProfessor(studentColor)} method.
     * @param gameModel the GameModel to be used for the computation
     */
    public CheckProfessorCharacter(GameModel gameModel){
        this.gameModel=gameModel;
    }

    @Override
    public void checkProfessor(PawnType studentColor) {
// current player of the game
        Player currentPlayer = gameModel.getCurrentPlayer();

        // 3 different situations are possible:
        /* 1. I have the professor
         * if I have the professor, and I have added one more student
         * for sure I still have the max number of students and therefore the professor is still mine
         */
        if(currentPlayer.getProfessors().contains(studentColor)){
            return;
        }

        /* 2. The professor belongs to an adversary
         * - find who has it
         * - if I have more or equal students than him, then I take the professor after having removed
         *   the professor by him
         */
        Collection<Player> playerList = gameModel.getPlayerList();
        int numOfStudents=currentPlayer.getNumStudentOf(studentColor);

        for(Player player:playerList){
            if(player.getProfessors().contains(studentColor)){
                if(numOfStudents >= player.getNumStudentOf(studentColor)){
                    player.removeProfessor(studentColor);
                    currentPlayer.addProfessor(studentColor);
                }
                return;
            }
        }

        /* 3. No one has the professor
         * If I have added one student, necessarily I have to take the professor
         */
        currentPlayer.addProfessor(studentColor);

    }
}
